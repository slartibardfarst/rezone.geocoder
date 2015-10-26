package rezone.geocoder.parser.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by awatkins on 15-10-24.
 */
public class ProductionRule {
    private String _name;
    private List<String> _symbols;
    private boolean _isTopLevel;
    private boolean _isTerminal;

    public ProductionRule(String name, String[] symbols) {
        this(name, Arrays.asList(symbols));
    }

    public ProductionRule(String name, List<String> symbols) {
        _name = name;

        _symbols = new ArrayList<>(symbols);
        _isTerminal = true; //default
        for(String currSymbol: _symbols)
            if(!isSymbolTerminal(currSymbol))
            {
                _isTerminal = false;
                break;
            }
    }

    public String getName() {
        return _name;
    }

    public List<String> getSymbols() {
        return _symbols;
    }

    public boolean getIsTerminal() {
        return _isTerminal;
    }

    public void setIsTopLevel(boolean isTopLevel) {
        _isTopLevel = isTopLevel;
    }

    public boolean getIsTopLevel() {
        return _isTopLevel;
    }

    public static boolean isSymbolTerminal(String symbol)
    { return symbol.contains("/");}

}
