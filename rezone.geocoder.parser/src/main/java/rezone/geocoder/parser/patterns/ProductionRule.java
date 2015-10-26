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

    public String getName() {
        return _name;
    }
    public List<String> getSymbols() {
        return _symbols;
    }
    public boolean getIsTerminal() {
        return _isTerminal;
    }
    public String getId() { return _id; }

    public ProductionRule(String name, String[] symbols) {
        this(name, Arrays.asList(symbols));
    }

    public ProductionRule(String name, List<String> symbols) {
        _name = name.trim();

        _symbols = new ArrayList<>();
        for(String currSymbol: symbols)
            _symbols.add(currSymbol.trim());

        _isTerminal = true; //default
        for(String currSymbol: _symbols)
            if(!isSymbolTerminal(currSymbol))
            {
                _isTerminal = false;
                break;
            }

        _id = generateRuleId();
    }



    public static boolean isSymbolTerminal(String symbol)
    { return symbol.contains("/");}

    private String generateRuleId()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(_name.toLowerCase().trim());
        sb.append(":");

        boolean first = true;
        for(String currSymbol: _symbols)
        {
            if(true == first)
                first = false;
            else
                sb.append(",");

            sb.append(currSymbol.toLowerCase().trim());
        }

        return sb.toString();
    }

}
