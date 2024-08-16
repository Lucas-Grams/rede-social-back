package connection.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import connection.model.Usuario;
import connection.security.TokenService;
import org.springframework.aop.interceptor.SimpleTraceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioUtils {

    @Autowired
    TokenService tokenService;

    public Usuario getUserFromToken() {
        String token = tokenService.getToken();
        DecodedJWT decodedJWT = tokenService.decodeToken(token);
        if (decodedJWT != null) {
            String email = decodedJWT.getSubject();
            String nome = decodedJWT.getClaim("nome").asString();
            String uuid = decodedJWT.getClaim("uuid").asString();

            // Crie um objeto Usuario com as informações obtidas
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setNome(nome);
            usuario.setUuid(uuid);

            return usuario;
        }
        return null;
    }
}
