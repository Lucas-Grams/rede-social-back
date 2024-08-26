package connection.service;

import connection.dto.UsuarioDTO;
import connection.model.Usuario;
import connection.repository.UsuarioRepository;
import connection.utils.UsuarioUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioUtils usuarioUtils;
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            UsuarioUtils usuarioUtils
    ) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioUtils = usuarioUtils;
    }

    public UsuarioDTO me() {
        Usuario usuario = this.usuarioUtils.getUserFromToken();

        UsuarioDTO usuarioDTO = new UsuarioDTO(
                this.usuarioRepository.findByUuid(usuario.getUuid()).get()
        );

        return usuarioDTO;
    }
}
