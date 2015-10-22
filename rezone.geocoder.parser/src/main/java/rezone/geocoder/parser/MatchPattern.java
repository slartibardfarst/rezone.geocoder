package rezone.geocoder.parser;

import java.util.*;


public class MatchPattern {

    private int _numTokens;
    private Predicate[] _predicates;


    public MatchPattern(Predicate[] predicates) {
        _predicates = predicates;

        for (Predicate currPredicate : _predicates)
            _numTokens += currPredicate.getNumTokens();
    }

    public int getNumTokens() {
        return _numTokens;
    }


    public MatchResult test(InputTokens input) {
        if (input.getNumTokens() != _numTokens) {
            return null;
        }

        MatchResult result = new MatchResult();
        for (Predicate p : _predicates) {
            PredicateMatch predicateMatch = p.test(input);
            if (null != predicateMatch) {
                result.PatternMatches.add(predicateMatch);
            } else {
                return null;
            }
        }

        return result;
    }
}