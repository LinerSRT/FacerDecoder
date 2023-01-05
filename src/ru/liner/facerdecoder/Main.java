package ru.liner.facerdecoder;

import ru.liner.facerdecoder.decoder.DecoderStack;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String rawInput = "((#DsZ#-1)=(-1)?59:(#DsZ#-1))";
        while (true) {
            Thread.sleep(1000);
            System.out.println("[DONE] Result: " + DecoderStack.decode(rawInput));
        }
    }
}
