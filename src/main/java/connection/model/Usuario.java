package connection.model;

import connection.dto.UsuarioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    private String uuid;

    private String foto;

    private String descricao;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "permissao")
    private Permissao permissao;

    @Column(name = "data_cadastro")
    private Date dataCadastro;

    private Boolean ativo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_assunto",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "assunto_id"))
    private List<Assunto> assuntos;

    public Usuario(UsuarioDTO usuarioDTO) {
        this.id = usuarioDTO.getId();
        this.nome = usuarioDTO.getNome();
        this.email = usuarioDTO.getEmail();
        this.senha = usuarioDTO.getSenha();
        this.uuid = usuarioDTO.getUuid();
        this.foto = usuarioDTO.getFoto();
        this.descricao = usuarioDTO.getDescricao();
        this.permissao = usuarioDTO.getPermissao();
        this.dataCadastro = usuarioDTO.getDataCadastro();
        this.ativo = usuarioDTO.getAtivo();

    }
}