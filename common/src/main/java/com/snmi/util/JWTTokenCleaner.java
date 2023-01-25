package com.snmi.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.snmi.constants.GlobalConstants.EMPTY_STRING;
import static com.snmi.constants.SecurityGlobalConstant.TOKEN_PREFIX;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JWTTokenCleaner {

    public static String finalizeToken(final String token) {
        String cleanToken = token;
        while (cleanToken.contains(TOKEN_PREFIX)) {
            cleanToken = cleanToken.replaceAll(TOKEN_PREFIX, EMPTY_STRING);
        }

        return cleanToken.trim();
    }

}
