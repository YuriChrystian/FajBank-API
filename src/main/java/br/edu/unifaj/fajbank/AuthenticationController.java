package br.edu.unifaj.fajbank;

import br.edu.unifaj.fajbank.data.dto.LoginUserDto;
import br.edu.unifaj.fajbank.data.dto.RegisterUserDto;
import br.edu.unifaj.fajbank.data.response.LoginResponse;
import br.edu.unifaj.fajbank.service.AuthenticationService;
import br.edu.unifaj.fajbank.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDetails> register(@RequestBody RegisterUserDto registerUserDto) {
        UserDetails registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        System.out.println("Chamou o login " + loginUserDto.getEmail() + " " + loginUserDto.getPassword());
        UserDetails authenticatedUser = authenticationService.authenticate(loginUserDto);
        System.out.println("Chamou o login " + authenticatedUser.getPassword());

        String jwtToken = jwtService.generateToken(authenticatedUser);
        System.out.println("Chamou o login JWT" + jwtToken);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        System.out.println("Chamou o login JWT" + loginResponse.getToken());
        return ResponseEntity.ok(loginResponse);
    }
}
