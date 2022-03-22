package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptPassword {

    private static final Logger logger = LogManager.getLogger();

    public static String encodePassword(String password) {
        logger.traceEntry("Encrypting password: {}.", password);
        String encryptedPassword = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            encryptedPassword = s.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        logger.traceExit(encryptedPassword);
        return encryptedPassword;
    }
}