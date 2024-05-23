package com.encryptors.ws.services;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Random;

@Service
public class EncryptionService {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String AES = "AES";
    private static final String AES_ECB_PKCS7PADDING = "AES/ECB/PKCS7Padding";
    private static final String STATIC_KEY = "STATIC_KEY_12345";

    public String generateKey() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();
        while (key.length() < 16) {
            int index = (int) (rnd.nextFloat() * characters.length());
            key.append(characters.charAt(index));
        }
        return key.toString();
    }

    public String encrypt(String data, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ECB_PKCS7PADDING, "BC");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.encodeBase64String(encryptedData);
    }

    public String decrypt(String data, String key) throws Exception {
        byte[] keyBytes = key.getBytes("UTF-8");
        byte[] keyBytesPadded = new byte[16];
        System.arraycopy(keyBytes, 0, keyBytesPadded, 0, Math.min(keyBytes.length, 16));

        Cipher cipher = Cipher.getInstance(AES_ECB_PKCS7PADDING, "BC");
        SecretKeySpec secretKey = new SecretKeySpec(keyBytesPadded, AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedData = decodeBase64(data);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData, "UTF-8");
    }

    public String encodeKeyForHttpHeader(String key) throws Exception {
        System.out.println();
        String base64Key = Base64.encodeBase64String(key.getBytes());
        return base64Key;
    }


    public byte[] decodeBase64(String data) {
        Base64 base64 = new Base64();
        return base64.decode(data);
    }

    public String decodeKeyForBase64(String key) {
        System.out.println("Base64 Encoded key: " + key);
        try {
            byte[] decodedBytes = decodeBase64(key);
            String decodedString = new String(decodedBytes, "UTF-8");
            System.out.println("Decoded base64 key: " + decodedString);
            return decodedString;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Base64 encoded string: " + e.getMessage());
            return null;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
