package rezone.geocoder.parser.patterns;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

/**
 * Created by awatkins on 15-10-25.
 */
public class PatternManagerTest extends TestCase {

    @Test
    public void testListRules() throws Exception {

        String s = "";
        s += "address :- city, zip;";

        //terminal symbols
        s += "city :- city/1;";
        s += "city :- city/2;";
        s += "zip  :- zip/1;";

        List<ProductionRule> rules = PatternManager.PatternManagerTestClass.testListRules(s);
        assertEquals(4, rules.size());
    }

    @Test
    public void testExpandRules() throws Exception {

        String s = "";
        s += "address :- city, state, zip;";
        s += "city_geo :- city, state;";

        //terminal symbols
        s += "city :- city/1;";
        s += "city :- city/2;";
        s += "state :- state/1;";
        s += "state :- state/2;";
        s += "zip  :- zip/1;";

        List<Pattern> actualPatterns =  PatternManager.PatternManagerTestClass.testListPatterns(s);
        assertEquals(8, actualPatterns.size());
    }
}