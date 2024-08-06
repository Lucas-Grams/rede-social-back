package connection.dto;

public record LoginDTO(String email, String senha) {

    @Override
    public String email() {
        return email;
    }

    @Override
    public String senha() {
        return senha;
    }

}
