package connection.controller;

import connection.dto.LoginDTO;
import connection.dto.LoginResponse;
import connection.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private JwtEncoder jwtEncoder;
    private final LoginService loginService;

    public LoginController(JwtEncoder jwtEncoder, LoginService loginService) {
        this.jwtEncoder = jwtEncoder;
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(loginService.login(loginDTO));
    }
}
