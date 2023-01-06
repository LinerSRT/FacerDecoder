package ru.liner.facerdecoder;


@SuppressWarnings({"EnhancedSwitchMigration", "SpellCheckingInspection"})
public class InteractionEvaluator extends VariableEvaluator {
    @Override
    public String evaluateVariable(String variable) {
        String variableToCheck = variable.contains(VARIABLE_ENCODER) ? variable.replace(VARIABLE_ENCODER, "") : variable;
        switch (variableToCheck) {
            //TODO Fill tags here
            default:
                if (variableToCheck.charAt(0) == INTERACTION_MARKER)
                    return "0";
                return variable;
        }
    }
}
