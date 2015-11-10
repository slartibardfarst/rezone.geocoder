package rezone.geocoder.parser;

import rezone.geocoder.parser.patterns.TokenParserHelpers;

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
        inputString = RemoveSpacesBetweenMultiDirectionals(inputString);
        inputString = inputString.replaceAll("(?i)NULL","");
        inputString = inputString.replaceAll("\\s+[\\-]+\\s+"," ");
        inputString = inputString.replaceAll("\\.","");
        inputString = inputString.replaceAll(";",",");
        inputString = inputString.replaceAll(",", " , ");
        inputString = inputString.replaceAll(",\\s*,", ",");
        inputString = inputString.trim();
        inputString = inputString.replaceAll("^,+", "");
        inputString = inputString.replaceAll(",$", "");
        inputString = inputString.trim();
        String[] tokens = inputString.split("\\s+");

        InputTokens result = new InputTokens(tokens);
        return result;
    }

    private static String RemoveSpacesBetweenMultiDirectionals(String addressString)
    {
        if (isNullOrEmpty(addressString))
            return null;

        String[] phrases = addressString.split(",", -1);
        if (phrases.length == 0)
            return addressString;

        ArrayList<String> parts = new ArrayList<String>(Arrays.asList(phrases[0].toLowerCase().split("\\s", -1)));
        for (int i = 0; i < parts.size()-1; i++)
        {
            String test = parts.get(i) + parts.get(i+1);
            if(TokenParserHelpers.getValidDirectionalCombinations().contains(test))
            {
                parts.set(i, parts.get(i) + parts.get(i + 1));
                parts.remove(i + 1);
                phrases[0] = String.join(" ", parts);
                return String.join(",", phrases);
            }
        }

        return addressString;
    }

    private static boolean isNullOrEmpty(String s)
    {
        return (null == s) || s.isEmpty();
    }
}

