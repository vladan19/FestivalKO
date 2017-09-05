package net.etfbl.ip.festivalko.utility;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtility {
    private static final int ITERATIONS = 10000;
    private static final int SALT_LENGTH = 64;
    private static final int KEY_LENGTH = 256;
    private static final String DELIMITER = "#";

    public static String generate(String password){
        try {
            byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(SALT_LENGTH);
            return hash(password, salt, ITERATIONS) + DELIMITER + Base64.getEncoder().encodeToString(salt) + DELIMITER + String.valueOf(ITERATIONS);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String hash(String password, byte[] salt, int iterations) throws IllegalArgumentException{
        if(password == null || password.length()==0){
            throw new IllegalArgumentException("Password can't be empty!");
        }
        try{
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, KEY_LENGTH);
            SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean check(String password, String hash) throws IllegalArgumentException{
        String[] hashComponents = hash.split(DELIMITER);
        if(hashComponents.length != 3){
            throw new IllegalArgumentException("Illegal hash format! Not enough informations!");
        }
        String passwordHash = hashComponents[0];
        String salt = hashComponents[1];
        int iterations = Integer.parseInt(hashComponents[2]);
        String computedHash = hash(password, Base64.getDecoder().decode(salt), iterations);
        return computedHash.equals(passwordHash);
    }
}
