package com.rpej.security.Auth;

import com.rpej.security.Exceptions.domain.EmailExistsException;
import com.rpej.security.Exceptions.domain.UserNameExistsException;
import com.rpej.security.Exceptions.domain.UserNameNotFoundException;
import com.rpej.security.User.Role;
import com.rpej.security.User.User;
import com.rpej.security.dtoAuth.AuthResponse;
import com.rpej.security.dtoAuth.HttpResponse;
import com.rpej.security.dtoAuth.LoginRequest;
import com.rpej.security.dtoAuth.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rpej.security.Jwt.JwtService;
import com.rpej.security.User.UserRepository;

import java.util.Optional;

@Service
public class AuthService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

	private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

	public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}
	
    public AuthResponse login(LoginRequest request) throws UserNameNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        String token=jwtService.getToken(user);
        return new AuthResponse.Builder()
            .token(token)
            .build();
    }

    public HttpResponse register(RegisterRequest request) throws UserNameExistsException, EmailExistsException {

        validateUserAndEmailExists(request.getUsername(), request.getEmail());
        User user = new User.Builder()
                .username(request.getUsername())
                .email(request.getEmail())
            .password(passwordEncoder.encode( request.getPassword()))
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .country(request.getCountry())
            .role(Role.USER)
            .build();

        userRepository.save(user);

        return new HttpResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED, HttpStatus.CREATED.getReasonPhrase().toUpperCase().toString(),"Usuario creado exitosamente!!");

    }

    private void validateUserAndEmailExists(String username, String email) throws UserNameExistsException, EmailExistsException {

        Optional<User> userByUsername = userRepository.findByUsername(username);
        if(userByUsername.isPresent()) {
            throw new UserNameExistsException("Username already exists");
        }

        Optional<User> userbyEmail = userRepository.findByEmail(email);
        if(userbyEmail.isPresent()) {
            throw new EmailExistsException("Email already exists");
        }
    }

}
