package rezone.geocoder.parser;
import java.util.*;


public class QueryParser {
    private List<MatchPattern> _patterns;


    public Geo parse(String query) {

        InputTokens tokenizedQuery = InputTokens.tokenize(query);
        List<MatchResult> matchResults = testPatterns(tokenizedQuery);
        return null;
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
}
