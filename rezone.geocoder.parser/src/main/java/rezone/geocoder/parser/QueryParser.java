package rezone.geocoder.parser;

import java.util.*;


public class QueryParser {
    private List<Pattern> _patterns;

    public QueryParser() {
        _patterns = PatternManager.setupPatterns();
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

}
