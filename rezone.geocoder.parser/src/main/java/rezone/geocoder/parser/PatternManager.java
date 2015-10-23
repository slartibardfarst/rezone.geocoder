package rezone.geocoder.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awatkins on 15-10-22.
 */
public class PatternManager {
    public static List<Pattern> setupPatterns()
    {
        List<Pattern> patterns = new ArrayList<>();

        Predicate street_no_1 = new Predicate("street_no", (s) -> TokenParserHelpers.streetNumber1(s));

        Predicate street_direction_1 = new Predicate("street_direction", (s) -> TokenParserHelpers.directional1(s));

        Predicate street_1 = new Predicate("street", (s) -> TokenParserHelpers.streetName1(s));
        Predicate street_2 = new Predicate("street", (s, t) -> TokenParserHelpers.streetName2(s, t));

        Predicate street_suffix_1 = new Predicate("street_suffix", (s) -> TokenParserHelpers.streetSuffix1(s));

        Predicate city_1 = new Predicate("city", (s) -> TokenParserHelpers.city1(s));
        Predicate city_2 = new Predicate("city", (s, t) -> TokenParserHelpers.city2(s, t));

        Predicate state_1 = new Predicate("state", (s) -> TokenParserHelpers.state1(s));
        Predicate state_2 = new Predicate("state", (s, t) -> TokenParserHelpers.state2(s, t));
        Predicate zip_1 = new Predicate("zip", s -> TokenParserHelpers.zip1(s));

        Predicate unit_2 = new Predicate("unit", (s, t) -> TokenParserHelpers.unit2(s, t));

        patterns = new ArrayList<Pattern>();

        patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, unit_2, city_2, state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_1, street_suffix_1, unit_2, city_2, state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_1, street_suffix_1, unit_2, city_1, state_1, zip_1}));

        patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_1, state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_2, state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_1, state_2, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_2, state_2, zip_1}));

        patterns.add(new Pattern(new Predicate[]{street_no_1, street_2, street_suffix_1, city_2, state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_1, street_suffix_1, city_1, state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_1, street_suffix_1, city_2, state_1, zip_1}));

        patterns.add(new Pattern(new Predicate[]{state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{state_2, zip_1}));

        patterns.add(new Pattern(new Predicate[]{city_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{city_2, zip_1}));

        patterns.add(new Pattern(new Predicate[]{state_1}));
        patterns.add(new Pattern(new Predicate[]{state_2}));

        patterns.add(new Pattern(new Predicate[]{city_1}));
        patterns.add(new Pattern(new Predicate[]{city_2}));

        return patterns;
    }
}
