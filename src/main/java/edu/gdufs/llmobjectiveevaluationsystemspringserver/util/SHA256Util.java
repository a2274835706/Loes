package edu.gdufs.llmobjectiveevaluationsystemspringserver.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256工具类
 * <p>使用 {@link #toSHA256(String)} 将字符串转换为 64 位哈希值</p>
 */

public class SHA256Util {

    static public String toSHA256(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(toSHA256("123456"));
        System.out.println(toSHA256("1899740163576168450"));
    }

}
