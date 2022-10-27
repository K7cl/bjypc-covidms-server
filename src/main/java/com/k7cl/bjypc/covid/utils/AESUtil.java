package com.k7cl.bjypc.covid.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

public class AESUtil {

    String aesKey;

    public AESUtil(String aesKey) {
        this.aesKey = aesKey;
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public String encrypt(Object object){
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");

            JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
            byte[] data = jsonObject.toString().getBytes();
            SecretKey key = new SecretKeySpec(aesKey.getBytes(), "AES");
            byte[] ivb = new byte[12];
            new SecureRandom().nextBytes(ivb);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, ivb);
            cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(ivb);
            outputStream.write(cipher.doFinal(data));
            return encodePart(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject decrypt(String encrypted) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");

        byte[] cipherAll = decodePart(encrypted);
        SecretKey key = new SecretKeySpec(aesKey.getBytes(), "AES");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(cipherAll);
        byte[] ivb = new byte[12];
        byte[] cipherText = new byte[cipherAll.length-12];
        inputStream.read(ivb, 0, ivb.length);
        inputStream.read(cipherText, 0, cipherText.length);
        IvParameterSpec iv = new IvParameterSpec(ivb);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        String text = new String(cipher.doFinal(cipherText));
        return JSONObject.parseObject(text);
    }

    private byte[] decodePart(String part) {
        return Base64.getDecoder().decode(Utf8Util.encode(part));
    }

    private String encodePart(byte[] part) {
        return Utf8Util.decode(Base64.getEncoder().encode(part));
    }

}
