package rezone.geocoder.parser.patterns;

import rezone.geocoder.parser.InputTokens;
import rezone.geocoder.parser.predicate.Predicate;
import rezone.geocoder.parser.predicate.PredicateMatch;

import java.util.List;

/**
 * A Pattern is an ordered list of predicates that captures one possible address pattern.
 * Input tokens are matched to pattern predicates 1:1 and so an list of n input tokens can
 * only ever match against a Pattern with the same number of tokens
 */
public class Pattern {

    private String _name;
    private int _numTokens;
    private Predicate[] _predicates;
    private String _patternId;


    public Pattern(String name, List<Predicate> predicates) {
        this((Predicate[]) predicates.toArray());
        _name = name;
    }

    public Pattern(Predicate[] predicates) {
        _predicates = predicates;

        //compute the number of input tokens this Pattern will process
        for (Predicate currPredicate : _predicates)
            _numTokens += currPredicate.getNumTokens();

        //compute a unique, human readable name for this pattern based on predicate names
        _patternId = buildPatternId();
    }


    public int getNumTokens() {
        return _numTokens;
    }

    public String getPatternId() {
        return _patternId;
    }

    /**
     * check the input tokens against the predicates of this pattern
     */
    public PatternMatch test(InputTokens input, boolean debug) {
        //a pattern cannot match unless it has the same number of tokens as the input
        if (input.getNumTokens() != _numTokens) {
            return (debug) ? new PatternMatch(this, false) : null;
        }

        PatternMatch result = new PatternMatch(this, true);
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

    private String buildPatternId() {
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
        return sb.toString();
    }
}