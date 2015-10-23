package rezone.geocoder.parser;

import java.util.*;


public class QueryParser {
    private List<MatchPattern> _patterns;

    public QueryParser() {
        SetupTestPatterns();
    }

    public Geo[] parse(String query) {
        return parse(query, null);
    }

    public Geo[] parse(String query, ParseDebug dbg) {

        InputTokens tokenizedQuery = InputTokens.tokenize(query);
        List<MatchPatternResult> matchPatternResults = testPatterns(tokenizedQuery, dbg);

        if(null != dbg) {
            dbg.setInputQuery(query);
            dbg.setInputTokens(tokenizedQuery);
            dbg.buildListOfMatchedPatterns();
        }

        List<Geo> result = new ArrayList<>();
        if (null != matchPatternResults)
            for (MatchPatternResult currMatch : matchPatternResults)
                result.add(new Geo(currMatch));

        return result.toArray(new Geo[0]);
    }


    private List<MatchPatternResult> testPatterns(InputTokens inputTokens, ParseDebug dbg) {
        List<MatchPatternResult> successfulMatches = new ArrayList<>();

        for (MatchPattern currPattern : _patterns) {
            inputTokens.resetCurrPos();
            MatchPatternResult matchPatternResult = currPattern.test(inputTokens, (dbg != null));

            if ((null != matchPatternResult) && (matchPatternResult.Success))
                successfulMatches.add(matchPatternResult);

            if(null != dbg)
                dbg.addPatternMatchResult(matchPatternResult);
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

        _patterns = new ArrayList<MatchPattern>();

        _patterns.add(new MatchPattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, unit_2, city_2, state_1, zip_1}));

        _patterns.add(new MatchPattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_1, state_1, zip_1}));
        _patterns.add(new MatchPattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_2, state_1, zip_1}));
        _patterns.add(new MatchPattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_1, state_2, zip_1}));
        _patterns.add(new MatchPattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_2, state_2, zip_1}));

        _patterns.add(new MatchPattern(new Predicate[]{street_no_1, street_2, street_suffix_1, city_2, state_1, zip_1}));
        _patterns.add(new MatchPattern(new Predicate[]{street_no_1, street_1, street_suffix_1, city_1, state_1, zip_1}));
        _patterns.add(new MatchPattern(new Predicate[]{street_no_1, street_1, street_suffix_1, city_2, state_1, zip_1}));

        _patterns.add(new MatchPattern(new Predicate[]{state_1, zip_1}));
        _patterns.add(new MatchPattern(new Predicate[]{state_2, zip_1}));

        _patterns.add(new MatchPattern(new Predicate[]{city_1, zip_1}));
        _patterns.add(new MatchPattern(new Predicate[]{city_2, zip_1}));

        _patterns.add(new MatchPattern(new Predicate[]{state_1}));
        _patterns.add(new MatchPattern(new Predicate[]{state_2}));
    }
}
