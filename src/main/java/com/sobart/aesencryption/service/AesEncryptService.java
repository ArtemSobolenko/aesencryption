package com.sobart.aesencryption.service;

public interface AesEncryptService {
    /**
     *  this method encrypting content via AES algorithm
     * @param text
     * @param key
     */
    String encrypt(String text, String key);

    /**
     * this method decrypting content via AES algorithm
     *
     * @param text The data to decrypt
     * @param key
     */
    String decrypt(String text, String key);
}
