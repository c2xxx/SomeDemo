package com.basedamo.utils;

import android.util.Base64;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


/**
 * Created by hui on 2016/2/1.
 */
public class RSAHelper {


    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;


        keyBytes = Base64.decode(key, Base64.DEFAULT);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decode(key, Base64.DEFAULT);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = Base64.encodeToString(keyBytes, Base64.DEFAULT);
        return s;
    }


    /**
     * RAS DEMO
     *
     * @param text 明文
     * @return
     * @throws Exception
     */
    public static String rsaDemo(String text) throws Exception {

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        //密钥位数
        keyPairGen.initialize(1024);
        //密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        String publicKeyString = getKeyString(publicKey);
        LogController.d("public:\n" + publicKeyString);

        String privateKeyString = getKeyString(privateKey);
        LogController.d("private:\n" + privateKeyString);

        //加解密类
        Cipher cipher = Cipher.getInstance("RSA");//Cipher.getInstance("RSA/ECB/PKCS1Padding");

        //明文
//        byte[] plainText = "我们都很好！邮件：@sina.com".getBytes();
        byte[] plainText = text.getBytes();

        //加密
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] enBytes = cipher.doFinal(plainText);

        //通过密钥字符串得到密钥
        publicKey = getPublicKey(publicKeyString);
        privateKey = getPrivateKey(privateKeyString);

        //解密
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] deBytes = cipher.doFinal(enBytes);

        publicKeyString = getKeyString(publicKey);

        privateKeyString = getKeyString(privateKey);

//        String result = new String(deBytes);

        return "\n加密结果(Base64):\n" + Base64.encodeToString(enBytes,Base64.DEFAULT) + "" +
                "\n解密结果:\n" + new String(deBytes) + "";
    }

}