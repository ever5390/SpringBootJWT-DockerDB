package com.rpej.security.Auth;

import com.rpej.security.Exceptions.domain.EmailExistsException;
import com.rpej.security.Exceptions.ExceptionHandling;
import com.rpej.security.Exceptions.domain.UserNameExistsException;
import com.rpej.security.Exceptions.domain.UserNameNotFoundException;
import com.rpej.security.Jwt.JwtService;
import com.rpej.security.User.User;
import com.rpej.security.User.UserService;
import com.rpej.security.dtoAuth.AuthResponse;
import com.rpej.security.dtoAuth.HttpResponse;
import com.rpej.security.dtoAuth.LoginRequest;
import com.rpej.security.dtoAuth.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController extends ExceptionHandling {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final AuthService authService;

    private final JwtService jwtService;

    private final UserService userService;

    public AuthController(AuthService authService, JwtService jwtService, UserService userService) {
		this.authService = authService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

	@PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) throws UserNameNotFoundException {
        LOGGER.info("::::::: LOGGER ::::::");
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<HttpResponse> register(@RequestBody RegisterRequest request) throws UserNameExistsException, EmailExistsException {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('user:create')")
    @PostMapping(value = "newUser")
    public ResponseEntity<HttpResponse> registerNewUser(@RequestBody RegisterRequest request) throws UserNameExistsException, EmailExistsException {
        return new ResponseEntity<>(userService.registerNewUser(request), HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated() and not hasRole('ROLE_USER')")
    @GetMapping("/users")
    public List<User> showUsers() {
        return this.userService.showUsers();
    }

}
