package com.example.bloompoem.util;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpUtil {
    public static String createOTP(int length) {
        Random rnd = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buf.append((char) (rnd.nextInt(26) + 97));
        }
        return buf.toString();
    }
}
