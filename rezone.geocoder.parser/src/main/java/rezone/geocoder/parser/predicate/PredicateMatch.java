package rezone.geocoder.parser.predicate;

import rezone.geocoder.parser.InputTokens;

import java.util.*;


public class PredicateMatch {
    public String Name;
    public String[] Tokens;

    public PredicateMatch(String name, InputTokens inputTokens, int index, int len)
    {
        Name = name;
        Tokens = Arrays.copyOfRange(inputTokens.Tokens, index, index+len);
    }
}