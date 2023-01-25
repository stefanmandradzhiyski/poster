package com.snmi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static com.snmi.constants.GlobalConstants.EMPTY_STRING;
import static com.snmi.constants.SecurityGlobalConstant.JWT_SECRET;
import static com.snmi.constants.SecurityGlobalConstant.TOKEN_PREFIX;

@Component
public class JwtTokenParserService {

    @Autowired
    public JwtTokenParserService() {

    }

    public String getStringValueFromToken(String token, String stringProperty) {
        return (String) getAllClaimsFromToken(token).get(stringProperty);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaimsFromToken(token));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET.getBytes())
                .parseClaimsJws(token.replace(TOKEN_PREFIX, EMPTY_STRING)).getBody();
    }

    private Boolean isTokenNotExpired(String token) {
        return getExpirationDateFromToken(token).after(new Date());
    }

    public Optional<Boolean> validateToken(String token) {
        return isTokenNotExpired(token) ? Optional.of(Boolean.TRUE) : Optional.empty();
    }

}