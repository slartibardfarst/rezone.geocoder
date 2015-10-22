package rezone.geocoder.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by awatkins on 15-10-21.
 */
public class TokenParserHelpers {
    private static Set<String> _states;
    private static Set<String> _streetSuffixes;
    private static Set<String> _directionals;

    static {
        _states = new HashSet<String>() {{
            add("wa"); add("washington");
            add("or"); add("oregon");
            add("ca"); add("california");
            add("tx"); add("texas");
            add("az"); add("arizona");
            add("ny"); add("new york");
            add("nh"); add("new hampshire");
            add("nd"); add("north dakota");
            add("fl"); add("florida");
            add("nv"); add("nevada");
            add("ga"); add("georgia");
        }};

        _streetSuffixes = new HashSet<String>() {{
            add("st"); add("street");
            add("ave"); add("avenue");
            add("dr"); add("drive");
            add("rd"); add("road");
            add("cr"); add("crescent");
        }};

        _directionals = new HashSet<String>() {{
            add("n"); add("s"); add("e"); add("w");
            add("ne"); add("nw"); add("se"); add("sw");
            add("north"); add("south"); add("east"); add("west");
            add("northeast"); add("northwest"); add("southeast"); add("southwest");
        }};
    }

    public static boolean streetNumber1(String s){
        return s.matches("\\d+");
    }

    public static boolean streetName1(String s){
        return s.matches("\\w+");
    }

    public static boolean streetName2(String s, String t){
        return s.matches("\\w+") && t.matches("\\w+");
    }

    public static boolean city1(String s){
        return s.matches("\\w+");
    }

    public static boolean city2(String s, String t){
        return s.matches("\\w+") && t.matches("\\w+");
    }

    public static boolean zip1(String s) {
        return s.matches("\\d{5}");
    }

    public static boolean state1(String s) {
        return _states.contains(s.toLowerCase());
    }

    public static boolean state2(String s, String t) {
        return _states.contains(s.toLowerCase() + " " + t);
    }

    public static boolean streetSuffix1(String s)
    {
        return _streetSuffixes.contains(s.toLowerCase());
    }

    public static boolean directional1(String s)
    {
        return _directionals.contains(s.toLowerCase());
    }

    public static boolean street1(String s)
    {
        return s.matches("\\d{0,3}?i:\\w+");
    }

}
