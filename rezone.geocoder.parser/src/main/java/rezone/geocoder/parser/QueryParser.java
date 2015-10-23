package rezone.geocoder.parser;

import java.util.*;


public class QueryParser {
    private List<Pattern> _patterns;

    public QueryParser() {
        SetupTestPatterns();
    }

    public Geo[] parse(String query) {
        return parse(query, null);
    }

    public Geo[] parse(String query, ParseDebug dbg) {

        InputTokens tokenizedQuery = InputTokens.tokenize(query);
        List<PatternMatch> patternMatches = testPatterns(tokenizedQuery, dbg);

        if(null != dbg) {
            dbg.setInputQuery(query);
            dbg.setInputTokens(tokenizedQuery);
            dbg.buildListOfMatchedPatterns();
        }

        List<Geo> result = new ArrayList<>();
        if (null != patternMatches)
            for (PatternMatch currMatch : patternMatches)
                result.add(new Geo(currMatch));

        return result.toArray(new Geo[0]);
    }


    private List<PatternMatch> testPatterns(InputTokens inputTokens, ParseDebug dbg) {
        List<PatternMatch> successfulMatches = new ArrayList<>();

        for (Pattern currPattern : _patterns) {
            inputTokens.resetCurrPos();
            PatternMatch patternMatch = currPattern.test(inputTokens, (dbg != null));

            if ((null != patternMatch) && (patternMatch.Success))
                successfulMatches.add(patternMatch);

            if(null != dbg)
                dbg.addPatternMatchResult(patternMatch);
        }

        return successfulMatches;
    }

    private void SetupTestPatterns() {
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

        _patterns = new ArrayList<Pattern>();

        _patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, unit_2, city_2, state_1, zip_1}));
        _patterns.add(new Pattern(new Predicate[]{street_no_1, street_1, street_suffix_1, unit_2, city_2, state_1, zip_1}));
        _patterns.add(new Pattern(new Predicate[]{street_no_1, street_1, street_suffix_1, unit_2, city_1, state_1, zip_1}));

        _patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_1, state_1, zip_1}));
        _patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_2, state_1, zip_1}));
        _patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_1, state_2, zip_1}));
        _patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_2, state_2, zip_1}));

        _patterns.add(new Pattern(new Predicate[]{street_no_1, street_2, street_suffix_1, city_2, state_1, zip_1}));
        _patterns.add(new Pattern(new Predicate[]{street_no_1, street_1, street_suffix_1, city_1, state_1, zip_1}));
        _patterns.add(new Pattern(new Predicate[]{street_no_1, street_1, street_suffix_1, city_2, state_1, zip_1}));

        _patterns.add(new Pattern(new Predicate[]{state_1, zip_1}));
        _patterns.add(new Pattern(new Predicate[]{state_2, zip_1}));

        _patterns.add(new Pattern(new Predicate[]{city_1, zip_1}));
        _patterns.add(new Pattern(new Predicate[]{city_2, zip_1}));

        _patterns.add(new Pattern(new Predicate[]{state_1}));
        _patterns.add(new Pattern(new Predicate[]{state_2}));

        _patterns.add(new Pattern(new Predicate[]{city_1}));
        _patterns.add(new Pattern(new Predicate[]{city_2}));
    }
}
