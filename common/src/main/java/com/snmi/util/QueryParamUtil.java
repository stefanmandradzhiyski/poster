package com.snmi.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.snmi.constants.GlobalConstants.DEFAULT_NUMBER;
import static com.snmi.constants.GlobalConstants.EMPTY_STRING;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryParamUtil {

    public static List<String> getListOrSingleton(final List<String> list) {
        return CollectionUtil.isEmpty(list) ? Collections.singletonList(EMPTY_STRING) : list;
    }

    public static Integer getNumberOrZero(final Integer number) {
        return number == null ? DEFAULT_NUMBER : number;
    }

    public static String getStringOrEmpty(final String text) {
        return StringUtil.isEmpty(text) ? EMPTY_STRING : text;
    }

    public static String getStringOrDefault(final String value, final String defaultValue) {
        return StringUtil.isEmpty(value) ? defaultValue : value;
    }

}
