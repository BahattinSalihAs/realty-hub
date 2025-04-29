package realtyhub.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import realtyhub.common.error.UserNotFoundException;
import realtyhub.common.security.service.JwtService;
import realtyhub.common.util.PasswordEncryptorUtil;
import realtyhub.user.model.dto.UserResponse;
import realtyhub.user.model.dto.request.UserAuthenticationRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.service.UserAuthenticationService;

@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncryptorUtil passwordEncryptorUtil;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public final UserResponse authenticate(
            final UserAuthenticationRequest userAuthenticationRequest
    ) {

        final UserEntity userEntity = userRepository.findByEmail(userAuthenticationRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncryptorUtil.isMatch(userAuthenticationRequest.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthenticationRequest.getEmail(), userAuthenticationRequest.getPassword()));
        final String token = jwtService.generateToken(userEntity);

        return UserResponse.builder().token(token).build();
    }
}
