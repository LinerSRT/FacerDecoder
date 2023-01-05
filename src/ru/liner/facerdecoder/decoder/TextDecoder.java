package ru.liner.facerdecoder.decoder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 01.01.2023, воскресенье
 **/
public class TextDecoder {
    private String value;
    private List<Class<? extends BaseDecoder>> decoderList;

    private TextDecoder(Builder builder) {
        this.value = builder.value;
        this.decoderList = builder.decoderList;;
    }

    public String decode(){
        for(Class<? extends BaseDecoder> decoderClass:decoderList){
            try {
                BaseDecoder decoder = decoderClass.getConstructor(String.class).newInstance(value);
                value = decoder.getComputedValue();
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public static class Builder{
        private final String value;
        private final List<Class<? extends BaseDecoder>> decoderList;

        public Builder(String value) {
            this.value = value;
            this.decoderList = new ArrayList<>();
        }

        public Builder withDecoder(Class<? extends BaseDecoder> decoder){
            this.decoderList.add(decoder);
            return this;
        }

        public String decode(){
            return new TextDecoder(this).decode();
        }
    }
}
