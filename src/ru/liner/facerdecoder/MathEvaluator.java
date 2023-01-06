package ru.liner.facerdecoder;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathEvaluator implements Script<String, String> {
    private final Pattern pattern = Pattern.compile("\\(([^=?:$#]+)\\)");
    private final List<Function> functionList = MathMethod.functionList();
    private final NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
    private DataProvider dataProvider;
    @Override
    public DataProvider getDataProvider() {
        return dataProvider;
    }

    @Override
    public void setSettings(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public boolean shouldEvaluate(String value) {
        return pattern.matcher(value).find();
    }

    @Override
    public String evaluate(String value) {
        String result = value;
        if(shouldEvaluate(result)){
            Matcher matcher = pattern.matcher(result);
            while (matcher.find()){
                String evaluate = matcher.group();
                if(evaluate.length() != 0) {
                    try {
                        Expression expression = new ExpressionBuilder(evaluate)
                                .variables("pi", "e")
                                .functions(functionList)
                                .build()
                                .setVariable("pi", Math.PI)
                                .setVariable("e", Math.E);
                        String evaluationResult = numberFormat.format(expression.evaluate()).replace(".0", "");
                        result = result.replace(evaluate, evaluationResult);
                    } catch (Exception e) {
                        result = result.replace(evaluate, "0");
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void update(long currentTimeMillis) {

    }
}
