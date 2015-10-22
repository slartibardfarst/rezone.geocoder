package rezone.geocoder.parser;
import java.util.*;


public class QueryParser {
    private List<MatchPattern> _patterns;

    public QueryParser()
    {
        SetupTestPatterns();
    }


    public Geo[] parse(String query) {

        InputTokens tokenizedQuery = InputTokens.tokenize(query);
        List<MatchResult> matchResults = testPatterns(tokenizedQuery);

        List<Geo> result = new ArrayList<>();
        if(null != matchResults)
            for(MatchResult currMatch: matchResults)
            result.add(new Geo(currMatch));

        return result.toArray(new Geo[0]);
    }


    private List<MatchResult> testPatterns(InputTokens inputTokens)
    {
        List<MatchResult> result = null;

        for (MatchPattern currPattern : _patterns) {
            MatchResult matchResult = currPattern.test(inputTokens);
            if (null != matchResult) {
                if(null == result)
                    result = new ArrayList<>();

                result.add(matchResult);
            }
        }

        return result;
    }

    private void SetupTestPatterns()
    {
        Predicate street_no_1 = new Predicate("street_no", (s) -> s.matches("\\d+"));
        Predicate street_1 = new Predicate("street", (s) -> s.matches("\\w+"));
        Predicate street_suffix_1 = new Predicate("street_suffix", (s) -> s.matches("\\w+"));
        Predicate city_1 = new Predicate("city", (s) -> s.matches("\\w+"));
        Predicate city_2 = new Predicate("city", (s, t) -> s.matches("\\w+") && t.matches("\\w+"));
        Predicate state_1 = new Predicate("state", (s) -> s.matches("\\w+"));
        Predicate zip_1 = new Predicate("zip", s -> s.matches("\\d{5,5}"));

        _patterns = new ArrayList<MatchPattern>();

        _patterns.add(new MatchPattern(new Predicate[]{street_no_1, street_1, street_suffix_1, city_1, state_1, zip_1}));
        _patterns.add(new MatchPattern(new Predicate[]{street_no_1, street_1, street_suffix_1, city_2, state_1, zip_1}));
        _patterns.add(new MatchPattern(new Predicate[]{city_1, zip_1}));
    }
}
