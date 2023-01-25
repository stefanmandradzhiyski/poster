package com.snmi.service;

import com.snmi.dto.JwtToken;
import com.snmi.exception.JwtAuthenticationException;
import com.snmi.model.JwtUser;
import com.snmi.repository.JwtUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.snmi.constants.SecurityGlobalConstant.PASSWORD;
import static com.snmi.constants.SecurityGlobalConstant.TOKEN_AUDIENCE;
import static com.snmi.constants.SecurityGlobalConstant.TOKEN_ISSUER;
import static com.snmi.constants.SecurityGlobalConstant.TOKEN_TYPE;
import static com.snmi.constants.SecurityGlobalConstant.TOKEN_TYPE_HEADER;
import static com.snmi.constants.SecurityGlobalConstant.USERNAME;
import static com.snmi.constants.SecurityGlobalConstant.WRONG_USER_CREDENTIALS;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expirationTime;

    private final PasswordEncoder passwordEncoder;
    private final JwtUserRepository jwtUserRepository;

    public JwtToken generateJWTToken(final String username, final String password) {
        return jwtUserRepository.findByUsername(username)
                .filter(jwtUser ->  passwordEncoder.matches(password, jwtUser.getPassword()))
                .map(jwtUser -> new JwtToken(generateToken(jwtUser)))
                .orElseThrow(() ->  new JwtAuthenticationException(WRONG_USER_CREDENTIALS));
    }

    public String generateToken(final JwtUser jwtUser) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .setHeaderParam(TOKEN_TYPE_HEADER, TOKEN_TYPE)
                .setIssuer(TOKEN_ISSUER)
                .setAudience(TOKEN_AUDIENCE)
                .claim(USERNAME, jwtUser.getUsername())
                .claim(PASSWORD, jwtUser.getPassword())
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(expirationTime)))
                .compact();
    }

}
