package com.snmi.security;

import com.snmi.util.JWTTokenCleaner;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

import static com.snmi.constants.SecurityGlobalConstant.TOKEN_PREFIX;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.secret}")
    private String tokenSecret;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain chain
    ) throws ServletException, IOException {
        final String requestHeader = request.getHeader(tokenHeader);
        if (requestHeader != null && requestHeader.startsWith(TOKEN_PREFIX)) {
            final String authToken = JWTTokenCleaner.finalizeToken(requestHeader);
            if (isTokenStillActive(authToken)) {
                JwtAuthentication authentication = new JwtAuthentication(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

    public boolean isTokenStillActive(final String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(tokenSecret.getBytes())
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration().after(new Date());
        } catch (MalformedJwtException | SignatureException | ExpiredJwtException exception) {
            return false;
        }
    }

}