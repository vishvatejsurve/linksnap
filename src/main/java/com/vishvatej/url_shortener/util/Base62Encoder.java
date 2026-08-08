package com.vishvatej.url_shortener.util;

import java.util.Base64;

public class Base62Encoder {
    private static final String ALPHABET ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;

    public static String encode(long id)
    {
        if(id==0) return String.valueOf(ALPHABET.charAt(0));

        StringBuilder sb=new StringBuilder();

        while (id>0)
        {
            sb.append(ALPHABET.charAt((int)(id% BASE)));
            id=id/BASE;
        }

        return sb.reverse().toString();
    }


}
