package com.codingfuns.blog.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class WxUtil {
    public static String getSHA1( String token, String timestamp, String nonce) throws Exception {
        String[] array = new String[]{token, timestamp, nonce};
        StringBuilder sb = new StringBuilder();
        Arrays.sort(array);
        for (int i = 0; i < 3; i++) {
            sb.append(array[i]);
        }
        String str = sb.toString();
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes());
        byte[] digest = md.digest();
        StringBuilder hexstr = new StringBuilder();
        String shaHex = "";
        for (byte aDigest : digest) {
            shaHex = Integer.toHexString(aDigest & 0xFF);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr.toString();
    }
}
