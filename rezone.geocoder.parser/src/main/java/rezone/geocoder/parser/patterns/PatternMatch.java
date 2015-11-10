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
    public Pattern Pattern;
    private String _patternId;
    private Map<String, PredicateMatch> _predicateMatches;
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

    public void setFirstFailedPredicate(Predicate firstFailedPredicate) {
        Success = false;
        _firstFailedPredicate = firstFailedPredicate;
    }

    public void addPredicateMatch(PredicateMatch pm) {
        if (null == _predicateMatches)
            _predicateMatches = new HashMap<>();

        _predicateMatches.put(pm.Name, pm);
    }

    public Collection<PredicateMatch> getMatches() {
        if (null == _predicateMatches)
            _predicateMatches = new HashMap<>();

        return _predicateMatches.values();
    }

    public boolean isSensibleMatch() {
        if (Pattern.getName().equalsIgnoreCase("address")) {
            if (_predicateMatches.containsKey("unit") &&
                    !_predicateMatches.containsKey("zip") &&
                    TokenParserHelpers.zip1(_predicateMatches.get("unit").Tokens[0]))
                return false;

            if (_predicateMatches.containsKey("street") && _predicateMatches.get("street").Tokens[0].toLowerCase().equalsIgnoreCase("state")) {
                if (_predicateMatches.containsKey("street_suffix") && _predicateMatches.get("street_suffix").Tokens[0].toLowerCase().equalsIgnoreCase("route"))
                    return false;

                if (_predicateMatches.get("street").Tokens.length == 2 && _predicateMatches.containsKey("unit"))
                    return false;

                if (_predicateMatches.get("street").Tokens.length == 3 && _predicateMatches.get("street").Tokens[1].toLowerCase().equalsIgnoreCase("route"))
                    return true;
            }


            if (_predicateMatches.containsKey("street") &&
                    _predicateMatches.get("street").Tokens.length == 3 &&
                    !_predicateMatches.containsKey("street_suffix") &&
                    TokenParserHelpers.streetSuffix1(_predicateMatches.get("street").Tokens[1]))
                return false;

            if (!_predicateMatches.containsKey("street_direction") &&
                    _predicateMatches.containsKey("street") &&
                    TokenParserHelpers.directional1(_predicateMatches.get("street").Tokens[0]))
                return false;

            if (_predicateMatches.containsKey("street") && !_predicateMatches.containsKey("street_suffix")) {
                int numTokens = _predicateMatches.get("street").Tokens.length;
                if (numTokens > 1 &&
                        TokenParserHelpers.streetSuffix1(_predicateMatches.get("street").Tokens[numTokens - 1]))
                    return false;
            }
        }

        if (Pattern.getName().equalsIgnoreCase("street_geo")) {
            if (_predicateMatches.containsKey("street") && _predicateMatches.get("street").Tokens.length == 3) {
                if (TokenParserHelpers.directional1(_predicateMatches.get("street").Tokens[0]) && !_predicateMatches.containsKey("street_direction"))
                    return false;

                if (TokenParserHelpers.streetSuffix1(_predicateMatches.get("street").Tokens[1]))
                    return false;
            }

            if (_predicateMatches.containsKey("street") && !_predicateMatches.containsKey("street_suffix")) {
                int numTokens = _predicateMatches.get("street").Tokens.length;
                if (numTokens > 1 &&
                        TokenParserHelpers.streetSuffix1(_predicateMatches.get("street").Tokens[numTokens - 1]))
                    return false;
            }
        }

        if (Pattern.getName().equalsIgnoreCase("nbhd_geo")) {
            if (_predicateMatches.containsKey("neighborhood")) {
                int numTokens = _predicateMatches.get("neighborhood").Tokens.length;
                if (numTokens == 1 &&
                        TokenParserHelpers.getStandardPrefixes().contains(_predicateMatches.get("neighborhood").Tokens[0].toLowerCase()))
                    return false;

                if (numTokens > 1 &&
                        TokenParserHelpers.streetSuffix1(_predicateMatches.get("neighborhood").Tokens[numTokens - 1]))
                    return false;
            }
        }

        return true;
    }
}