package com.snmi.util;

import com.snmi.exception.UnProcessableEntityException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.snmi.constants.GlobalConstants.DEFAULT_COUNT_NUMBER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserValidator {

    private static final String INVALID_USERNAME = "The username must contain only letter and digits. Used value: %s";
    private static final String INVALID_PASSWORD = "The password must not contain white spaces. Used value: %s";

    public static void validateUsername(final String username) {
        for (int index = DEFAULT_COUNT_NUMBER; index < username.length(); index++) {
            if ((!Character.isLetterOrDigit(username.charAt(index)))) {
                throw new UnProcessableEntityException(String.format(INVALID_USERNAME, username));
            }
        }
    }

    public static void validatePassword(final String password) {
        for (int index = DEFAULT_COUNT_NUMBER; index < password.length(); index++) {
            if (Character.isWhitespace(password.charAt(index)) || Character.isSpaceChar(password.charAt(index))) {
                throw new UnProcessableEntityException(String.format(INVALID_PASSWORD, password));
            }
        }
    }

}
