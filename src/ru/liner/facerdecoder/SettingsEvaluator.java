package ru.liner.facerdecoder;


@SuppressWarnings({"EnhancedSwitchMigration", "SpellCheckingInspection"})
public class SettingsEvaluator extends VariableEvaluator {
    @Override
    public String evaluateVariable(String variable) {
        String variableToCheck = variable.contains(VARIABLE_ENCODER) ? variable.replace(VARIABLE_ENCODER, "") : variable;
        switch (variableToCheck) {
            case "DTIMEFORMAT":
                return dataProvider.get("is24HourFormat", true) ? "24" : "12";
            case "UNITSYS":
                return dataProvider.get("unitSystem", "METRIC");
            default:
                return variable;
        }
    }
}
