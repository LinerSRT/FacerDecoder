package ru.liner.facerdecoder.utils;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public class MathUtils {
    public static final int BYTES_PER_KB = 1000;
    public static final int BYTES_PER_MB = 1000000;
    public static final float MILLIS_PER_HOUR = 3600000.0f;
    public static final float MILLIS_PER_MINUTE = 60000.0f;
    public static final float MILLIS_PER_SECOND = 1000.0f;
    public static final float ZERO_FLOAT_THRESHOLD = 1.6E-6f;
    public static final Double NANOS_PER_SECOND = 1.0E9d;
    public static final Double MICROS_PER_SECOND = 1000000.0d;
    public static final Double MICROS_PER_UNIT = 1000000.0d;

    public static float clamp(float value, float min, float max) {
        float actualMin = Math.min(min, max);
        float actualMax = Math.max(min, max);
        if (value <= actualMin) {
            value = actualMin;
        }
        if (value >= actualMax) {
            return actualMax;
        }
        return value;
    }

    public static double clamp(double value, double min, double max) {
        double actualMin = Math.min(min, max);
        double actualMax = Math.max(min, max);
        if (value <= actualMin) {
            value = actualMin;
        }
        if (value >= actualMax) {
            return actualMax;
        }
        return value;
    }

    public static double constrainToRange(double value, double srcMin, double srcMax, double destMin, double destMax) {
        double normalizedValue = clamp(value, srcMin, srcMax);
        double actualSrcMin = Math.min(srcMin, srcMax);
        double actualSrcMax = Math.max(srcMin, srcMax);
        double actualDestMin = Math.min(destMin, destMax);
        double actualDestMax = Math.max(destMin, destMax);
        double intermediateValue = (normalizedValue - actualSrcMin) / (actualSrcMax - actualSrcMin);
        return clamp(((actualDestMax - actualDestMin) * intermediateValue) + actualDestMin, destMin, destMax);
    }

    public static boolean isZero(float value) {
        return value <= 1.6E-6f && value >= -1.6E-6f;
    }

}
