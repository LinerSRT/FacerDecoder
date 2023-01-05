package ru.liner.facerdecoder.decoder;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 22.12.2022, четверг
 **/
public class AstronomyDecoder extends Decoder {
    public AstronomyDecoder(String value) {
        super(value);
    }

    @Override
    public boolean onElement(String element, StringBuilder stringBuilder) {
        if (element.contains("#"))
            element = element.replace("#", "");
        switch (element) {
            case "MOONPHASE":
                stringBuilder.append("3"); //The index of the Moon’s current phase (0-7)
                return true;
            case "MOONPHASENAME":
                stringBuilder.append("Waxing Gibbous"); //The name of the Moon’s current phase
                return true;
            case "MOONAGE":
                stringBuilder.append("26.897151414674646"); //The current age of the moon in days
                return true;
            case "MOONAGEPERCENT":
                stringBuilder.append("0.9108234378088014"); //The current age of the moon as a percent
                return true;
            case "MOONISWANING":
                stringBuilder.append("true"); //Boolean indicating if the Moon is waning
                return true;
            case "MOONISWAXING":
                stringBuilder.append("false"); //Boolean indicating if the Moon is waxing
                return true;
            case "MOONDISTANCE":
                stringBuilder.append("67.89485098450928"); //Distance to the Moon from Earth (measured in Earth radii)
                return true;
            case "MOONDISTANCEAPOGEE":
                stringBuilder.append("68.3"); //Maximum distance of the Moon from Earth (measured in Earth radii)
                return true;
            case "MOONDISTANCEPERIGEE":
                stringBuilder.append("56"); //Minimum distance of the Moon from Earth (measured in Earth radii)
                return true;
            case "MOONLN":
                stringBuilder.append("281"); //Current Lunation Number (e.g. 244). This is the number of Moon cycles since the first new Moon of the year 2000.
                return true;
            default:
                return false;
        }
    }
}
