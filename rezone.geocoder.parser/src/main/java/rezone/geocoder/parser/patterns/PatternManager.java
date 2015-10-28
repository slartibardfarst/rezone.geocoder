package rezone.geocoder.parser.patterns;

import rezone.geocoder.parser.predicate.Predicate;

import java.util.*;

/**
 * Created by awatkins on 15-10-22.
 */
public class PatternManager {

    private static String getRulesFromUser() {
        String s = "";

        //top-level rules
        s += "address :- address_line, city, state, zip;";

        s += "street_geo :- street, city, state, zip;";
        s += "street_geo :- street, city, state;";

        s += "city_geo :- city, state;";

        s += "state_geo :- state;";

        s += "county_geo :- county_name;";
        s += "county_geo :- county_name, state;";

        s += "zip_geo :- zip;";

        //non-terminals
        s += "address_line :- street_number, street;";
        s += "address_line :- street_number, street, unit;";

        s += "street :- street_name, street_suffix;";
        s += "street :- predirectional, street_name, street_suffix;";

        //terminal symbols
        s += "street_number :- street_no/1;";
        s += "street_name :- street/1;";
        s += "street_name :- street/2;";
        s += "street_suffix :- street_suffix/1;";
        s += "predirectional :- directional/1;";
        s += "city :- city/1;";
        s += "city :- city/2;";
        s += "state :- state/1;";
        s += "state :- state/2;";
        s += "zip  :- zip/1;";
        s += "unit :- unit/2;";
        s += "county_name :- county/2;";
        s += "county_name :- county/3;";

        return s;
    }

    private static Map<String, Predicate> definePredicates() {
        Map<String, Predicate> result = new HashMap<String, Predicate>();

        result.put("street_no/1", new Predicate("street_no", (s) -> TokenParserHelpers.streetNumber1(s)));
        result.put("directional/1", new Predicate("directional", (s) -> TokenParserHelpers.directional1(s)));
        result.put("street/1", new Predicate("street", (s) -> TokenParserHelpers.streetName1(s)));
        result.put("street/2", new Predicate("street", (s, t) -> TokenParserHelpers.streetName2(s, t)));
        result.put("street_suffix/1", new Predicate("street_suffix", (s) -> TokenParserHelpers.streetSuffix1(s)));
        result.put("city/1", new Predicate("city", (s) -> TokenParserHelpers.city1(s)));
        result.put("city/2", new Predicate("city", (s, t) -> TokenParserHelpers.city2(s, t)));
        result.put("state/1", new Predicate("state", (s) -> TokenParserHelpers.state1(s)));
        result.put("state/2", new Predicate("state", (s, t) -> TokenParserHelpers.state2(s, t)));
        result.put("zip/1", new Predicate("zip", s -> TokenParserHelpers.zip1(s)));
        result.put("unit/2", new Predicate("unit", (s, t) -> TokenParserHelpers.unit2(s, t)));
        result.put("county/2", new Predicate("county", (s, t) -> TokenParserHelpers.county2(s, t)));
        result.put("county/3", new Predicate("county", (s, t, u) -> TokenParserHelpers.county3(s, t, u)));

        return result;
    }

    private static List<ProductionRule> defineRules() {
        String rulesDefinitionString = getRulesFromUser();
        return defineRules(rulesDefinitionString);
    }

    private static List<ProductionRule> defineRules(String rulesDefinitionsString) {
        List<ProductionRule> result = new ArrayList<>();

        String[] ruleStrings = rulesDefinitionsString.split(";");
        for (String ruleString : ruleStrings) {
            String[] ruleParts = ruleString.split(":-");
            result.add(new ProductionRule(ruleParts[0], ruleParts[1].split(",")));
        }

        return result;
    }

    private static void expandOptionalSymbolsInRule(ProductionRule rule, List<ProductionRule> expandedAcc) {
        if (!rule.containsOptionSymbols())
            expandedAcc.add(rule);
        else
            for (int i = 0; i < rule.getSymbols().size(); i++) {
                if (rule.nthSymbolIsOptional(i)) {
                    ProductionRule copy1 = new ProductionRule(rule);
                    ProductionRule copy2 = new ProductionRule(rule);
                    copy1.removeNthSymbol(i);
                    copy2.makeNthSymbolNotOptional(i);
                    expandOptionalSymbolsInRule(copy1, expandedAcc);
                    expandOptionalSymbolsInRule(copy2, expandedAcc);
                }
            }
    }

    private static List<ProductionRule> expandOptionalSymbolsInRules(List<ProductionRule> unexpandedRules) {
        List<ProductionRule> result = new ArrayList<>();
        for (ProductionRule currUnexpanded : unexpandedRules) {
            List<ProductionRule> expanded = new ArrayList<>();
            expandOptionalSymbolsInRule(currUnexpanded, expanded);
            result.addAll(expanded);
        }

        return result;
    }

    private static List<ProductionRule> expandRules(List<ProductionRule> rules) {
        List<ProductionRule> allTerminalSymbolsRules = new ArrayList<>();
        for (ProductionRule currRule : rules)
            recursivelyExpandRule(currRule, rules, allTerminalSymbolsRules);

        List<ProductionRule> deduplicatedExpandedRules = deduplicateRules(allTerminalSymbolsRules);
        return deduplicatedExpandedRules;
    }

    public static void recursivelyExpandRule(ProductionRule currRule,
                                             List<ProductionRule> definitions,
                                             List<ProductionRule> terminalRulesAcc) {
        boolean allSymbolsTerminal = true; //default
        for (int i = 0; i < currRule.getSymbols().size(); i++) {
            String currSymbol = currRule.getSymbols().get(i);

            if (!isTerminalSymbol(currSymbol)) {
                allSymbolsTerminal = false;
                List<ProductionRule> symbolDefinitions = getSymbolDefinitions(currSymbol, definitions);
                for (ProductionRule currDefinition : symbolDefinitions) {
                    ProductionRule expanded = expandSymbolInRule(currRule, i, currDefinition);
                    recursivelyExpandRule(expanded, definitions, terminalRulesAcc);
                }
            }
        }

        if (allSymbolsTerminal)
            terminalRulesAcc.add(currRule);
    }

    private static ProductionRule expandSymbolInRule(ProductionRule source, int iSymbol, ProductionRule symbolDefinition) {
        List<String> expandedSymbols = new ArrayList<>();
        for (int i = 0; i < iSymbol; i++)
            expandedSymbols.add(source.getSymbols().get(i));

        for (int i = 0; i < symbolDefinition.getSymbols().size(); i++)
            expandedSymbols.add(symbolDefinition.getSymbols().get(i));

        for (int i = iSymbol + 1; i < source.getSymbols().size(); i++)
            expandedSymbols.add(source.getSymbols().get(i));

        ProductionRule expandedRule = new ProductionRule(source);
        expandedRule.setSymbols(expandedSymbols);
        return expandedRule;
    }

    private static List<ProductionRule> deduplicateRules(List<ProductionRule> rulesWithDuplicates) {
        Set<String> existingPatterns = new HashSet<>();
        List<ProductionRule> deduplicatedRules = new ArrayList<>();

        for (ProductionRule currRule : rulesWithDuplicates) {
            String id = currRule.getId();
            if (!existingPatterns.contains(id)) {
                existingPatterns.add(id);
                deduplicatedRules.add(currRule);
            }
        }

        return deduplicatedRules;
    }

    private static boolean isTerminalSymbol(String s) {
        return s.contains("/");
    }

    private static List<ProductionRule> getSymbolDefinitions(String currSymbol, List<ProductionRule> definitions) {
        List<ProductionRule> result = new ArrayList<>();
        for (ProductionRule currRule : definitions)
            if (currRule.getName().equalsIgnoreCase(currSymbol))
                result.add(currRule);

        return result;
    }

    private static List<Pattern> convertRulesToPatterns(List<ProductionRule> terminalRules, Map<String, Predicate> predicates) {
        List<Pattern> result = new ArrayList<>();

        for (ProductionRule currTerminalRule : terminalRules) {
            if (!currTerminalRule.getIsTerminal())
                continue;

            if (!currTerminalRule.getIsTopLevel())
                continue;

            List<Predicate> currRulePredicates = new ArrayList<>();
            for (String currSymbol : currTerminalRule.getSymbols()) {
                Predicate p = predicates.getOrDefault(currSymbol, null);
                if (null == p)
                    throw new RuntimeException("cannot find predicate for name:" + currSymbol);

                currRulePredicates.add(p);
            }

            Predicate[] pa = currRulePredicates.toArray(new Predicate[currRulePredicates.size()]);
            Pattern p = new Pattern(currTerminalRule.getName(), pa);
            result.add(p);
        }

        return result;
    }

    private static void markTopLevelRules(List<ProductionRule> rules) {
        for (ProductionRule currRule : rules) {
            String currRuleName = currRule.getName();
            boolean foundInAnotherRule = false; //default
            for (ProductionRule otherRule : rules) {
                for (String currOtherRuleSymbol : otherRule.getSymbols()) {
                    if (currRuleName.equalsIgnoreCase(currOtherRuleSymbol)) {
                        foundInAnotherRule = true;
                        break;
                    }
                }

                if (foundInAnotherRule)
                    break;
            }

            currRule.setIsTopLevel(!foundInAnotherRule);
        }
    }


    public static List<Pattern> setupPatterns() {

        //a rule has a name and a list of symbols
        //a symbol is a name of a rule or the name of a predicate
        //if a rule name is not mentioned in the list of symbols of any other rule, then it's a top-level rule
        //if the list of symbols associated with a rule contains only predicate names, then that is a terminal rule
        //a Pattern is a terminal rule where predicate names have been converted to the corresponding predicate functions

        Map<String, Predicate> predicates = definePredicates();
        List<ProductionRule> rules = defineRules();
        markTopLevelRules(rules);
        List<ProductionRule> expandedRules = expandRules(rules);
        List<Pattern> patterns = convertRulesToPatterns(expandedRules, predicates);

        return patterns;
    }


/*    public static List<Pattern> setupPatterns() {
        List<Pattern> patterns = new ArrayList<>();

        Predicate street_no_1 = new Predicate("street_no", (s) -> TokenParserHelpers.streetNumber1(s));

        Predicate street_direction_1 = new Predicate("street_direction", (s) -> TokenParserHelpers.directional1(s));

        Predicate street_1 = new Predicate("street", (s) -> TokenParserHelpers.streetName1(s));
        Predicate street_2 = new Predicate("street", (s, t) -> TokenParserHelpers.streetName2(s, t));

        Predicate street_suffix_1 = new Predicate("street_suffix", (s) -> TokenParserHelpers.streetSuffix1(s));

        Predicate city_1 = new Predicate("city", (s) -> TokenParserHelpers.city1(s));
        Predicate city_2 = new Predicate("city", (s, t) -> TokenParserHelpers.city2(s, t));

        Predicate state_1 = new Predicate("state", (s) -> TokenParserHelpers.state1(s));
        Predicate state_2 = new Predicate("state", (s, t) -> TokenParserHelpers.state2(s, t));
        Predicate zip_1 = new Predicate("zip", s -> TokenParserHelpers.zip1(s));

        Predicate unit_2 = new Predicate("unit", (s, t) -> TokenParserHelpers.unit2(s, t));

        patterns = new ArrayList<Pattern>();

        patterns.add(new Pattern("address", new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, unit_2, city_2, state_1, zip_1}));
        patterns.add(new Pattern("address", new Predicate[]{street_no_1, street_1, street_suffix_1, unit_2, city_2, state_1, zip_1}));
        patterns.add(new Pattern("address", new Predicate[]{street_no_1, street_1, street_suffix_1, unit_2, city_1, state_1, zip_1}));

        patterns.add(new Pattern("address", new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_1, state_1, zip_1}));
        patterns.add(new Pattern("address", new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_2, state_1, zip_1}));
        patterns.add(new Pattern("address", new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_1, state_2, zip_1}));
        patterns.add(new Pattern("address", new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_2, state_2, zip_1}));

        patterns.add(new Pattern("address", new Predicate[]{street_no_1, street_2, street_suffix_1, city_2, state_1, zip_1}));
        patterns.add(new Pattern("address", new Predicate[]{street_no_1, street_1, street_suffix_1, city_1, state_1, zip_1}));
        patterns.add(new Pattern("address", new Predicate[]{street_no_1, street_1, street_suffix_1, city_2, state_1, zip_1}));

        patterns.add(new Pattern("city", new Predicate[]{city_1, zip_1}));
        patterns.add(new Pattern("city", new Predicate[]{city_2, zip_1}));
        patterns.add(new Pattern("city", new Predicate[]{city_1}));
        patterns.add(new Pattern("city", new Predicate[]{city_2}));

        patterns.add(new Pattern("state", new Predicate[]{state_1}));
        patterns.add(new Pattern("state", new Predicate[]{state_2}));
        patterns.add(new Pattern("state", new Predicate[]{state_1, zip_1}));
        patterns.add(new Pattern("state", new Predicate[]{state_2, zip_1}));

        return patterns;
    }*/

    public static class PatternManagerTestClass {
        public static List<ProductionRule> testListRules(String rulesString) {
            List<ProductionRule> result = defineRules(rulesString);
            return result;
        }


        public static List<Pattern> testListPatterns(String rulesString) {
            Map<String, Predicate> predicates = definePredicates();
            List<ProductionRule> rules = defineRules(rulesString);
            List<ProductionRule> expandedOptionals = expandOptionalSymbolsInRules(rules);
            markTopLevelRules(expandedOptionals);
            List<ProductionRule> expandedRules = expandRules(expandedOptionals);
            List<Pattern> patterns = convertRulesToPatterns(expandedRules, predicates);
            return patterns;
        }
    }
}
