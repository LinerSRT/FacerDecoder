package ru.liner.facerdecoder;


@SuppressWarnings({"EnhancedSwitchMigration", "SpellCheckingInspection"})
public class DeviceInformationEvaluator extends VariableEvaluator {
    @Override
    public String evaluateVariable(String variable) {
        String variableToCheck = variable.contains(VARIABLE_ENCODER) ? variable.replace(VARIABLE_ENCODER, "") : variable;
        switch (variableToCheck) {
            case "BLP":
            case "PBP":
                return "46%";
            case "BLN":
            case "PBN":
                return "46";
            case "BTC":
                return "31°C";
            case "BTCN":
                return "31";
            case "BTI":
                return "87°F";
            case "BTIN":
                return "87";
            case "BS":
                return dataProvider.get("isCharging", false) ? "1" : "0";
            case "ZLP":
                return dataProvider.get("isLowPowerMode", false) ? "true" : "false";
            case "ZDEVICE":
                return dataProvider.get("deviceName", "Undefined");
            case "ZMANU":
                return dataProvider.get("deviceManufacturer", "Undefined");
            case "ZISROUND":
                return dataProvider.get("isScreenRound", false) ? "true" : "false";
            case "PWL":
                return String.valueOf(dataProvider.get("wifiLevel", 0));
            case "ZWC":
                return String.valueOf(dataProvider.get("watchfaceShownCount", 0));
            case "ZSC":
                return String.valueOf(dataProvider.get("stepsCount", 0));
            case "ZHR":
                return String.valueOf(dataProvider.get("heartRate", 0));
            case "ZSTANDVALUE":
                return String.valueOf(dataProvider.get("standValue", 0));
            case "ZSTANDGOAL":
                return String.valueOf(dataProvider.get("standGoalValue", 0));
            case "ZMOVEVALUE":
                return String.valueOf(dataProvider.get("caloriesValue", 0));
            case "ZMOVEGOAL":
                return String.valueOf(dataProvider.get("caloriesGoalValue", 0));
            case "ZEXERCISEVALUE":
                return String.valueOf(dataProvider.get("exerciseMinutes", 0));
            case "ZEXERCISEGOAL":
                return String.valueOf(dataProvider.get("exerciseGoalMinutes", 0));
            default:
                if (variableToCheck.charAt(0) == DEVICE_MARKER)
                    return "0";
                return variable;
        }
    }
}
