package com.goldenhour.infrastructure.security;

import com.goldenhour.domain.user.model.LoginRequestDTO;
import com.goldenhour.domain.user.model.LoginResponseDTO;
import com.goldenhour.domain.user.service.UserService;
import com.goldenhour.infrastructure.handler.exceptions.ForbiddenException;
import com.goldenhour.infrastructure.handler.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(@Lazy UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = userService.getUserByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Username doesn't exist");
        }

        var user = optionalUser.get();

        return new AuthenticatedUser(user);
    }

    public LoginResponseDTO authenticate(LoginRequestDTO loginDTO) {
        var authenticatedUser = (AuthenticatedUser) loadUserByUsername(loginDTO.username());

        if (!passwordEncoder.matches(loginDTO.password(), authenticatedUser.getPassword())) {
            throw new NotFoundException("Password isn't valid");
        }

        var accessToken = jwtUtil.createToken(authenticatedUser);

        return new LoginResponseDTO(accessToken);
    }

    public void canUserAccess(String username, String exceptionMessage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String loggedInUser = authentication.getName();
            if (!loggedInUser.equals(username)) {
                throw new ForbiddenException(exceptionMessage);
            }
        }
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
