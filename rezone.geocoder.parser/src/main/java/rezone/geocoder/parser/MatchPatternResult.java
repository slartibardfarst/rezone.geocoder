package rezone.geocoder.parser;

import java.util.*;

/**
 * Holds the result of applying a list of input tokens to a match pattern
 */
public class MatchPatternResult {
    public boolean Success;
    public boolean TokenCountMatch;
    public MatchPattern Pattern;
    private String _patternId;
    private List<PredicateMatch> _predicateMatches;
    private Predicate _firstFailedPredicate;


    public MatchPatternResult(MatchPattern pattern) {
        Success = false;
        Pattern = pattern;
        _patternId = pattern.getPatternId();
    }

    public MatchPatternResult(MatchPattern pattern, boolean tokenCountMatch) {
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