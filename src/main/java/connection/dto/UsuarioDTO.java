package connection.dto;

import connection.model.Assunto;
import connection.model.Permissao;
import connection.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

        private Long id;

        private String nome;

        private String email;

        private String senha;

        private String uuid;

        private String foto;

        private String descricao;

        private Permissao permissao;

        private Date dataCadastro;

        private Boolean ativo;

        private List<Assunto> assuntos;

        public UsuarioDTO(Usuario usuario){
            this.id = usuario.getId();
            this.nome = usuario.getNome();
            this.email = usuario.getEmail();
            this.senha = usuario.getSenha();
            this.uuid = usuario.getUuid();
            this.foto = usuario.getFoto();
            this.descricao = usuario.getDescricao();
            this.permissao = usuario.getPermissao();
            this.dataCadastro = usuario.getDataCadastro();
            this.ativo = usuario.getAtivo();
            this.assuntos = usuario.getAssuntos();
        }
}
