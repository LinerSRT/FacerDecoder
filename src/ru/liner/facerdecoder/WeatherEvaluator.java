package ru.liner.facerdecoder;


@SuppressWarnings({"EnhancedSwitchMigration", "SpellCheckingInspection"})
public class WeatherEvaluator extends VariableEvaluator {
    @Override
    public String evaluateVariable(String variable) {
        String variableToCheck = variable.contains(VARIABLE_ENCODER) ? variable.replace(VARIABLE_ENCODER, "") : variable;
        switch (variableToCheck) {
            case "WM":
                return dataProvider.get("weatherUnit", "C");
            case "WLC":
                return dataProvider.get("weatherLocation", "-");
            case "WTH":
                return String.valueOf(dataProvider.get("todayMaxTemp", 30));
            case "WTL":
                return String.valueOf(dataProvider.get("todayMinTemp", 30));
            case "WCT":
                return String.valueOf(dataProvider.get("todayCurrentTemp", 30));
            case "WCCI":
                return String.valueOf(dataProvider.get("currentWeatherConditionIcon", 30));
            case "WCCT":
                return dataProvider.get("currentWeatherConditionText", "fair");
            case "WCHN":
                return String.valueOf(dataProvider.get("currentHumidityNumber", 30));
            case "WCHP":
                return dataProvider.get("currentHumidityNumber", 30) + "%";
            case "DISDAYTIME":
                return "true";
            default:
                if (variableToCheck.charAt(0) == WEATHER_MARKER)
                    return "0";
                return variable;
        }
    }
}
