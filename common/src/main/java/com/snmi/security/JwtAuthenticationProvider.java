package com.snmi.security;

import com.snmi.constants.SecurityGlobalConstant;
import com.snmi.exception.JwtAuthenticationException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    private final JwtTokenParserService jwtService;

    @SuppressWarnings("unused")
    public JwtAuthenticationProvider() {
        this(null);
    }

    @Autowired
    public JwtAuthenticationProvider(JwtTokenParserService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String token = (String) authentication.getCredentials();
            String username = jwtService.getStringValueFromToken(token, SecurityGlobalConstant.USERNAME);
            String password = jwtService.getStringValueFromToken(token, SecurityGlobalConstant.PASSWORD);

            return jwtService.validateToken(token)
                    .map(bool -> new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()))
                    .orElseThrow(() -> new JwtAuthenticationException(SecurityGlobalConstant.JWT_VALIDATION_FAILED));

        } catch (JwtException | JwtAuthenticationException ex) {
            log.error(String.format("%s %s", SecurityGlobalConstant.INVALID_JWT_TOKEN, ex.getMessage()));
            throw new JwtAuthenticationException(SecurityGlobalConstant.INVALID_JWT_TOKEN);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }

}