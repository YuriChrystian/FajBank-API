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

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public SignInController(
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody AuthenticateRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String token = jwtService.encode(request.email());

        return ResponseEntity.ok(new AuthenticateResponse(token, new Date()));
    }
}
