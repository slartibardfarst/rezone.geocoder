package rezone.geocoder.parser.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by awatkins on 15-10-24.
 */
public class ProductionRule {
    private String _id;
    private String _name;
    private List<String> _symbols;
    private boolean _isTerminal;
    private boolean _isTopLevelRule;

    public String getName() {
        return _name;
    }

    public boolean getIsTerminal() {
        return _isTerminal;
    }

    public String getId() {
        return _id;
    }

    public void setIsTopLevel(boolean isTopLevel) {
        _isTopLevelRule = isTopLevel;
    }

    public boolean getIsTopLevel() {
        return _isTopLevelRule;
    }

    public List<String> getSymbols() {
        return _symbols;
    }

    public void setSymbols(List<String> symbols) {
        _symbols = new ArrayList<>();
        for (String currSymbol : symbols)
            _symbols.add(currSymbol.trim());

        _isTerminal = true; //default
        for (String currSymbol : _symbols)
            if (!isSymbolTerminal(currSymbol)) {
                _isTerminal = false;
                break;
            }

        _id = generateRuleId();
    }

    public ProductionRule(ProductionRule src) {
        _name = src.getName().trim();
        _isTerminal = src._isTerminal;
        _isTopLevelRule = src._isTopLevelRule;
        setSymbols(src.getSymbols());
    }

    public ProductionRule(String name, String[] symbols) {
        this(name, Arrays.asList(symbols));
    }

    public ProductionRule(String name, List<String> symbols) {
        _name = name.trim();
        _isTopLevelRule = false;
        setSymbols(symbols);
    }


    public static boolean isSymbolTerminal(String symbol) {
        return symbol.contains("/");
    }

    private String generateRuleId() {
        StringBuilder sb = new StringBuilder();
        sb.append(_name.toLowerCase().trim());
        sb.append(":");

        boolean first = true;
        for (String currSymbol : _symbols) {
            if (true == first)
                first = false;
            else
                sb.append(",");

            sb.append(currSymbol.toLowerCase().trim());
        }

        return sb.toString();
    }

}
