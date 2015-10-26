package rezone.geocoder.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@RunWith(JUnitParamsRunner.class)
public class QueryParserTests {
    private static QueryParser _parser;
    private static Gson _gson;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        _parser = new QueryParser();
        _gson = new GsonBuilder().create();
    }


    @Test
    @Parameters({
            "7212 SUN COVE CR;Las Vegas;NV;89128       | [{ address_line: '7212 Sun Cove Cr'; street_no: '7212'; street_direction: null; street: 'Sun Cove'; street_suffix: 'Cr'; street_post_direction: null; city: 'Las Vegas'; state: 'NV'; zip: '89128'; unit: null }]"
    })
    public void parseFullAddress(String input, String  expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");


        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);

        assertNotNull("Parser response is null", actual);
        assertEquals(1, actual.length);
        assertTrue(input, actual[0].equals(expected[0]));
    }

    @Test
    @Parameters({
            "123 Main St; New York; NY 10010       | [{ address_line: '123 Main St'; street_no: '123'; street_direction: null;  street: 'Main'; street_suffix: 'St'; street_post_direction: null; city: 'New York'; state: 'NY'; zip: '10010'; unit: null }]",
            "123 Main St New York. NY 10010          | [{ address_line: '123 Main St'; street_no: '123'; street_direction: null;  street: 'Main'; street_suffix: 'St'; street_post_direction: null; city: 'New York'; state: 'NY'; zip: '10010'; unit: null }]",
            "123 Main St New York NY 10010             | [{ address_line: '123 Main St'; street_no: '123'; street_direction: null;  street: 'Main'; street_suffix: 'St'; street_post_direction: null; city: 'New York'; state: 'NY'; zip: '10010'; unit: null }]",
            "123 E 21st st. Brooklyn NY 11020        | [{ address_line: '123 E 21st St'; street_no: '123'; street_direction: 'E'; street: '21st'; street_suffix: 'St'; street_post_direction: null; city: 'Brooklyn'; state: 'NY'; zip: '11020'; unit: null }]",
            "754 Pharr Rd. Atlanta. Georgia 31035  | [{ address_line: '754 Pharr Rd'; street_no: '754'; street: 'Pharr'; street_suffix: 'Rd'; city: 'Atlanta'; state: 'Georgia'; zip: '31035' }]",
            "Texas 76013                               | [{ state: 'Texas'; zip: '76013' }]",
            "Dallas                                    | [{ city: 'Dallas' }]",
            "CA                                        | [{ state: 'CA' }]",
            "Grand canyon 86023                        | [{ city: 'Grand canyon'; zip: '86023'  }]"
    })
    public void addressitTests(String input, String  expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertNotNull("Parser response is null", actual);
        assertEquals(1, actual.length);
        assertTrue(input, actual[0].equals(expected[0]));
    }

    @Test
    @Parameters({
            "100 Lincoln Rd # 448; Miami Beach; FL 33139 | [{ address_line: '100 Lincoln Rd # 448'; street_no: '100'; street_direction: null;  street: 'Lincoln'; street_suffix: 'Rd'; street_post_direction: null; city: 'Miami Beach'; state: 'FL'; zip: '33139'; unit: '# 448' }]",
            "851 Mink Lane Unit 1-2;Fraser;CO;80442      | [{ address_line: '851 Mink Lane Unit 102'; street_no: '851'; street_direction: null;  street: 'Mink'; street_suffix: 'Lane'; street_post_direction: null; city: 'Fraser'; state: 'CO'; zip: '80442'; unit: 'Unit 1-2' }]",
            "112 Hamilton St Fairview WV 26570           | [{ address_line: '112 Hamilton St'; street_no: '112'; street_direction: null;  street: 'Hamilton'; street_suffix: 'St'; street_post_direction: null; city: 'Fairview'; state: 'WV'; zip: '26570'; unit: null }]"
    })
    public void qaAcceptanceTests(String input, String  expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertNotNull("Parser response is null", actual);
        assertEquals(1, actual.length);
        assertTrue(input, actual[0].equals(expected[0]));
    }
}
