package rezone.geocoder.parser;

import java.util.*;


public class InputTokens {

    public String[] Tokens;
    public int CurrPos;

    public InputTokens(String[] src) {
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
        String[] tokens = inputString.split("\\s+|,\\s*");

        InputTokens result = new InputTokens(tokens);
        return result;
    }
}

