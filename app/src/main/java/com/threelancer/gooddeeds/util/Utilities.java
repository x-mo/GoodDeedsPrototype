package com.threelancer.gooddeeds.util;

import java.text.SimpleDateFormat;

public final class Utilities {

    public static String encodeEmail(String email){
        return email.replace(".", ",");
    }
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd \n HH:mm");
}
