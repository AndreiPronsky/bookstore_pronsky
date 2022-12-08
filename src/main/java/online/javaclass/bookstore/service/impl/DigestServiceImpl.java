package online.javaclass.bookstore.service.impl;

import online.javaclass.bookstore.service.DigestService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestServiceImpl implements DigestService {
    @Override
    public String hashPassword(String originalPassword) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytesIn = originalPassword.getBytes();
            messageDigest.update(bytesIn);
            byte[] bytesOut = messageDigest.digest();
            BigInteger bigInteger = new BigInteger(1, bytesOut);
            return bigInteger.toString(16).toUpperCase();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
