package ru.liner.facerdecoder;

import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionEvaluator implements Script<String, String> {
    private final Pattern conditionPattern = Pattern.compile("(\\$(-?\\w+\\.?\\d*)(=|==|!=|<=|>=|>|<)(-?\\w+\\.?\\d*)(\\|\\||&&)?(-?\\w+\\.?\\d*)?(=|==|!=|<=|>=|>|<)?(-?\\w+\\.?\\d*)?(\\|\\||&&)?(-?\\w+\\.?\\d*)?(=|==|!=|<=|>=|>|<)?(-?\\w+\\.?\\d*)?\\?([^:\\r\\n]*):([^$\\r\\n]*)\\$)");
    private DataProvider dataProvider;

    public DataProvider getDataProvider() {
        return dataProvider;
    }

    @Override
    public void setSettings(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public boolean shouldEvaluate(String value) {
        return conditionPattern.matcher(value).find();
    }

    @Override
    public String evaluate(String value) {
        if (shouldEvaluate(value)) {
            Matcher matcher = conditionPattern.matcher(value);
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
        return value;
    }

    @Override
    public void update(long currentTimeMillis) {

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

    private static class Condition {
        public final String firstValue;
        public final Operator operator;
        public final String secondValue;
        public final String trueValue;
        public final String falseValue;
        public boolean result;

        Condition(String firstValue, Operator operator, String secondValue, String trueValue, String falseValue) {
            this.firstValue = firstValue;
            this.operator = operator;
            this.secondValue = secondValue;
            this.trueValue = trueValue;
            this.falseValue = falseValue;
            this.result = operator.eval(firstValue, secondValue);
        }


        public String result(Condition condition) {
            boolean eval = operator == Operator.OR ? condition.result || result : result && condition.result;
            return eval ? trueValue : falseValue;
        }

        public String result() {
            return result ? trueValue : falseValue;
        }
    }

    private enum Operator {
        OR("||", String::matches),
        EQ("=", String::matches),
        EQD("==", String::matches),
        NEQ("!=", (value1, value2) -> !value1.matches(value2)),
        LT("<", (value1, value2) -> Float.parseFloat(value1) < Float.parseFloat(value2)),
        GT(">", (value1, value2) -> Float.parseFloat(value1) > Float.parseFloat(value2)),
        LTE("<=", (value1, value2) -> Float.parseFloat(value1) <= Float.parseFloat(value2)),
        GTE(">=", (value1, value2) -> Float.parseFloat(value1) >= Float.parseFloat(value2));

        private final String symbol;
        private final BiFunction<String, String, Boolean> evaluator;

        Operator(String symbol, BiFunction<String, String, Boolean> evaluator) {
            this.symbol = symbol;
            this.evaluator = evaluator;
        }

        public static Operator fromSymbol(String symbol) {
            for (Operator op : values())
                if (op.symbol.equals(symbol))
                    return op;
            throw new IllegalArgumentException("Invalid operator symbol: " + symbol);
        }

        public boolean eval(String value1, String value2) {
            return evaluator.apply(value1.replace(".0", ""), value2.replace(".0", ""));
        }
    }
}
