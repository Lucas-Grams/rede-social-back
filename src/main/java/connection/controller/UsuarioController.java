package connection.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/usuario")
public class UsuarioController {

    @GetMapping("/me")
    public String me() {
        return "Hello World!";
    }
}
