package ru.liner.facerdecoder.decoder;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 22.12.2022, четверг
 **/
public abstract class BaseDecoder {
    public static final String TAG = BaseDecoder.class.getSimpleName();
    protected String value;
    protected String[] encodedElements;
    protected boolean isEncoded;

    public BaseDecoder(String value) {
        this.value = value;
        this.encodedElements = allocateEncodedElements();
        this.isEncoded = encodedElements.length != 0 || value.contains("$");
    }

    private String[] allocateEncodedElements() {
        Pattern pattern = Pattern.compile("#\\w*#");
        Matcher matcher = pattern.matcher(value);
        matcher.reset(value);
        ArrayList<String> out = new ArrayList<>();
        while (matcher.find()) {
            out.add(matcher.group());
        }
        return out.toArray(new String[0]);
    }

    public boolean isEncoded() {
        return isEncoded;
    }

    public String getComputedValue() {
        return isEncoded ? maybeDecodeValue() : value;
    }

    public abstract String maybeDecodeValue();

    public BaseDecoder next(Class<? extends BaseDecoder> decoderClass){
        try {
            return decoderClass.getConstructor(String.class).newInstance(getComputedValue());
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return this;
        }
    }
}
