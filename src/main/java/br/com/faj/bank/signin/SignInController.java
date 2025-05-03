package br.com.faj.bank.signin;

import br.com.faj.bank.infra.security.JwtService;
import br.com.faj.bank.signin.model.request.AuthenticateRequest;
import br.com.faj.bank.signin.model.response.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/v1/signin")
public class SignInController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public SignInController(
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody AuthenticateRequest request) {

        if (request.email() == null || request.password() == null) {
            return ResponseEntity.badRequest().body("Email and password are required");
        }

        UsernamePasswordAuthenticationToken data = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.getPasswordByQuery()
        );

        try {
            authenticationManager.authenticate(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        String token = jwtService.encode(request.email());

        if (token == null) {
            return ResponseEntity.badRequest().body("Failed to login");
        }

        AuthenticateResponse response = new AuthenticateResponse(token, new Date());

        return ResponseEntity.ok(response);
    }
}
