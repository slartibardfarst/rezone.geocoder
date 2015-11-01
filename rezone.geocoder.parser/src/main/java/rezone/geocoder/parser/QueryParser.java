package rezone.geocoder.parser;

import rezone.geocoder.parser.patterns.Pattern;
import rezone.geocoder.parser.patterns.PatternManager;
import rezone.geocoder.parser.patterns.PatternMatch;

import java.util.*;


public class QueryParser {
    private List<Pattern> _patterns;

    public QueryParser() {
        _patterns = PatternManager.setupPatterns();
    }

    public QueryParser(String patternsFilename) {
        _patterns = PatternManager.setupPatterns(patternsFilename);
    }

    public QueryParser(List<Pattern> patterns)
    {
        _patterns = patterns;
    }

    public Geo[] parse(String query) {
        return parse(query, null);
    }

    public Geo[] parse(String query, ParseDebug dbg) {
        if(null!= dbg) {dbg.startStopwatch();}
        InputTokens tokenizedQuery = InputTokens.tokenize(query);
        List<PatternMatch> allMatches = testPatterns(tokenizedQuery, dbg);
        List<PatternMatch> filteredMatches = discardNonsenseMatches(allMatches);

        if(null != dbg) {
            dbg.stopStopwatch();
            dbg.setInputQuery(query);
            dbg.setInputTokens(tokenizedQuery);
            dbg.buildListOfMatchedPatterns();
        }

        List<Geo> result = new ArrayList<>();
        if (null != filteredMatches)
            for (PatternMatch currMatch : filteredMatches)
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


    private List<PatternMatch> discardNonsenseMatches(List<PatternMatch> allMatches)
    {
        List<PatternMatch> result = new ArrayList<>();
        for(PatternMatch currMatch: allMatches)
            if(currMatch.isSensibleMatch())
                result.add(currMatch);

        return result;
    }
}
