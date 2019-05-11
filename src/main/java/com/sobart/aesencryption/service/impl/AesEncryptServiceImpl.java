package com.sobart.aesencryption.service.impl;

import com.sobart.aesencryption.service.AesEncryptService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.IntStream;

@Service
public class AesEncryptServiceImpl implements AesEncryptService {

    private static final String ALGORITHM = "AES";
    private static final String MODE = "/CBC";
    private static final String ADDITION = "/PKCS5Padding";
    private static final int DEFAULT_KEY_SIZE = 16;
    private byte[] keyValue;

    public AesEncryptServiceImpl() {
        keyValue = getInitVector(DEFAULT_KEY_SIZE);
    }

    @Override
    public String encrypt(String text, String key) {

        IvParameterSpec ivParameterSpec = new IvParameterSpec(keyValue);
        SecretKeySpec secretKeySpec;
        Cipher cipher;

        try {
            secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), ALGORITHM);
            cipher = Cipher.getInstance(ALGORITHM.concat(MODE).concat(ADDITION));
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            return Base64.encodeBase64String(cipher.doFinal(text.getBytes()));

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException |
                NoSuchPaddingException | InvalidKeyException |
                InvalidAlgorithmParameterException | IllegalBlockSizeException |
                BadPaddingException e) {
            System.out.println("Exception: " + e);
            return e.getMessage();
        }
    }

    @Override
    public String decrypt(String text, String key) {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(keyValue);
        SecretKeySpec secretKeySpec;
        Cipher cipher;

        try {
            secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), ALGORITHM);
            cipher = Cipher.getInstance(ALGORITHM.concat(MODE).concat(ADDITION));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            return new String(cipher.doFinal(Base64.decodeBase64(text)));

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException |
                NoSuchPaddingException | InvalidKeyException |
                InvalidAlgorithmParameterException | IllegalBlockSizeException |
                BadPaddingException e) {
            System.out.println("Exception: " + e);
            return e.getMessage();
        }
    }

    private byte[] getInitVector(int size) {
        byte[] bytes = new byte[size];
        try {
            SecureRandom.getInstanceStrong().nextBytes(bytes);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception: " + e);
        }
        IntStream.range(0, bytes.length).forEach(i -> bytes[i] = (byte) Math.abs((int) bytes[i]));
        return bytes;
    }
}
