package ru.liner.facerdecoder;

import ru.liner.facerdecoder.decoder.DecoderStack;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String rawInput = "((#DsZ#-1)=(-1)?59:(#DsZ#-1))";
        String rawInput2 = "(219 + sin((#DWFSS#))*10)";
        while (true) {
            System.out.println("[DONE] Result: " + DecoderStack.decode(rawInput2));
            TimeUnit.SECONDS.sleep(1);
        }
//
//
//        String[] inputStrings = {
//                "sin((2*3)-1))",
//                "(219 + sin((#DWFSS#))*10)",
//                "((sin(#DWE#*5))*50+50)",
//                "(100-(interpAccel(#DWE#,0,2,2)*100))",
//                "(100-(interpAccel((2*3)/4)*100))",
//                "(160+cos(#DWFSS#/4)*50)",
//                "(2+2)",
//                "(sin(sin(2) + 2))",
//                "(sin((29 / 2) + (22 + 1)))"
//        };
//        for (String input : inputStrings) {
//            System.out.println(Arrays.toString(extractFunctionAndParams(input)));
//        }
    }
}
