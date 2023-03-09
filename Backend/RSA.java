package Backend;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private static final String PRIVATE_KEY_STRING = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJOEjaDx0eKZiYfIElPgd9lsLNZDOrBHBnrKJWA3vPC7nqA6vyhKJ5dXU8eLOHuBiJiwyxPPxH5K/EipUl07Ck6714X0s6lDom2bB4f6MwOcnI2KEN8IbMsPT3fpLi0Xp7kXgCvLOwi/Ae0xnCFt2Vka9BWBiZk0uZazCmkM+09vAgMBAAECgYAC/60c9vLO3kI2OKIxJOf2hwMNp5O+Ac/gvdtu+0Dysua/SyjTkFesSpyYnO/t91VdWk84HNkEaSYmWGIC4G5WaXnxfS6BPaPJQl0+cGhRwa719M5yBM5oQ12OE47XeUcbnRRk7AneEF9HdiMtFQL5Mkxpiyk/UKyN1//Xf3yLJQJBAKfL/1o4lfEyuN+sw/6v5shadlJ8b+q1HmQl93TUamt3tkkYahiGPA25cvObsoK75+Tub56rJWj93z5j0UpJh+UCQQDhD6arfo/iJydMxes+Ttvad8w698gU+2jrMtu26S50/YazKySiWwdvJYynegiqtKMtecl0FbwH0HkE/X5qcNzDAkAIjyhcYsyLPCogBsEMqc5c4/hrJrvSviBpvWINDJTNHJMo0V0AGUDQ9me9s3B8qLcgf2UkM9i5rVGI7jCL2IpdAkEA0WXufMFUveYP+q5rIZ3dkHbF3leMIXmJNMwtARIT12XLXghWHXj0gcU+CNTSG4HWEQmvFgLi36Jn9uykIgTAYQJAGi8r0fDyKHTBov8Fvvo7nHOEkLmmXFvQE+ihSAv/vb8I+XGDgRM2RoriyBGqfRzjudqaTLY7ff4FNslxvaspGA==";
    private static final String PUBLIC_KEY_STRING = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCThI2g8dHimYmHyBJT4HfZbCzWQzqwRwZ6yiVgN7zwu56gOr8oSieXV1PHizh7gYiYsMsTz8R+SvxIqVJdOwpOu9eF9LOpQ6JtmweH+jMDnJyNihDfCGzLD0936S4tF6e5F4AryzsIvwHtMZwhbdlZGvQVgYmZNLmWswppDPtPbwIDAQAB";

    public void initFromStrings() {
        try {
            // KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            // generator.initialize(1024);
            // KeyPair pair = generator.generateKeyPair();
            // privateKey = pair.getPrivate();
            // publicKey = pair.getPublic();

            // FileWriter fileWriter = new FileWriter("Backend/RSA_public_key.pem");
            // String pemPrivate = "-----BEGIN PRIVATE KEY-----\n"
            // + Base64.getEncoder().encodeToString(privateKey.getEncoded())
            // + "\n-----END PRIVATE KEY-----\n";
            // fileWriter.write(pemPrivate);
            // fileWriter.close();

            // fileWriter = new FileWriter("Backend/public_key.pem");
            // String pemPublic = "-----BEGIN PRIVATE KEY-----\n"
            // + Base64.getEncoder().encodeToString(publicKey.getEncoded())
            // + "\n-----END PRIVATE KEY-----\n";
            // fileWriter.write(pemPublic);
            // fileWriter.close();
            // String key = new String(Files.readAllBytes(file.toPath("RSA_private_key.pem")
            // Charset.defaultCharset());

            X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(PUBLIC_KEY_STRING));
            PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(PRIVATE_KEY_STRING));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            publicKey = keyFactory.generatePublic(keySpecPublic);
            privateKey = keyFactory.generatePrivate(keySpecPrivate);

        } catch (Exception ignored) {
        }
    }

    public void printKeys() {
        System.err.println("Public key\n" + encode(publicKey.getEncoded()));
        System.err.println("Private key\n" + encode(privateKey.getEncoded()));
    }

    public String encrypt(String message) throws Exception {
        byte[] messageToBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(messageToBytes);
        return encode(encryptedBytes);
    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public String decrypt(String encryptedMessage) throws Exception {
        byte[] encryptedBytes = decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
        return new String(decryptedMessage, "UTF8");
    }

    public static String RSAEncryption(String message) {
        RSA rsa = new RSA();
        rsa.initFromStrings();

        try {
            String encryptedMessage = rsa.encrypt(message);

            System.out.println(encryptedMessage);
            return encryptedMessage;

        } catch (Exception ingored) {
            System.out.println(ingored);

            return "ERROR";

        }

    }

    public static String RSADecryption(String encryptedMessage) {
        RSA rsa = new RSA();
        rsa.initFromStrings();

        try {

            String decryptedMessage = rsa.decrypt(encryptedMessage);

            System.out.println(decryptedMessage);
            return decryptedMessage;

        } catch (Exception ingored) {
            System.out.println(ingored);
            return "Error";

        }
    }
}

// QxgJToGGffgQeJRaV34mib5DPsoMTs+W7H4bigIQXKcm4Uc+kSWqFiRmcAf3GFfm7SGO1nmPm1RfnPmcRO8ekkFX3f7q99PDG1ZLReJJeCCDyV4j45YjQ2P3zkPWSUT8v2Ghq0UCf7dFc+vod8/gk/BrVXgtxW3YvO9MsmbZ8t4=