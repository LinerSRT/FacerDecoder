package ru.liner.facerdecoder.decoder;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.parser.ParseException;
import ru.liner.facerdecoder.evaluator.ConditionEvaluator;
import ru.liner.facerdecoder.evaluator.MathEvaluator;

import java.math.BigDecimal;


/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 01.01.2023, воскресенье
 **/
public class DecoderStack {
    private BaseDecoder decoder;

    public DecoderStack(String value) {
        this.decoder = new BaseDecoder(value) {
            @Override
            public String maybeDecodeValue() {
                return value;
            }
        };
    }

    public DecoderStack next(Class<? extends BaseDecoder> decoderClass) {
        decoder = decoder.next(decoderClass);
        return this;
    }

    public String getComputedValue() {
        return new ConditionEvaluator(new MathEvaluator(decoder.getComputedValue()).evaluate()).evaluate();
    }

    private static String compute(String value, StringBuilder stringBuilder, Decoder decoder) {
        String decoded = decoder.getComputedValue();
        if (!decoded.equals(value)) {
            stringBuilder.append(decoder.getClass().getSimpleName()).append(" -> [").append(decoded).append("] ");
            return decoded;
        }
        return value;
    }

    public static String decode(String encodedText) {
        long startTime = System.nanoTime();
        StringBuilder stringBuilder = new StringBuilder();
        String rawValue = compute(encodedText, stringBuilder, new UserSettingsDecoder(encodedText));
        rawValue = compute(rawValue, stringBuilder, new UserSettingsDecoder(rawValue));
        rawValue = compute(rawValue, stringBuilder, new HealthDecoder(rawValue));
        rawValue = compute(rawValue, stringBuilder, new DateTimeDecoder(rawValue));
        rawValue = compute(rawValue, stringBuilder, new WeatherDecoder(rawValue));
        rawValue = compute(rawValue, stringBuilder, new AstronomyDecoder(rawValue));
        rawValue = compute(rawValue, stringBuilder, new DeviceDecoder(rawValue));
        String mathEvaluated = new MathEvaluator(rawValue).evaluate();
        if (!mathEvaluated.equals(rawValue)) {
            rawValue = mathEvaluated;
            stringBuilder.append(MathEvaluator.class.getSimpleName()).append(" -> [").append(rawValue).append("] ");
        }
        String conditionEvaluated = new ConditionEvaluator(rawValue).evaluate();
        if (!conditionEvaluated.equals(rawValue)) {
            rawValue = conditionEvaluated;
            stringBuilder.append(ConditionEvaluator.class.getSimpleName()).append(" -> [").append(rawValue).append("] ");
        }
        System.out.println("[INFO]Decode took " + ((System.nanoTime() - startTime) / 1000000f) + " ms. Stack: " + encodedText + " | " + stringBuilder);
        return rawValue;
    }

    public static float decodeToFloat(String encodedText) {
        String result = decode(encodedText);
        try {
            return Float.parseFloat(result);
        } catch (NumberFormatException e) {
            try {
                return ((BigDecimal) new Expression(result).evaluate().getValue()).floatValue();
            } catch (EvaluationException | ParseException ex) {
                ex.printStackTrace();
                return 0f;
            }
        }
    }

    public static int decodeToInt(String encodedText) {
        String result = decode(encodedText);
        try {
            return Integer.parseInt(result);
        } catch (NumberFormatException e) {
            try {
                return ((BigDecimal) new Expression(result).evaluate().getValue()).intValue();
            } catch (EvaluationException | ParseException ex) {
                ex.printStackTrace();
                return 0;
            }
        }
    }
}
