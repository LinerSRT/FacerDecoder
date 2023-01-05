package ru.liner.facerdecoder.evaluator;


import net.objecthunter.exp4j.function.Function;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 02.01.2023, понедельник
 **/
public class MathEvaluator {
    private static final Pattern bracketPattern = Pattern.compile("\\(([ 0-9a-zA-Z+|\\-|\\/|\\*.]*)\\)");
    private static final Pattern functionPattern = Pattern.compile("[a-zA-Z]([a-zAZ]*)\\(");
    public static final List<MathMethod> methodList = MathMethod.mathMethodList();
    public static final List<Function> functionList = MathMethod.functionList();
    public static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
    private final String value;

    static {
        numberFormat.setMaximumFractionDigits(15);
        numberFormat.setMinimumIntegerDigits(1);
        numberFormat.setGroupingUsed(false);
    }

    public MathEvaluator(String value) {
        this.value = value;
    }

    private boolean shouldEvaluate(String value) {
        return value.contains("+") ||
                value.contains("-") ||
                value.contains("/") ||
                value.contains("*") ||
                value.contains("(") ||
                value.contains(")") ||
                value.contains("[") ||
                value.contains("]");
    }

    public String evaluate() {
        if (!shouldEvaluate(value))
            return value;
        String evaluated = process(parseMethods(value), new MathEvaluateProvider());
        if (evaluated.contains("?") || evaluated.contains(":")) {
            if (evaluated.contains("(") && evaluated.contains(")")) {
                return evaluated.replace("(", "$").replace(")", "$");
            } else {
                return "&" + evaluated + "&";
            }
        } else {
            return evaluated.replace("(", "").replace(")", "");
        }
    }


    private String process(String input, EvaluateProvider provider) {
        String result = input.trim();
        List<String> brackets = new ArrayList<>();
        Matcher matcher = bracketPattern.matcher(input);
        while (matcher.find())
            brackets.add(matcher.group());
        for (String bracket : brackets)
            result = result.replace(bracket, provider.provide(bracket.replace("(", "").replace(")", "")));
        if (shouldEvaluate(result))
            return result.contains("?") || result.contains(":") ? result : result.contains("(") || result.contains(")") ? process(result, provider) : provider.provide(result);
        return result;
    }


    private String parseMethods(String value) {
        value = value.trim();
        String[] method = extractFunctionAndParams(value);
        if (method != null) {
            String methodName = method[0];
            String methodParams = method[1];
            for (MathMethod mathMethod : methodList) {
                if (methodParams.contains(mathMethod.getName()))
                    methodParams = parseMethods(methodParams);
            }
            return MathMethod.eval(methodName + "(" + process(methodParams, new MathEvaluateProvider()) + ")");
        } else {
            return value;
        }
    }


    public static String[] extractFunctionAndParams(String input) {
        Matcher matcher = functionPattern.matcher(input);
        if (matcher.find()) {
            String function = matcher.group().substring(0, matcher.group().length() - 1);
            String rawValue = input.substring(input.indexOf(function), input.lastIndexOf(")"));
            AtomicInteger openParenthesesCount = new AtomicInteger((int) rawValue.chars().filter(character -> character == '(').count());
            StringBuilder contentBuilder = new StringBuilder();
            rawValue.chars()
                    .mapToObj(i -> (char) i)
                    .skip(rawValue.indexOf("(") + 1)
                    .takeWhile(i -> {
                        if (i == ')')
                            openParenthesesCount.getAndDecrement();
                        return openParenthesesCount.get() > 0;
                    })
                    .forEach(contentBuilder::append);
            return new String[]{function, contentBuilder.toString()};
        } else {
            return null;
        }
    }
}
