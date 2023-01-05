package ru.liner.facerdecoder.decoder;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 22.12.2022, четверг
 **/
public class HealthDecoder extends Decoder {
    public HealthDecoder(String value) {
        super(value);
    }

    @Override
    public boolean onElement(String element, StringBuilder stringBuilder) {
        if (element.contains("#"))
            element = element.replace("#", "");
        switch (element) {
            case "ZSC":
                stringBuilder.append("1556"); // Step Count
                return true;
            case "ZHR":
                stringBuilder.append("76"); // Average Heart Rate (bpm)
                return true;
            case "ZSTANDVALUE":
                stringBuilder.append("5"); // Current stand value (number of hours where user has stood at least once)
                return true;
            case "ZSTANDGOAL":
                stringBuilder.append("12"); //Current stand goal (number of hours where user has stood at least once)
                return true;
            case "ZMOVEVALUE":
                stringBuilder.append("153.9999"); //Current move value (Cal)
                return true;
            case "ZMOVEGOAL":
                stringBuilder.append("300"); //Current move goal (Cal)
                return true;
            case "ZEXERCISEVALUE":
                stringBuilder.append("13"); //Current exercise value (minutes)
                return true;
            case "ZEXERCISEGOAL":
                stringBuilder.append("30"); //	Current exercise goal (minutes)
                return true;
            default:
                return false;
        }
    }
}
