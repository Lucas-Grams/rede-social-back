package connection.security;

import connection.model.Usuario;
import connection.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService; // Serviço de validação de token

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositório de usuários

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Permitir acesso ao endpoint de login sem autenticação
        if ("/auth/login".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // Recupera o token do cabeçalho
        String token = recoverToken(request);
        if (token != null) {
            String login = tokenService.validateToken(token); // Valida o token e obtém o login
            if (login != null) {
                Usuario user = usuarioRepository.findByEmail(login)
                        .orElseThrow(() -> new RuntimeException("User Not Found"));
                var authorities = Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + user.getPermissao().getNome().toUpperCase().replace(" ", "_"))
                );
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response); // Continua a cadeia de filtros
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Remove o prefixo "Bearer " do token
        }
        return null; // Retorna null se o cabeçalho não estiver presente ou não começar com "Bearer "
    }
}
