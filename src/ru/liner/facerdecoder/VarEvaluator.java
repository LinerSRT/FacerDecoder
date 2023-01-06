package ru.liner.facerdecoder;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings({"EnhancedSwitchMigration", "SpellCheckingInspection"})
public class VarEvaluator extends VariableEvaluator {
    private final Pattern variablePattern = Pattern.compile("VAR_[1-6]");
    private final HashMap<String, VariableHolder> variableHolderMap;

    public VarEvaluator() {
        this.variableHolderMap = new HashMap<>();
        this.variableHolderMap.put("VAR_1", new VariableHolder("VAR_1", 0));
        this.variableHolderMap.put("VAR_2", new VariableHolder("VAR_2", 0));
        this.variableHolderMap.put("VAR_3", new VariableHolder("VAR_3", 0));
        this.variableHolderMap.put("VAR_4", new VariableHolder("VAR_4", 0));
        this.variableHolderMap.put("VAR_5", new VariableHolder("VAR_5", 0));
        this.variableHolderMap.put("VAR_6", new VariableHolder("VAR_6", 0));
    }

    @Override
    public String evaluateVariable(String variable) {
        String variableToCheck = variable.contains(VARIABLE_ENCODER) ? variable.replace(VARIABLE_ENCODER, "") : variable;
        Matcher variableMatcher = variablePattern.matcher(variableToCheck);
        if (variableMatcher.find()) {
            String variableName = variableMatcher.group();
            VariableHolder variableHolder = getVariable(variableName);
            if (variableToCheck.equals(variableName))
                return String.valueOf(variableHolder.getValue());
            if (variableToCheck.length() > variableName.length() + 1)
                switch (variableToCheck.substring(variableName.length() + 1)) {
                    case "T":
                        return String.valueOf(variableHolder.getLasUpdateTime());
                    case "TE":
                        return String.valueOf(System.currentTimeMillis() - variableHolder.getLasUpdateTime());
                }
        }
        if (variableToCheck.charAt(0) == VARIABLE_MARKER)
            return "0";
        return variable;
    }

    public VariableHolder getVariable(String key){
        return variableHolderMap.get(key);
    }

    public void setVariable(VariableHolder variable){
        variableHolderMap.replace(variable.getKey(), variable);
    }

    public static class VariableHolder {
        private final String key;
        private int value;
        private long lasUpdateTime;

        public VariableHolder(String key, int value) {
            this.key = key;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public long getLasUpdateTime() {
            return lasUpdateTime;
        }

        public String getKey() {
            return key;
        }

        public VariableHolder increment() {
            this.lasUpdateTime = System.currentTimeMillis();
            this.value += 1;
            return this;
        }

        public VariableHolder decrement() {
            this.lasUpdateTime = System.currentTimeMillis();
            this.value -= 1;
            return this;
        }

        public VariableHolder reset() {
            this.lasUpdateTime = System.currentTimeMillis();
            this.value = 0;
            return this;
        }

        public VariableHolder toggle() {
            this.lasUpdateTime = System.currentTimeMillis();
            this.value = value == 0 ? 1 : 0;
            return this;
        }
    }
}
