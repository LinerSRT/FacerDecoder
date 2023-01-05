package ru.liner.facerdecoder.evaluator;


import net.objecthunter.exp4j.function.Function;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 02.01.2023, понедельник
 **/
public class MathEvaluator {
    private static final Pattern bracketPattern = Pattern.compile("\\(([0-9a-zA-Z+|\\-|\\/|\\*.]*)\\)");
    public static final String PARAM_SEPARATOR = ",";
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

    public String evaluate() {
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


    private static String process(String input, EvaluateProvider provider) {
        String result = input;
        List<String> brackets = new ArrayList<>();
        Matcher matcher = bracketPattern.matcher(input);
        while (matcher.find())
            brackets.add(matcher.group());
        for (String bracket : brackets)
            result = result.replace(bracket, provider.provide(bracket.replace("(", "").replace(")", "")));
        return result.contains("?") || result.contains(":") ? result : result.contains("(") || result.contains(")") ? process(result, provider) : provider.provide(result);
    }

    private String parseMethods(String value) {
        if (value == null || value.trim().length() <= 0) {
            return null;
        }
        String output = value.trim();
        if (output.length() > 0) {
            int latestIndex;
            do {
                latestIndex = getLatestMethodIndex(output);
                if (latestIndex >= 0) {
                    String methodString = output.substring(latestIndex);
                    int[] indices = getMethodIndices(methodString);
                    if (indices[1] >= 0) {
                        StringBuilder compiledMethodBuilder = new StringBuilder();
                        compiledMethodBuilder.append(methodString.substring(0, indices[0]));
                        compiledMethodBuilder.append("(");
                        String[] params = methodString.substring(indices[0] + 1, indices[1]).split(PARAM_SEPARATOR);
                        for (int i = 0; i < params.length; i++) {
                            if (i > 0) {
                                compiledMethodBuilder.append(PARAM_SEPARATOR);
                            }
                            String evaluatedParam = process(parseMethods(params[i]), new MathEvaluateProvider());
                            ;
                            if (evaluatedParam != null) {
                                compiledMethodBuilder.append(evaluatedParam);
                            }
                        }
                        compiledMethodBuilder.append(")");
                        String methodResult = MathMethod.eval(compiledMethodBuilder.toString());
                        output = output.substring(0, latestIndex) + methodResult + methodString.substring(indices[1] + 1);
                    }
                }
            } while (latestIndex >= 0);
            return output;
        }
        return output;
    }


    private int getLatestMethodIndex(String output) {
        int latestIndex = -1;
        for (MathMethod method : methodList) {
            int lastMethodIndex = output.lastIndexOf(method.getName());
            if (lastMethodIndex > latestIndex)
                latestIndex = lastMethodIndex;
        }
        return latestIndex;
    }

    private int[] getMethodIndices(String methodString) {
        int beginningParanIndex = -1;
        int endingParenIndex = -1;
        int depth = 0;
        int i = 0;
        while (true) {
            if (i >= methodString.length()) {
                break;
            }
            if (methodString.charAt(i) == '(' || methodString.charAt(i) == '[') {
                if (depth == 0) {
                    beginningParanIndex = i;
                }
                depth++;
            } else if ((methodString.charAt(i) == ')' || methodString.charAt(i) == ']') && depth - 1 == 0) {
                endingParenIndex = i;
                break;
            }
            i++;
        }
        return new int[]{beginningParanIndex, endingParenIndex};
    }
}
