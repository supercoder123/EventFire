package com.eventfire.ashley.eventfire;

/**
 * Created by gf on 05-09-2017.
 */
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import android.util.Base64;
//import org.apache.commons.codec.binary.Base64;

// Java example for RSA encryption/decryption.
// Uses Apache commons codec library
// Uses strong encryption with 2048 key size.
public class RSAEncryptionJava8 {

    public static Map<String, Object> initialize() throws Exception{
        Map<String, Object> keys = getRSAKeys();

        PrivateKey privateKey = (PrivateKey) keys.get("private");
        PublicKey publicKey = (PublicKey) keys.get("public");

        //String encryptedText = encryptMessage(plainText, privateKey);
        //String descryptedText = decryptMessage(encryptedText, publicKey);

        return keys;
    }


    // Get RSA keys. Uses key size of 2048.
    private static Map<String, Object> getRSAKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(128);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        Map<String, Object> keys = new HashMap<String, Object>();
        keys.put("private", privateKey);
        keys.put("public", publicKey);
        return keys;
    }

    // Decrypt using RSA public key
    public static String decryptMessage(String encryptedText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //byte[] data = encryptedText.getBytes("UTF-8");
        return new String(cipher.doFinal(Base64.decode(encryptedText,Base64.DEFAULT)));
    }

    // Encrypt using RSA private key
    public static String encryptMessage(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] data = plainText.getBytes("UTF-8");
        return Base64.encodeToString(cipher.doFinal(data),Base64.DEFAULT);
    }
}