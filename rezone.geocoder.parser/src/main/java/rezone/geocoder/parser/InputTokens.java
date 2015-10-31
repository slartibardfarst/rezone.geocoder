package rezone.geocoder.parser;

import java.util.*;


public class InputTokens {

    public String[] Tokens;
    public int CurrPos;

    public InputTokens(String[] src) {
        for(int i = 0; i < src.length; i++)
            src[i] = src[i].trim();

        Tokens = src;
        CurrPos = 0;
    }

    public int getNumTokens() {
        return Tokens.length;
    }

    public void resetCurrPos() {
        CurrPos = 0;
    }

    public static InputTokens tokenize(String inputString) {
        inputString = inputString.replaceAll("\\.","");
        inputString = inputString.replaceAll(";",",");
        String spacedOutCommas = inputString.replaceAll(",", " , ");
        String[] tokens = spacedOutCommas.split("\\s+");

        InputTokens result = new InputTokens(tokens);
        return result;
    }
}

