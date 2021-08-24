package com.travelsite.travellife.utils;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AesUtil {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    //指定字符串生成密钥
    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            //得到UTF-8格式字节数组
            key = myKey.getBytes("UTF-8");
            //输入字符串进行SHA-1加密
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            //指定128位
            key = Arrays.copyOf(key, 16);
            //构造密钥
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            //定义算法模式补码方式
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //转为字符串
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] result = cipher.doFinal(Base64Utils.decodeFromString(strToDecrypt));
            return new String(result, "UTF-8");
           // return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt.replace(" ", ""))),"UTF-8");
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
