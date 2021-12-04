package com.bootcamp.demo.security;

import com.bootcamp.demo.model.User;
import com.bootcamp.demo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    private UserRepository userRepository;

    public UserAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken upAuth = (UsernamePasswordAuthenticationToken) authentication;
        final String name = (String) authentication.getPrincipal();

        final String password = (String) upAuth.getCredentials();
        User user = userRepository.getUserByEmail(name);
        if (user != null) {
            if (!user.getPassword().equals(password))
                throw new BadCredentialsException("Invalid username or password");

            final Object principal = authentication.getPrincipal();
            final UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                    principal, authentication.getCredentials(),
                    user.getAuthorities());
            result.setDetails(authentication.getDetails());
            return result;
        }
        throw new BadCredentialsException("Invalid username or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}