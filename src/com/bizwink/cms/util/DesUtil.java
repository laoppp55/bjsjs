package com.bizwink.cms.util;


import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

//import java.util.HashMap;

//import org.joda.time.DateTime;

//import com.hywebchina.ksfoa.util.StringUtils;

public class DesUtil {

    private static final Map<String, SecretKey> SECRETKEYS = new HashMap<String, SecretKey>();

    private static final SecretKey getSecretKey(String key) throws InvalidKeySpecException, NoSuchAlgorithmException,
            InvalidKeyException, UnsupportedEncodingException {
        if (SECRETKEYS.containsKey(key))
            return SECRETKEYS.get(key);

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        SECRETKEYS.put(key, secretKey);
        return secretKey;
    }

    /**
     * 解碼
     * 
     * @param message
     * @param key
     */
    public static String decrypt(String message, String key) throws IllegalBlockSizeException, BadPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException, NoSuchAlgorithmException,
            UnsupportedEncodingException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key), iv);
        byte[] bytesrc = convertHexString(message);
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }

    /**
     * 編碼
     * 
     * @param message
     * @param key
     */
    public static byte[] encrypt(String message, String key) throws IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException,
            InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key), iv);
        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    public static String encryptToString(String value, String key) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        return toHexString(encrypt(URLEncoder.encode(value, "utf-8"), key)).toUpperCase();
    }

    public static String decryptToString(String value, String key) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        return URLDecoder.decode(decrypt(value, key), "utf-8");
    }

    public static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }

    public static String toHexString(byte b[]) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }
        return hexString.toString();
    }

    public static void main(String[] args) throws Exception {
        String testValue = "10400700";
        System.out.println("testValue：" + testValue);
        String encryptResult = encryptToString(testValue, "K1O2N3G4");
        System.out.println("encryptResult：" + encryptResult);
        String decryptResult = decryptToString(encryptResult, "K1O2N3G4");
        System.out.println("decryptResult：" + decryptResult);
       // String now = new DateTime().toString("yyyyMMddHHmmss");
        //String nowStr = DesUtil.encryptToString(StringUtils.safeToString(now), "K1O2N3G4");
        //System.out.println(nowStr);
    }

}
