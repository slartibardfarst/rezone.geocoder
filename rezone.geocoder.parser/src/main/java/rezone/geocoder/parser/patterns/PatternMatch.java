package rezone.geocoder.parser.patterns;

import rezone.geocoder.parser.predicate.Predicate;
import rezone.geocoder.parser.predicate.PredicateMatch;

import java.util.*;

/**
 * Holds the result of testing a list of input tokens against a pattern
 */
public class PatternMatch {
    public boolean Success;
    public boolean TokenCountMatch;
    public rezone.geocoder.parser.patterns.Pattern Pattern;
    private String _patternId;
    private List<PredicateMatch> _predicateMatches;
    private Predicate _firstFailedPredicate;


    public PatternMatch(rezone.geocoder.parser.patterns.Pattern pattern) {
        Success = false;
        Pattern = pattern;
        _patternId = pattern.getPatternId();
    }

    public PatternMatch(rezone.geocoder.parser.patterns.Pattern pattern, boolean tokenCountMatch) {
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