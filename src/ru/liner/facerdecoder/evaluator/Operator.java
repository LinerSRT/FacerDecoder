package ru.liner.facerdecoder.evaluator;

import java.util.function.BiFunction;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 01.01.2023, воскресенье
 **/
public enum Operator {
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
        for (Operator op : values()) {
            if (op.symbol.equals(symbol)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Invalid operator symbol: " + symbol);
    }

    public boolean eval(String value1, String value2) {
        if (value1.endsWith(".0")) {
            value1 = value1.substring(0, value1.length() - 2);
        }
        if (value2.endsWith(".0")) {
            value2 = value2.substring(0, value2.length() - 2);
        }
        return evaluator.apply(value1, value2);
    }
}