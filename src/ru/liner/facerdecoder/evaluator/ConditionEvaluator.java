package ru.liner.facerdecoder.evaluator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 01.01.2023, воскресенье
 **/
public class ConditionEvaluator {
    private static final String SPECIAL_REGEX = "(\\$(-?\\w+\\.?\\d*)(=|==|!=|<=|>=|>|<)(-?\\w+\\.?\\d*)(\\|\\||&&)?(-?\\w+\\.?\\d*)?(=|==|!=|<=|>=|>|<)?(-?\\w+\\.?\\d*)?(\\|\\||&&)?(-?\\w+\\.?\\d*)?(=|==|!=|<=|>=|>|<)?(-?\\w+\\.?\\d*)?\\?([^:\\r\\n]*):([^$\\r\\n]*)\\$)";
    private static final Pattern PATTERN = Pattern.compile(SPECIAL_REGEX);
    private final String value;

    public ConditionEvaluator(String value) {
        this.value = value;
    }

    public String evaluate(){
        Matcher matcher = PATTERN.matcher(value);
        StringBuilder stringBuilder = new StringBuilder();
        int position = 0;
        while (matcher.find()) {
            stringBuilder.append(value, position, matcher.start());
            position = matcher.end();
            Condition[] conditions = new Condition[3];
            for (int i = 0; i < 3; i++) {
                String firstValue = matcher.group(2 + 4 * i);
                String operator = matcher.group(3 + 4 * i);
                String secondValue = matcher.group(4 + 4 * i);
                String trueValue = matcher.group(13);
                String falseValue = matcher.group(14);
                if (firstValue == null || operator == null || secondValue == null)
                    break;
                conditions[i] = new Condition(firstValue, Operator.fromSymbol(operator), secondValue, trueValue, falseValue);
            }
            stringBuilder.append(evaluateConditions(conditions));
        }
        stringBuilder.append(value, position, value.length());
        return stringBuilder.toString();
    }

    private String evaluateConditions(Condition[] conditions) {
        String result = conditions[0].result();
        for (int i = 1; i < conditions.length; i++) {
            if (conditions[i] != null) {
                result = conditions[i - 1].result(conditions[i]);
            }
        }
        return result;
    }

}
