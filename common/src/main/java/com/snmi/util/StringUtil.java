package com.snmi.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

    public static boolean isEmpty(final String text) {
        return text == null || text.length() == 0 || text.trim().length() == 0;
    }

}
