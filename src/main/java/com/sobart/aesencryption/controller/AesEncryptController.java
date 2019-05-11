package com.sobart.aesencryption.controller;

import com.sobart.aesencryption.service.AesEncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AesEncryptController {

    private final String DEFAULT_KEY;

    public AesEncryptController(@Value("${default.key}") final String defaultKey) {
        this.DEFAULT_KEY = defaultKey;
    }

    @Autowired
    private AesEncryptService encryptService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestParam(name = "content", defaultValue = "default") String content,
                          @RequestParam(name = "key", defaultValue = "default") String key,
                          Model model) {

        if (key.equals("default")) {
            String encryptedString = encryptService.encrypt(content, DEFAULT_KEY);
            model.addAttribute("content", content);
            model.addAttribute("key", DEFAULT_KEY);
            model.addAttribute("encrypt", encryptedString);
            return "index";
        } else {
            String encryptedString = encryptService.encrypt(content, key);
            model.addAttribute("content", content);
            model.addAttribute("key", key);
            model.addAttribute("encrypt", encryptedString);
            return "index";
        }
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestParam(name = "contentdec", defaultValue = "default") String contentDec,
                          @RequestParam(name = "keydec", defaultValue = "default") String keyDec,
                          Model model) {

        if (keyDec.equals("default")) {
            String decryptedString = encryptService.decrypt(contentDec, DEFAULT_KEY);
            model.addAttribute("contentdec", contentDec);
            model.addAttribute("keydec", DEFAULT_KEY);
            model.addAttribute("decrypt", decryptedString);
            return "index";
        } else {
            String decryptedString = encryptService.decrypt(contentDec, keyDec);
            model.addAttribute("contentdec", contentDec);
            model.addAttribute("keydec", keyDec);
            model.addAttribute("decrypt", decryptedString);
            return "index";
        }
    }
}
