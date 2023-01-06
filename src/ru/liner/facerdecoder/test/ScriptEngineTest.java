package ru.liner.facerdecoder.test;

import ru.liner.facerdecoder.*;

public class ScriptEngineTest {

    public static void main(String[] args) {
        String[] inputStrings = {
                "(160 + #VAR_2#*5)",
                "$#VAR_3#==0?100:0$",
                "-#DWFMS#",
                "((100)/4)",
                "$(round(#BLN#/100*20))=1?100:0$",
                "(round(#BLN#/100*20))"
        };
        ScriptEngine scriptEngine = new ScriptEngine()
                .addEvaluator(new AstronomyEvaluator())
                .addEvaluator(new DeviceInformationEvaluator())
                .addEvaluator(new InteractionEvaluator())
                .addEvaluator(new SettingsEvaluator())
                .addEvaluator(new TimeEvaluator())
                .addEvaluator(new VarEvaluator())
                .addEvaluator(new WeatherEvaluator())
                .addEvaluator(new MathEvaluator())
                .addEvaluator(new ConditionEvaluator());
        scriptEngine.update(System.currentTimeMillis());
        for (String input : inputStrings)
            System.out.println(scriptEngine.evaluate(input));
    }
}
