package ru.liner.facerdecoder.decoder;


/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 22.12.2022, четверг
 **/
public class WeatherDecoder extends Decoder {

    public WeatherDecoder(String value) {
        super(value);
    }

    @Override
    public boolean onElement(String element, StringBuilder stringBuilder) {
        if(element.contains("#"))
            element = element.replace("#", "");
        switch (element) {
            case "WM":
                stringBuilder.append("F"); //Weather Units (F/C)
                return true;
            case "WLC":
                stringBuilder.append("Los Angeles"); //Weather Location
                return true;
            case "WTH":
                stringBuilder.append("86"); //Today's High
                return true;
            case "WTL":
                stringBuilder.append("63"); //Today's Low
                return true;
            case "WCT":
                stringBuilder.append("84"); //Current Temp
                return true;
            case "WCCI":
                stringBuilder.append("3"); //Current Condition Icon
                return true;
            case "WCCT":
                stringBuilder.append("Fair"); //Current Condition Text
                return true;
            case "WCHN":
                stringBuilder.append("40"); //	Current Humidity Number
                return true;
            case "WCHP":
                stringBuilder.append("40").append("%"); //	Current Humidity Percentage
                return true;
            case "DISDAYTIME":
                stringBuilder.append("TRUE"); //Returns true if time is after sunrise and before sunset
                return true;
            case "WRh":
                stringBuilder.append("5"); //Sunrise hour (1-12)
                return true;
            case "WRhZ":
                stringBuilder.append("05"); //Sunrise hour (leading zero) (01-12)
                return true;
            case "WRH":
                stringBuilder.append("5"); //Sunrise hour (0-23)
                return true;
            case "WRHZ":
                stringBuilder.append("5"); //Sunrise hour (leading zero) (00-23)
                return true;
            case "WRm":
                stringBuilder.append("50"); //Sunrise minute (0-59)
                return true;
            case "WRmZ":
                stringBuilder.append("50"); //Sunrise minute (leading zero) (00-59)
                return true;
            case "WSh":
                stringBuilder.append("8"); //Sunset hour (1-12)
                return true;
            default:
                return false;
        }
    }
}
