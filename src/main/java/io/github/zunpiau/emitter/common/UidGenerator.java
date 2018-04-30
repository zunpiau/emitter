package io.github.zunpiau.emitter.common;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class UidGenerator {

    private static final long twepoch = 1288834974657L; //Thu, 04 Nov 2010 01:42:54 GMT
    private static final long sequenceBits = 6L;
    private static final long sequenceMask = ~(-1L << sequenceBits);
    private static final long timestampLeftShift = sequenceBits;
    private static final int RADIX = 58;
    private static final char[] alphabet = "rpshnaf39wBUDNEGHJKLM4PQRST7VWXYZ2bcdeCg65jkm8oFqi1tuvAxyz".toCharArray();
    private static long lastTimestamp = -1L;
    private long sequence = 0L;

    public String generate() {
        return encode(generateSerial());
    }

    private String encode(long l) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (l < 0);

        if (!negative) {
            l = -l;
        }
        while (l <= -RADIX) {
            buf[charPos--] = alphabet[(int) (-(l % RADIX))];
            l = l / RADIX;
        }
        buf[charPos] = alphabet[(int) (-l)];
        if (negative) {
            buf[--charPos] = '-';
        }
        return new String(buf, charPos, (65 - charPos));
    }

    private synchronized long generateSerial() {

        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generateSerial id for " + (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tailNextMillis(lastTimestamp);
            }
        } else {
            sequence = new SecureRandom().nextInt(10);
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) | sequence;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    private long tailNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }
}
