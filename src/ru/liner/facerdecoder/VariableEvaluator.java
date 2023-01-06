package ru.liner.facerdecoder;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class VariableEvaluator implements Script<String, String> {
    protected static final String VARIABLE_ENCODER = "#";
    protected static final char TIME_MARKER = 'D';
    protected static final char WEATHER_MARKER = 'W';
    protected static final char ASTRONOMY_MARKER = 'M';
    protected static final char DEVICE_MARKER = 'Z';
    protected static final char VARIABLE_MARKER = 'V';
    protected static final char INTERACTION_MARKER = 'S';
    protected DataProvider dataProvider;
    private final Pattern pattern;
    private final HashMap<String, String> variableMap;

    public VariableEvaluator() {
        this.pattern = Pattern.compile("(\\#[a-zA-Z_0-9]*\\#)");
        this.variableMap = new HashMap<>();
    }

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
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            String variable = matcher.group();
            if (!variableMap.containsKey(variable))
                variableMap.put(variable, evaluateVariable(variable));
        }
        for (String key : variableMap.keySet())
            result = result.replace(key, variableMap.get(key));
        return result;
    }

    @Override
    public void update(long currentTimeMillis) {
        for (String variable : variableMap.keySet())
            variableMap.replace(variable, evaluateVariable(variable));
    }

    public abstract String evaluateVariable(String variable);
}
