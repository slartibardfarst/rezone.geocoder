package rezone.geocoder.parser.patterns;

import rezone.geocoder.parser.predicate.Predicate;

import java.util.*;

/**
 * Created by awatkins on 15-10-22.
 */
public class PatternManager {

    private static String getRulesFromUser() {
        String s = "";
        s += "address :- city, zip;";

        //terminal symbols
        s += "city :- city/1;";
        s += "city :- city/2;";
        s += "zip  :- zip/1;";

        return s;
    }

    private static Map<String, Predicate> definePredicates() {
        Map<String, Predicate> result = new HashMap<String, Predicate>();

        result.put("street_no_1", new Predicate("street_no", (s) -> TokenParserHelpers.streetNumber1(s)));
        result.put("street_direction_1", new Predicate("street_direction", (s) -> TokenParserHelpers.directional1(s)));
        result.put("street_1", new Predicate("street", (s) -> TokenParserHelpers.streetName1(s)));
        result.put("street_2", new Predicate("street", (s, t) -> TokenParserHelpers.streetName2(s, t)));
        result.put("street_suffix_1", new Predicate("street_suffix", (s) -> TokenParserHelpers.streetSuffix1(s)));
        result.put("city_1", new Predicate("city", (s) -> TokenParserHelpers.city1(s)));
        result.put("city_2", new Predicate("city", (s, t) -> TokenParserHelpers.city2(s, t)));
        result.put("state_1", new Predicate("state", (s) -> TokenParserHelpers.state1(s)));
        result.put("state_2", new Predicate("state", (s, t) -> TokenParserHelpers.state2(s, t)));
        result.put("zip_1", new Predicate("zip", s -> TokenParserHelpers.zip1(s)));
        result.put("unit_2", new Predicate("unit", (s, t) -> TokenParserHelpers.unit2(s, t)));

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

    private static void markTopLevelRules(List<ProductionRule> rules) {
        for (ProductionRule currRule : rules) {
            boolean isTopLevelRule = true; //default
            for (ProductionRule currOtherRule : rules) {
                if (currRule == currOtherRule)
                    continue;

                for (String currOtherSymbol : currOtherRule.getSymbols()) {
                    if (currOtherSymbol.equals(currRule.getName())) {
                        isTopLevelRule = false;
                        break;
                    }
                }
            }
            currRule.setIsTopLevel(isTopLevelRule);
        }
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

    private static ProductionRule expandSymbolInRule(ProductionRule source, int iSymbol, ProductionRule symbolDefinition)
    {
        List<String> expandedSymbols = new ArrayList<>();
        for(int i = 0; i < iSymbol; i++)
            expandedSymbols.add(source.getSymbols().get(i));

        for(int i = 0; i < symbolDefinition.getSymbols().size(); i++)
            expandedSymbols.add(symbolDefinition.getSymbols().get(i));

        for(int i = iSymbol+1; i < source.getSymbols().size(); i++)
            expandedSymbols.add(source.getSymbols().get(i));

        return new ProductionRule(source.getName(), expandedSymbols);
    }

    private static List<ProductionRule> deduplicateRules(List<ProductionRule> rulesWithDuplicates)
    {
        //TODO
        return rulesWithDuplicates;
    }

    private static boolean isTerminalSymbol(String s) {
        return s.contains("/");
    }

    private static List<ProductionRule> getSymbolDefinitions(String currSymbol, List<ProductionRule>definitions)
    {
        List<ProductionRule> result = new ArrayList<>() ;
        for(ProductionRule currRule: definitions)
        if(currRule.getName().equalsIgnoreCase(currSymbol))
            result.add(currRule);

        return result;
    }

    private static List<Pattern> convertRulesToPatterns(List<ProductionRule> terminalRules, Map<String, Predicate> predicates) {
        List<Pattern> result = new ArrayList<>();

        for(ProductionRule currTerminalRule: terminalRules) {
            if(!currTerminalRule.getIsTerminal())
                return result;

            if(!currTerminalRule.getIsTopLevel())
                continue;

            List<Predicate> currRulePredicates = new ArrayList<>();
            for (String currSymbol : currTerminalRule.getSymbols())
                currRulePredicates.add(predicates.get(currSymbol));

            result.add( new Pattern(currTerminalRule.getName(), currRulePredicates));
        }

        return result;
    }



    public static List<Pattern> listPatterns2() {

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


    public static List<Pattern> setupPatterns() {
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

        patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, unit_2, city_2, state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_1, street_suffix_1, unit_2, city_2, state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_1, street_suffix_1, unit_2, city_1, state_1, zip_1}));

        patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_1, state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_2, state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_1, state_2, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_direction_1, street_1, street_suffix_1, city_2, state_2, zip_1}));

        patterns.add(new Pattern(new Predicate[]{street_no_1, street_2, street_suffix_1, city_2, state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_1, street_suffix_1, city_1, state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{street_no_1, street_1, street_suffix_1, city_2, state_1, zip_1}));

        patterns.add(new Pattern(new Predicate[]{state_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{state_2, zip_1}));

        patterns.add(new Pattern(new Predicate[]{city_1, zip_1}));
        patterns.add(new Pattern(new Predicate[]{city_2, zip_1}));

        patterns.add(new Pattern(new Predicate[]{state_1}));
        patterns.add(new Pattern(new Predicate[]{state_2}));

        patterns.add(new Pattern(new Predicate[]{city_1}));
        patterns.add(new Pattern(new Predicate[]{city_2}));

        return patterns;
    }

    public static class PatternManagerTestClass
    {
        public static List<ProductionRule> testListRules(String rulesString)
        {
            List<ProductionRule> result = defineRules(rulesString);
            return result;
        }


        public static List<Pattern> testListPatterns()
        {
            return listPatterns2();
        }
    }
}
