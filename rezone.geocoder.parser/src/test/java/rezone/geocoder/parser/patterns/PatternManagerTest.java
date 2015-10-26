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
        PatternManager.PatternManagerTestClass.testListPatterns();
        assertTrue(true);
    }
}