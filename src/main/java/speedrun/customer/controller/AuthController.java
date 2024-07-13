package speedrun.customer.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import speedrun.customer.model.Role;
import speedrun.customer.model.User;
import speedrun.customer.repository.UserRepository;
import speedrun.customer.security.JwtTokenProvider;
import speedrun.customer.utils.dto.UserDTO;
import speedrun.customer.utils.response.Res;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;


    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return Res.renderJson(null, "username is taken", HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        if (newUser.getRole() == null) {
            newUser.setRole(Role.ROLE_USER);
        }
        return Res.renderJson(
                userRepository.save(newUser),
                "Successfully Registered!",
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = tokenProvider.generateToken(auth);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);

        return Res.renderJson(
                response,
                "Login Sucessfull!",
                HttpStatus.OK
        );
    }

    @PostConstruct
    public void initAdmin() {
        String username = "admin";
        String password = "admin";

        Optional<User> optionalUserCredential = userRepository.findByUsername(username);
        if(optionalUserCredential.isPresent()) {
            return;
        }

        Role roleAdmin = Role.ROLE_ADMIN;
        User userCredential = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(roleAdmin)
                .build();
        userRepository.save(userCredential);
    }
}
