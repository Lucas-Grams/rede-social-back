package connection.service;

import connection.dto.LoginDTO;
import connection.dto.LoginResponse;
import connection.model.Usuario;
import connection.repository.UsuarioRepository;
import connection.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Service
public class LoginService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    public LoginService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public LoginResponse login(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.email())
                .orElseThrow(() -> new BadCredentialsException("E-mail ou senha inválidos"));

        if(!passwordEncoder.matches(loginDTO.senha(), usuario.getSenha())) {
            return null;
        }
        String token = this.tokenService.generateToken(usuario);

        return new LoginResponse(token, 600L);
    }
}
