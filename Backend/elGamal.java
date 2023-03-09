package Backend;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class elGamal {

    private static final String PRIVATE_KEY_STRING = "MIIBogIBADCCARQGBisOBwIBATCCAQgCgYEA/X9TgR11EilS30qcLuzk5/YRt1I870QAwx4/gLZRJmlFXUAiUftZPY1Y+r/F9bow9subVWzXgTuAHTRv8mZgt2uZUKWkn5/oBHsQIsJPu6nX/rfGG/g7V+fGqKYVDwT7g/bTxR7DAjVUE1oWkTL2dfOuK2HXKu/yIgMZndFIAccCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEgYQCgYEA1JIy49HU+eRfzbX3T5jwE64lJlOxeMdXG+11ywNP2Q8tm0PYFhzzHlfU/gnZB/v8kcAOg6QtS7KEUT21RO16SxcGfw/OC/OJiFgVsK1U5p6Dwo/W/9B3DD6PGzxiq69nR98yankVBIdvMkw9tF8BB5EewsI09pZWpGes4UG+r/Y=";
    private static final String PUBLIC_KEY_STRING = "MIIBoDCCARQGBisOBwIBATCCAQgCgYEA/X9TgR11EilS30qcLuzk5/YRt1I870QAwx4/gLZRJmlFXUAiUftZPY1Y+r/F9bow9subVWzXgTuAHTRv8mZgt2uZUKWkn5/oBHsQIsJPu6nX/rfGG/g7V+fGqKYVDwT7g/bTxR7DAjVUE1oWkTL2dfOuK2HXKu/yIgMZndFIAccCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYUAAoGBAKpm2QdQbiYoDtlE77SGtJbluzvZo90Mocg3x7NKxEef2m5Gf19fD0cKmOlKrrlE5WiYyXHVxVBXX6rZZXKn30GFUUhRH13QFW0k2v8olwpGpP7vE0fMgdIIf0TLFjMH6Wao5cTEIxU4t0jVLhD4wXg+IZM6Dx/El594NGznZXrH";

    private PrivateKey privateKey;
    private PublicKey publicKey;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    }

    public void initFromStrings() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("ELGAMAL", "BC");
            generator.initialize(1024);
            KeyPair pair = generator.generateKeyPair();
            publicKey = pair.getPublic();
            privateKey = pair.getPrivate();

            // KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            // generator.initialize(1024);
            // KeyPair pair = generator.generateKeyPair();
            // privateKey = pair.getPrivate();
            // publicKey = pair.getPublic();

            // FileWriter fileWriter = new FileWriter("Backend/ELGAMAL_public_key.pem");
            // String pemPrivate = "-----BEGIN PUBLIC KEY-----\n"
            // + Base64.getEncoder().encodeToString(publicKey.getEncoded())
            // + "\n-----END PUBLIC KEY-----\n";
            // fileWriter.write(pemPrivate);
            // fileWriter.close();

            // fileWriter = new FileWriter("Backend/ELGAMALprivate_key.pem");
            // String pemPublic = "-----BEGIN PRIVATE KEY-----\n"
            // + Base64.getEncoder().encodeToString(privateKey.getEncoded())
            // + "\n-----END PRIVATE KEY-----\n";
            // fileWriter.write(pemPublic);
            // fileWriter.close();

            X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(PUBLIC_KEY_STRING));
            PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(PRIVATE_KEY_STRING));

            KeyFactory keyFactory = KeyFactory.getInstance("ELGAMAL");

            publicKey = keyFactory.generatePublic(keySpecPublic);
            privateKey = keyFactory.generatePrivate(keySpecPrivate);
        } catch (Exception ignored) {
            System.out.println("---- " + ignored);
        }
    }

    public void printKeys() {
        System.err.println("Public key\n" + encode(publicKey.getEncoded()));
        System.err.println("Private key\n" + encode(privateKey.getEncoded()));
    }

    public String encrypt(String message) throws Exception {
        byte[] messageToBytes = message.getBytes();

        Cipher cipher = Cipher.getInstance("ELGamal/ECB/PKCS1padding");
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
        Cipher cipher = Cipher.getInstance("ELGAMAL/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
        return new String(decryptedMessage, "UTF8");
    }

    public static String ElgamalEncryption(String message) {
        elGamal elgamal = new elGamal();
        elgamal.initFromStrings();

        try {
            String encryptedMessage = elgamal.encrypt(message);
            return encryptedMessage;
        } catch (Exception ingored) {
            System.out.println(ingored);
            return ingored.getLocalizedMessage();
        }

    }

    public static String ElgamalDecryption(String encryptedMessage) {
        elGamal elgamal = new elGamal();
        elgamal.initFromStrings();

        try {
            String decryptedMessage = elgamal.decrypt(encryptedMessage);
            return decryptedMessage;
        } catch (Exception ingored) {
            System.out.println(ingored);
            return "Error";
        }
    }
}