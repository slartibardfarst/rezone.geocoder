package rezone.geocoder.parser.patterns;

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
    private static Set<String> _unitDescriptors;
    private static Set<String> _standard_prefixes;
    private static Set<String> _multidirectionalStarts;
    private static Set<String> _multidirectionalEnds;

    static {
        _states = new HashSet<String>() {{
            add("ak");
            add("alaska");
            add("az");
            add("arizona");
            add("ca");
            add("california");
            add("co");
            add("colorado");
            add("fl");
            add("florida");
            add("ga");
            add("georgia");
            add("id");
            add("idaho");
            add("il");
            add("illinois");
            add("ks");
            add("kansas");
            add("mi");
            add("michigan");
            add("nd");
            add("north dakota");
            add("nh");
            add("new hampshire");
            add("nv");
            add("nevada");
            add("ny");
            add("new york");
            add("oh");
            add("ohio");
            add("or");
            add("oregon");
            add("pa");
            add("pennsylvania");
            add("sc");
            add("south carolina");
            add("tx");
            add("texas");
            add("wa");
            add("washington");
            add("wv");
            add("west virginia");
      }};

        _streetSuffixes = new HashSet<String>() {{
            add("st");
            add("street");
            add("ave");
            add("avenue");
            add("dr");
            add("drive");
            add("rd");
            add("road");
            add("cr");
            add("crescent");
            add("ln");
            add("lane");
            add("highway");
        }};

        _directionals = new HashSet<String>() {{
            add("n");
            add("s");
            add("e");
            add("w");
            add("ne");
            add("nw");
            add("se");
            add("sw");
            add("north");
            add("south");
            add("east");
            add("west");
            add("northeast");
            add("northwest");
            add("southeast");
            add("southwest");
        }};


        _multidirectionalStarts = new HashSet<String>() {{
            add("n");
            add("s");
            add("north");
            add("south");
        }};

        _multidirectionalEnds = new HashSet<String>() {{
            add("e");
            add("w");
            add("east");
            add("west");
        }};

        _standard_prefixes = new HashSet<String>() {{
            add("los");
        }};

        _unitDescriptors = new HashSet<String>() {{
            add("#");
            add("unit");
            add("ste");
        }};
    }

    public static boolean streetNumber1(String s) {
        return s.matches("\\d+\\w{0,3}") ||
                s.matches("\\d+-\\d+");
    }

    //public static boolean streetName1(String s) {        return s.matches("\\d{0,3}?i:\\w+");    }
    public static boolean streetName1(String s) {
        return s.matches("\\w+") &&
               !_standard_prefixes.contains(s.toLowerCase());
    }

    public static boolean streetName2(String s, String t) {
        return s.toLowerCase().matches("[a-z]+") &&
                t.matches("\\w+") &&
                !_directionals.contains(s.toLowerCase()) &&
                !_directionals.contains(t.toLowerCase()) &&
                !streetSuffix1(t);
    }


    public static boolean streetName3(String s, String t, String u) {
        return s.equalsIgnoreCase("state") &&
                t.equalsIgnoreCase("route") &&
                u.matches("\\d+");
    }

    public static boolean city1(String s) {
        return s.toLowerCase().matches("[a-z]+") &&
                !state1(s) &&
                (!s.equalsIgnoreCase("county"));
    }

    public static boolean city2(String s, String t) {
        return s.toLowerCase().matches("[a-z]+") &&
                t.toLowerCase().matches("[a-z]+") &&
                !state2(s, t) &&
                !t.equalsIgnoreCase("county") &&
                !streetSuffix1(s) &&
                !state1(t);
    }

    public static boolean zip1(String s) {
        return s.matches("\\d{5}") || s.matches("\\d{5}\\+\\d{4}");
    }

    public static boolean state1(String s) {
        return _states.contains(s.toLowerCase());
    }

    public static boolean state2(String s, String t) {
        return _states.contains(s.toLowerCase() + " " + t);
    }

    public static boolean streetSuffix1(String s) {
        return _streetSuffixes.contains(s.toLowerCase());
    }

    public static boolean directional1(String s) {
        return _directionals.contains(s.toLowerCase());
    }

    public static boolean directional2(String s,String t) {
        return  _multidirectionalStarts.contains(s.toLowerCase()) &&
                _multidirectionalEnds.contains(t.toLowerCase());
    }


    public static boolean unit1(String s) {return s.matches("\\d{1,3}[a-zA-Z]{0,2}"); }
    public static boolean unit2(String s, String t) {return _unitDescriptors.contains(s.toLowerCase()) && t.matches("\\d+-?\\w*"); }

    public static boolean county2(String s, String t) {return s.toLowerCase().matches("[a-z]+") && t.equalsIgnoreCase("county");}
    public static boolean county3(String s, String t, String u) {return s.toLowerCase().matches("[a-z]+") && t.toLowerCase().matches("[a-z]+") && u.equalsIgnoreCase("county");}
}
