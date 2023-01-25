package com.snmi.client;

import com.snmi.dto.AuthenticationDTO;
import com.snmi.dto.BaseResponse;
import com.snmi.dto.JwtToken;
import com.snmi.util.BaseRESTTemplate;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.snmi.constants.PortGlobalConstants.AUTH_SERVER_PORT;
import static com.snmi.constants.SecurityGlobalConstant.PASSWORD;
import static com.snmi.constants.SecurityGlobalConstant.TOKEN_AUDIENCE;
import static com.snmi.constants.SecurityGlobalConstant.TOKEN_ISSUER;
import static com.snmi.constants.SecurityGlobalConstant.TOKEN_TYPE;
import static com.snmi.constants.SecurityGlobalConstant.TOKEN_TYPE_HEADER;
import static com.snmi.constants.SecurityGlobalConstant.USERNAME;

@Service
public class AuthenticationServiceClient extends BaseClient {

    @Value("${jwt.secret")
    private String tokenSecret;

    @Value("${jwt.expiration}")
    private String tokenExpiration;

    public AuthenticationServiceClient() {
        setPort(AUTH_SERVER_PORT);
        setBaseURL("api/v1/auth");
    }

    public BaseResponse<JwtToken> login(final String token, final AuthenticationDTO authenticationDTO) {
        return BaseRESTTemplate.post(token, getBaseURL() + "/login", authenticationDTO, new ParameterizedTypeReference<>(){});
    }

    public String generateAnonymousToken() {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes())
                .setHeaderParam(TOKEN_TYPE_HEADER, TOKEN_TYPE)
                .setIssuer(TOKEN_ISSUER)
                .setAudience(TOKEN_AUDIENCE)
                .claim(USERNAME, "posterAnonymousUsername")
                .claim(PASSWORD, "123456")
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(tokenExpiration)))
                .compact();
    }

}
