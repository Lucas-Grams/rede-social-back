package connection.service;

import connection.dto.LoginDTO;
import connection.dto.LoginResponse;
import connection.model.Usuario;
import connection.repository.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Service
public class LoginService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public LoginResponse login(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.email())
                .filter(u -> passwordEncoder.matches(loginDTO.senha(), u.getSenha()))
                .orElseThrow(() -> new BadCredentialsException("E-mail ou senha inválidos"));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("connection")
                .subject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim("uuid", usuario.getUuid())
                .claim("nome", usuario.getNome())
                .claim("email", usuario.getEmail())
                .claim("ativo", usuario.getAtivo())
                .claim("permissao", usuario.getPermissao().getNome())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(600))
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(token, 600L);
    }
}
