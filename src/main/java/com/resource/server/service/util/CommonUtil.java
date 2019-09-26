package com.resource.server.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommonUtil {

    public static boolean isBlank(String param) {
        return param.isEmpty() || param.trim().equals("");
    }

}
