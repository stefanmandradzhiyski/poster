package com.snmi.util;

import com.snmi.exception.ForbiddenException;
import com.snmi.model.AuthenticatedUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.snmi.constants.GlobalConstants.FORBIDDEN_ACCESS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessValidator {

    public static void controlYourData(final String username) {
        if (getLoggedInUser() == null || !getLoggedInUser().getUsername().equals(username)) {
            throw new ForbiddenException(FORBIDDEN_ACCESS);
        }
    }

    public static String getLoggedUsername() {
        return getLoggedInUser() == null ? null : getLoggedInUser().getUsername();
    }

    public static boolean isYours(final String username) {
        return getLoggedInUser() != null && getLoggedInUser().getUsername().equals(username);
    }

    private static AuthenticatedUser getLoggedInUser() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return null;
        }

        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return authenticatedUser;
    }

}
