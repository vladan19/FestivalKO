package net.etfbl.ip.festivalko.utility;

import java.security.*;
import java.security.cert.Certificate;
import java.util.Base64;

public class CryptographyUtility {
    public static byte[] sign(PrivateKey privateKey, String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        return sign(privateKey, message.getBytes());
    }

    public static byte[] sign(PrivateKey privateKey, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public static boolean verifySignature(Certificate certificate, byte[] signature, String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        return verifySignature(certificate, signature, message.getBytes());
    }

    public static boolean verifySignature(Certificate certificate, byte[] signature, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initVerify(certificate);
        sign.update(data);
        return sign.verify(signature);
    }

    public static String hash(byte[] data, byte[] salt) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(data);
        digest.update(salt);
        return Base64.getEncoder().encodeToString(digest.digest());
    }
    
    public static String hash(byte[] data) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(data);
        return Base64.getEncoder().encodeToString(digest.digest());
    }
}
