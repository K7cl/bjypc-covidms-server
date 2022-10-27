package com.k7cl.bjypc.covid.utils;

import org.bouncycastle.crypto.generators.SCrypt;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class Scrypt {
    private final int cpuCost = 16384;
    private final int memoryCost = 8;
    private final int parallelization = 1;
    private final int keyLength = 32;

    public String encode(CharSequence rawPassword) {
        return digest(rawPassword, genSalt(64));
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null || encodedPassword.length() < this.keyLength) {
            return false;
        }
        return decodeAndCheckMatches(rawPassword, encodedPassword);
    }

    private boolean decodeAndCheckMatches(CharSequence rawPassword, String encodedPassword) {
        String[] parts = encodedPassword.split("\\$");
        if (parts.length != 4) {
            return false;
        }
        long params = Long.parseLong(parts[1], 16);
        byte[] salt = decodePart(parts[2]);
        byte[] derived = decodePart(parts[3]);
        int cpuCost = (int) Math.pow(2, params >> 16 & 0xffff);
        int memoryCost = (int) params >> 8 & 0xff;
        int parallelization = (int) params & 0xff;
        byte[] generated = SCrypt.generate(Utf8Util.encode(rawPassword), salt, cpuCost, memoryCost, parallelization,
                this.keyLength);
        return MessageDigest.isEqual(derived, generated);
    }

    private String digest(CharSequence rawPassword, byte[] salt) {
        byte[] derived = SCrypt.generate(Utf8Util.encode(rawPassword), salt, this.cpuCost, this.memoryCost,
                this.parallelization, this.keyLength);
        String params = Long.toString(
                ((int) (Math.log(this.cpuCost) / Math.log(2)) << 16L) | this.memoryCost << 8 | this.parallelization,
                16);
        StringBuilder sb = new StringBuilder((salt.length + derived.length) * 2);
        sb.append("$").append(params).append('$');
        sb.append(encodePart(salt)).append('$');
        sb.append(encodePart(derived));
        return sb.toString();
    }

    public static byte[] genSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }

    private byte[] decodePart(String part) {
        return Base64.getDecoder().decode(Utf8Util.encode(part));
    }

    private String encodePart(byte[] part) {
        return Utf8Util.decode(Base64.getEncoder().encode(part));
    }
}
