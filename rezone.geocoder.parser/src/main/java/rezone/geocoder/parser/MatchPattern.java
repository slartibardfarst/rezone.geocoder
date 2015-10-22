package rezone.geocoder.parser;

import java.util.*;


public class MatchPattern {

    private int _numTokens;
    private List<Predicate> _predicates;

    public MatchPattern() {
        _predicates = new ArrayList<>();
        _numTokens = 0;
    }

    public MatchPattern(List<Predicate> predicates) {
        _predicates = new ArrayList<>(predicates);
        _predicates.stream().forEach((p) -> {
            _numTokens += p.getNumTokens();
        });
    }

    public int getNumTokens()
    {
        return _numTokens;
    }

    public void addPredicate(Predicate p)
    {
        _predicates.add(p);
        _numTokens += p.getNumTokens();
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