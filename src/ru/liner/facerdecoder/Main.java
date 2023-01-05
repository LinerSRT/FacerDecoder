package ru.liner.facerdecoder;

import ru.liner.facerdecoder.decoder.DecoderStack;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String[] inputStrings = {
                "null (2 + 2)",
                "sin((2*3)-1))",
                "(219 + sin((#DWFSS#))*10)",
                "((sin(#DWE#*5))*50+50)",
                "(100-(interpAccel(#DWE#,0,2,2)*100))",
                "(100-(interpAccel((2*3)/4)*100))",
                "(160+cos(#DWFSS#/4)*50)",
                "(2+2)",
                "((sin(#DWE#*2)*40)+50)",
                "2 + sin(3 + cos(3))",
                "TEST is 2",
                "TEST is (2)",
                "$#BLN#>=20&&#BLN#<=40?100:0$",
                "$ (1 == 1) || (1 == 1) ? 1 : 0 $"
        };

        for (String input : inputStrings)
            System.out.println("[DONE] Result: " + DecoderStack.decode(input));

    }
}
