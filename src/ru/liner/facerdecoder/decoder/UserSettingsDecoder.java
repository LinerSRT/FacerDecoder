package ru.liner.facerdecoder.decoder;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 22.12.2022, четверг
 **/
public class UserSettingsDecoder extends Decoder {
    public UserSettingsDecoder(String value) {
        super(value);
    }

    @Override
    public boolean onElement(String element, StringBuilder stringBuilder) {
        if (element.contains("#"))
            element = element.replace("#", "");
        switch (element) {
            case "DTIMEFORMAT":
                stringBuilder.append("24"); // 24 or 12
                return true;
            case "UNITSYS":
                stringBuilder.append("METRIC"); // METRIC or IMPERIAL
                return true;
            default:
                return false;
        }
    }
}
