package ru.liner.facerdecoder.decoder;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 01.01.2023, воскресенье
 **/
public class DeviceDecoder extends Decoder {
    public DeviceDecoder(String value) {
        super(value);
    }

    @Override
    public boolean onElement(String element, StringBuilder stringBuilder) {
        if(element.contains("#"))
            element = element.replace("#", "");
        switch (element) {
            case "PBP":
            case "BLP":
                stringBuilder.append("55").append("%"); //Watch Battery Level Percentage
                return true;
            case "PBN":
            case "BLN":
                stringBuilder.append("55"); //Watch Battery Level Integer
                return true;
            case "BTC":
                stringBuilder.append("31°C"); //Watch Battery Temperature (°C)
                return true;
            case "BTCN":
                stringBuilder.append("31"); //Watch Battery Temperature (Celcius)
                return true;
            case "BTI":
                stringBuilder.append("87°F"); //Watch Battery Temperature (°F)
                return true;
            case "BTIN":
                stringBuilder.append("87"); //Watch Battery Temperature (Fahrenheit)
                return true;
            case "BS":
                stringBuilder.append("0"); //Watch Battery Charging Status (0, if not charging; 1, if charging)
                return true;
            case "ZLP":
                stringBuilder.append("FALSE"); //Low Power Mode (FALSE if not low power; TRUE if low power)
                return true;
            case "ZDEVICE":
                stringBuilder.append("Smartwatch"); //Device Name
                return true;
            case "ZMANU":
                stringBuilder.append("Liner"); //Device Name
                return true;
            case "ZISROUND":
                stringBuilder.append("TRUE"); //Device Screen Shape (true, if circular)
                return true;
            case "PWL":
                stringBuilder.append("3"); //Watch WiFi Level
                return true;
            case "ZWC":
                stringBuilder.append("0"); //Number of watch face activations since synced
                return true;
            default:
                return false;
        }
    }
}
