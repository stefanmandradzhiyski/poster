package com.snmi.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityGlobalConstant {

    public static final String TOKEN_TYPE = "JWT";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String TOKEN_ISSUER = "poster";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_AUDIENCE = "poster";
    public static final String TOKEN_TYPE_HEADER = "type";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String INVALID_JWT_TOKEN = "Invalid JWT Token";
    public static final String WRONG_USER_CREDENTIALS = "Wrong user credentials";
    public static final String JWT_VALIDATION_FAILED = "JWT Token validation failed";
    public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

}