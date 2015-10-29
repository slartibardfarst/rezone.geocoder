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

    @Test
    public void testOptionalSymbols1() throws Exception {

        String s = "";
        s += "address_line :- street_number, street, [unit];";

        s += "street :- [street_direction], street_name, street_suffix, [street_post_direction];";

        //terminal symbols
        s += "street_number :- street_no/1;";
        s += "street_name :- street/1;";
        s += "street_name :- street/2;";
        s += "street_suffix :- street_suffix/1;";
        s += "street_direction :- street_direction/1;";
        s += "street_post_direction :- street_post_direction/1;";
        s += "unit :- unit/2;";

        List<Pattern> actualPatterns =  PatternManager.PatternManagerTestClass.testListPatterns(s);
        assertEquals(16, actualPatterns.size());
    }

    @Test
    public void testOptionalSymbols2() throws Exception {

        String s = "";
        s += "address_line :- street_number, street_name, [unit];";

        //terminal symbols
        s += "street_number :- street_no/1;";
        s += "street_name :- street/1;";
        s += "street_name :- street/2;";
        s += "unit :- unit/2;";

        List<Pattern> actualPatterns =  PatternManager.PatternManagerTestClass.testListPatterns(s);
        assertEquals(4, actualPatterns.size());
    }
}