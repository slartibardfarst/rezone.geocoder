package rezone.geocoder.parser;

import java.util.*;

/**
 * Holds the result of testing a list of input tokens against a pattern
 */
public class PatternMatch {
    public boolean Success;
    public boolean TokenCountMatch;
    public rezone.geocoder.parser.Pattern Pattern;
    private String _patternId;
    private List<PredicateMatch> _predicateMatches;
    private Predicate _firstFailedPredicate;


    public PatternMatch(rezone.geocoder.parser.Pattern pattern) {
        Success = false;
        Pattern = pattern;
        _patternId = pattern.getPatternId();
    }

    public PatternMatch(rezone.geocoder.parser.Pattern pattern, boolean tokenCountMatch) {
        Success = false;
        TokenCountMatch = tokenCountMatch;
        Pattern = pattern;
        _patternId = pattern.getPatternId();
        _predicateMatches = null;
    }

    public void setFirstFailedPredicate(Predicate firstFailedPredicate)
    {
        Success = false;
        _firstFailedPredicate = firstFailedPredicate;
    }

    public void addPredicateMatch(PredicateMatch pm) {
        if (null == _predicateMatches)
            _predicateMatches = new ArrayList<>();

        _predicateMatches.add(pm);

    }

    public List<PredicateMatch> getMatches() {
        if (null == _predicateMatches)
            _predicateMatches = new ArrayList<>();

        return _predicateMatches;
    }


}