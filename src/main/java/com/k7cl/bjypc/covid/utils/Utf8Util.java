package com.k7cl.bjypc.covid.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Utf8Util {
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    /**
     * Get the bytes of the String in UTF-8 encoded form.
     */
    public static byte[] encode(CharSequence string) {
        try {
            ByteBuffer bytes = CHARSET.newEncoder().encode(CharBuffer.wrap(string));
            byte[] bytesCopy = new byte[bytes.limit()];
            System.arraycopy(bytes.array(), 0, bytesCopy, 0, bytes.limit());
            return bytesCopy;
        }
        catch (CharacterCodingException ex) {
            throw new IllegalArgumentException("Encoding failed", ex);
        }
    }

    /**
     * Decode the bytes in UTF-8 form into a String.
     */
    public static String decode(byte[] bytes) {
        try {
            return CHARSET.newDecoder().decode(ByteBuffer.wrap(bytes)).toString();
        }
        catch (CharacterCodingException ex) {
            throw new IllegalArgumentException("Decoding failed", ex);
        }
    }
}
