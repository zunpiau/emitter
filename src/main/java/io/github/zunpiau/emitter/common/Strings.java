package io.github.zunpiau.emitter.common;

public class Strings {

    private static final int STRING_MAX_LENGTH = 256;

    public static String cutoff(String s) {
        if (s.length() > STRING_MAX_LENGTH) {
            return s.substring(0, STRING_MAX_LENGTH);
        } else return s;
    }

}
