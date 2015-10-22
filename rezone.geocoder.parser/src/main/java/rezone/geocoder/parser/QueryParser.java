package rezone.geocoder.parser;

import com.sun.javafx.fxml.expression.Expression;

import java.util.*;


public class QueryParser {
    private List<MatchPattern> _patterns;

    public QueryParser() {
        SetupTestPatterns();
    }


    public Geo[] parse(String query) {

        InputTokens tokenizedQuery = InputTokens.tokenize(query);
        List<MatchResult> matchResults = testPatterns(tokenizedQuery);

        List<Geo> result = new ArrayList<>();
        if (null != matchResults)
            for (MatchResult currMatch : matchResults)
                result.add(new Geo(currMatch));

        return result.toArray(new Geo[0]);
    }


    private List<MatchResult> testPatterns(InputTokens inputTokens) {
        List<MatchResult> result = null;

        for (MatchPattern currPattern : _patterns) {
            inputTokens.resetCurrPos();
            MatchResult matchResult = currPattern.test(inputTokens);
            if (null != matchResult) {
                if (null == result)
                    result = new ArrayList<>();

                result.add(matchResult);
            }
        }

        return result;
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

        _patterns = new ArrayList<MatchPattern>();

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
