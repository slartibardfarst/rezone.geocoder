package rezone.geocoder.parser;

import rezone.geocoder.parser.patterns.PatternMatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Trace why an input string did or did not match various patterns
 */
public class ParseDebug {
    private String _inputQuery;
    private InputTokens _tokenizedQuery;
    private List<PatternMatch> _results;
    private String _matchedIndices;
    private long _start;
    private long _duration;

    public void setInputQuery(String query) {
        _inputQuery = query;
    }

    public void setInputTokens(InputTokens tokenizedQuery) {
        _tokenizedQuery = tokenizedQuery;
    }

    public void addPatternMatchResult(PatternMatch result) {
        if (null == _results)
            _results = new ArrayList<>();

        _results.add(result);
    }

    public void startStopwatch()
    {
        _start = System.currentTimeMillis();
    }

    public long stopStopwatch()
    {
        long now = System.currentTimeMillis();
        _duration = now-_start;
        return _duration;
    }

    public void buildListOfMatchedPatterns()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < _results.size(); i++)
            if(_results.get(i).Success) {
                sb.append(i);
                sb.append(" ");
            }
        _matchedIndices = sb.toString();
    }
}
