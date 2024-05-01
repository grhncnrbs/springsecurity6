package org.grhncnrbs.springsecurity6.auth;

import lombok.RequiredArgsConstructor;
import org.grhncnrbs.springsecurity6.config.JwtService;
import org.grhncnrbs.springsecurity6.model.dto.request.RegisterRequest;
import org.grhncnrbs.springsecurity6.model.dto.response.AuthenticationResponse;
import org.grhncnrbs.springsecurity6.model.entity.User;
import org.grhncnrbs.springsecurity6.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();
        var savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return  AuthenticationResponse.builder().accessToken(jwtToken).build();
    }
}
