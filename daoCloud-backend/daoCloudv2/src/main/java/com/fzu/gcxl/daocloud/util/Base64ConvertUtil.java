package com.fzu.gcxl.daocloud.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64ConvertUtil {
    private Base64ConvertUtil() {
    }

    public static String encode(String str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes("utf-8"));
        return new String(encodeBytes);
    }

    public static String decode(String str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes("utf-8"));
        return new String(decodeBytes);
    }
}
