package rezone.geocoder.parser;

/**
 * A MatchPattern is an ordered list of predicates that captures one possible address pattern.
 * Input tokens are matched to match pattern predicates 1:1 and so an list of n input tokens can
 * only ever match against a MatchPattern with the same number of tokens
 */
public class MatchPattern {

    private int _numTokens;
    private Predicate[] _predicates;
    private String _patternId;


    public MatchPattern(Predicate[] predicates) {
        _predicates = predicates;

        //compute the number of input tokens this MatchPattern will process
        for (Predicate currPredicate : _predicates)
            _numTokens += currPredicate.getNumTokens();

        //compute a unique, human readable name for this pattern based on predicate names
        boolean firstItem = true;
        StringBuilder sb = new StringBuilder();
        for (Predicate currPredicate : _predicates) {
            if (firstItem)
                firstItem = false;
            else
                sb.append(", ");

            sb.append(currPredicate.Name);
            sb.append("/");
            sb.append(currPredicate.getNumTokens());
        }
        _patternId = sb.toString();
    }

    public int getNumTokens() {
        return _numTokens;
    }

    public String getPatternId() {
        return _patternId;
    }


    public MatchPatternResult test(InputTokens input, boolean debug) {
        if (input.getNumTokens() != _numTokens) {
            return (debug) ? new MatchPatternResult(this, false) : null;
        }

        MatchPatternResult result = new MatchPatternResult(this, true);
        for (Predicate p : _predicates) {
            PredicateMatch predicateMatch = p.test(input);
            if (null != predicateMatch) {
                result.addPredicateMatch(predicateMatch);
            } else {
                result.setFirstFailedPredicate(p);
                return (debug) ? result : null;
            }
        }

        result.Success = true;
        return result;
    }
}