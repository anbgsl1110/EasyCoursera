package com.jasonwangex.easyCoursera.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by wangjz
 * on 17/2/22.
 */
public final class SecurityUtil {
    private SecurityUtil() {
    }

    private static MessageDigest MD5Digest;
    private static MessageDigest SHADigest;

    static {
        try {
            MD5Digest = MessageDigest.getInstance("MD5");
            SHADigest = MessageDigest.getInstance("SHA1");

        } catch (NoSuchAlgorithmException ignore) {

        }
    }


    public static String MD5(Object object) {
        try {
            return digest(object, MD5Digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String SHA1(Object object) {
        try {
            return digest(object, SHADigest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String digest(Object object, MessageDigest messageDigest) throws Exception {
        if (object == null || messageDigest == null) {
            return null;
        }
        byte[] bytes;
        if (object instanceof String) {
            bytes = ((String) object).getBytes();
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            bytes = byteArrayOutputStream.toByteArray();
        }

        messageDigest.update(bytes);
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }

    public static void main(String[] args) {

    }

}
