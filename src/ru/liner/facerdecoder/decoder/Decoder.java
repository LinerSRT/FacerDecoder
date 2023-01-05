package ru.liner.facerdecoder.decoder;


/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 22.12.2022, четверг
 **/
public abstract class Decoder extends BaseDecoder {

    public Decoder(String value) {
        super(value);
    }

    @Override
    public String maybeDecodeValue() {
        String computedValue = value;
        StringBuilder stringBuilder;
        for(String element:encodedElements){
            stringBuilder = new StringBuilder();
            if(onElement(element, stringBuilder))
                computedValue = computedValue.replace(element, stringBuilder.toString());
        }
        return computedValue;
    }

    public abstract boolean onElement(String element, StringBuilder stringBuilder);
}
