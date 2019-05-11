package com.sobart.aesencryption.service.impl;

import com.sobart.aesencryption.service.AesEncryptService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AesEncryptServiceImplTest {

    private AesEncryptService aesEncryptService;
    private String content;
    private String secretKey;
    private String encrypt;

    @Before
    public void setUp() throws Exception {
        aesEncryptService = new AesEncryptServiceImpl();
        content = "Some content for test";
        secretKey = "secretKeyForTest";
        encrypt = aesEncryptService.encrypt(content, secretKey);
    }

    @Test
    public void encryptTest() {
        //GIVEN

        //WHEN
        encrypt = aesEncryptService.encrypt(content, secretKey);
        //THEN
        System.out.println(encrypt);
    }

    @Test
    public void decryptTest() {
        //GIVEN
        String actualContent = content;
        //WHEN
        String expectedContent = aesEncryptService.decrypt(encrypt, secretKey);
        //THEN
        assertEquals(actualContent, expectedContent);
    }
}