package rezone.geocoder.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import rezone.geocoder.parser.patterns.PatternManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@RunWith(JUnitParamsRunner.class)
public class QueryParserTests {
    private static QueryParser _parser;
    private static Gson _gson;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        //_parser = new QueryParser("/users/awatkins/patterns3.csv");
        _parser = new QueryParser();
        _gson = new GsonBuilder().create();
    }


    @Test
    @Parameters({
            "7212 SUN COVE CR;Las Vegas;NV;89128       | [{ geo_type: ADDRESS; address_line: '7212 Sun Cove Cr'; street_no: '7212'; street_direction: null; street: 'Sun Cove'; street_suffix: 'Cr'; street_post_direction: null; city: 'Las Vegas'; state: 'NV'; zip: '89128'; unit: null }]"
    })
    public void parseFullAddress(String input, String expectedAsJson) throws Exception {
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
            "123 Main St; New York; NY 10010      |1| [{ geo_type: ADDRESS; address_line: '123 Main St'; street_no: '123'; street_direction: null;  street: 'Main'; street_suffix: 'St'; street_post_direction: null; city: 'New York'; state: 'NY'; zip: '10010'; unit: null }]",
            "123 Main St New York. NY 10010       |2| [{ geo_type: ADDRESS; address_line: '123 Main St'; street_no: '123'; street_direction: null;  street: 'Main'; street_suffix: 'St'; street_post_direction: null; city: 'New York'; state: 'NY'; zip: '10010'; unit: null }]",
            "123 Main St New York NY 10010        |2| [{ geo_type: ADDRESS; address_line: '123 Main St'; street_no: '123'; street_direction: null;  street: 'Main'; street_suffix: 'St'; street_post_direction: null; city: 'New York'; state: 'NY'; zip: '10010'; unit: null }]",
            "123 E 21st st. Brooklyn NY 11020     |1| [{ geo_type: ADDRESS; address_line: '123 E 21st St'; street_no: '123'; street_direction: 'E'; street: '21st'; street_suffix: 'St'; street_post_direction: null; city: 'Brooklyn'; state: 'NY'; zip: '11020'; unit: null }]",
            "754 Pharr Rd. Atlanta. Georgia 31035 |1| [{ geo_type: ADDRESS; address_line: '754 Pharr Rd'; street_no: '754'; street: 'Pharr'; street_suffix: 'Rd'; city: 'Atlanta'; state: 'Georgia'; zip: '31035' }]",
            "Texas 76013                          |1| [{ geo_type: STATE; state: 'Texas'; zip: '76013' }]",
            "CA                                   |1| [{ geo_type: STATE; state: 'CA' }]",
            "Grand canyon 86023                   |3| [{ geo_type: CITY; city: 'Grand canyon'; zip: '86023'  }]"
    })
    public void addressitTests(String input, int numExpectedMatches, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertNotNull("Parser response is null", actual);
        assertEquals(numExpectedMatches, actual.length);
        if (numExpectedMatches == 1)
            assertTrue("matchFound", actual[0].equals(expected[0]));
        else {
            boolean matchFound = false;
            for (int i = 0; i < actual.length; i++)
                if (actual[i].equals(expected[0]))
                    matchFound = true;

            assertTrue("matchFound", matchFound);

        }
    }

    @Test
    @Parameters({
            "100 Lincoln Rd # 448; Miami Beach; FL 33139 | [{ geo_type: ADDRESS; address_line: '100 Lincoln Rd # 448'; street_no: '100'; street_direction: null;  street: 'Lincoln'; street_suffix: 'Rd'; street_post_direction: null; city: 'Miami Beach'; state: 'FL'; zip: '33139'; unit: '# 448' }]",
            "851 Mink Lane Unit 1-2;Fraser;CO;80442      | [{ geo_type: ADDRESS; address_line: '851 Mink Lane Unit 102'; street_no: '851'; street_direction: null;  street: 'Mink'; street_suffix: 'Lane'; street_post_direction: null; city: 'Fraser'; state: 'CO'; zip: '80442'; unit: 'Unit 1-2' }]",
            "112 Hamilton St Fairview WV 26570           | [{ geo_type: ADDRESS; address_line: '112 Hamilton St'; street_no: '112'; street_direction: null;  street: 'Hamilton'; street_suffix: 'St'; street_post_direction: null; city: 'Fairview'; state: 'WV'; zip: '26570'; unit: null }]"
    })
    public void qaAcceptanceTests(String input, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertNotNull("Parser response is null", actual);
        assertEquals(1, actual.length);
        assertTrue(input, actual[0].equals(expected[0]));
    }

    // Geos unit tests for specific match method type = 'city'
    @Test
    @Parameters({
            "Chicago; IL                          | [{ geo_type: CITY; city: 'Chicago'; state: 'IL' }]",
    })
    public void qaMatchMethodCityUnitTest(String input, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertNotNull("Parser response is null", actual);
        assertEquals(1, actual.length);
        assertTrue(input, actual[0].equals(expected[0]));
    }

    // Geos unit test for specific match method type = 'street'
    @Test
    @Parameters({
            "Thomas Welborn Rd; Anderson; SC | [{ geo_type: STREET; street: 'Thomas Welborn'; street_suffix: 'Rd'; city: 'Anderson'; state: 'SC'}]",
            "Los Angeles; CA | [{ geo_type: CITY; address_line: null; street_no: null; street_direction: null;  street: null; street_suffix:null; street_post_direction: null; city: 'Los Angeles'; state: 'CA'; zip:null; unit: null }]"
    })
    public void qaMatchMethodStreetUnitTest(String input, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertNotNull("Parser response is null", actual);
        assertEquals(1, actual.length);
        assertTrue(input, actual[0].equals(expected[0]));
    }

    // Geos specific unit test for match method type = 'zip'
    @Test
    @Parameters({
            "90210 | [{ geo_type: ZIP; zip: 90210 }]"
    })
    public void qaMatchMethodZipUnitTest(String input, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertNotNull("Parser response is null", actual);
        assertEquals(1, actual.length);
        assertTrue(input, actual[0].equals(expected[0]));
    }


    // Geos specific unit test for match method type = 'county'
    @Test
    @Parameters({
            "Reagan county; TX | [{ geo_type: COUNTY; state: 'TX'; county: 'Reagan county'}]"
    })
    public void qaMatchMethodCountyTest(String input, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertNotNull("Parser response is null", actual);
        assertEquals(1, actual.length);
        assertTrue(input, actual[0].equals(expected[0]));
    }

    // Geos specific unit test for match method type = 'Neighborhood' note that neighborhoods currently parsed as cities or streets
    @Test
    @Parameters({
            "Bellrose Park; Tampa; FL |2| [{ geo_type: STREET; address_line: null; street_no: null; street_direction: null;  street: 'Bellrose'; street_suffix:'Park'; street_post_direction: null; city: 'Tampa'; state: 'FL'; zip:null; unit: null}]"

    })
    public void qaMatchMethodNeighborhoodTest(String input, int expectedNumMatches, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertEquals(expectedNumMatches, actual.length);
        if (expectedNumMatches == 1)
            assertTrue("matchFound", actual[0].equals(expected[0]));
        else {
            boolean matchFound = false;
            for (int i = 0; i < actual.length; i++)
                if (actual[i].equals(expected[0]))
                    matchFound = true;

            assertTrue("matchFound", matchFound);
        }
    }

    // GEOV-329
    @Test
    @Parameters({
            "S State Line Rd Pittsburg KS 64832    | 1 |  [{ geo_type: STREET; street_direction: 'S';  street: 'State Line'; street_suffix: 'Rd'; city: 'Pittsburg'; state: 'KS'; zip:'64832'}]",
            "Fox AK                                | 1 |  [{ geo_type: CITY; city: 'Fox'; state: 'AK'}]",
            "Clark county ID                       | 1 |  [{ geo_type: COUNTY; county: 'Clark county'; state: 'ID'}]",
            "Ester Parks Highway West Fairbanks AK | 2 |  [{ geo_type: STREET; street: 'Ester Parks'; street_suffix: 'Highway'; city: 'West Fairbanks'; state: 'AK'};{ geo_type: STREET; street: 'Ester Parks'; street_suffix: 'Highway'; street_post_direction: 'West'; city: 'Fairbanks'; state: 'AK'}]"
    })
    public void GEOV329_tests(String input, int expectNumMatches, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertNotNull("Parser response is null", actual);
        assertEquals(expectNumMatches, actual.length);
        assertTrue(input, actual[0].equals(expected[0]));
    }

    //first 8 from https://wiki.move.com/display/ps/Expected+variations+in+Street+Address+input+formatting
    //With or without punctuation (e.g. no comma separators)
    //Leading zeros in house number
    //Special characters in house number (e.g. hyphen in Queens, NY addresses)
    // Pre- and post-directional synonyms (e.g. "NE", "Northeast", "North East")
    // two directionals
    @Test
    @Parameters({
            "227B Oak St Medford;NY;11763                       | 1 |  [{ geo_type: ADDRESS; street_no: '227B'; street: 'Oak'; street_suffix: 'St'; city: 'Medford'; state: 'NY'; zip:'11763'}]",
            "227B Oak St Medford NY;11763                       | 1 |  [{ geo_type: ADDRESS; street_no: '227B'; street: 'Oak'; street_suffix: 'St'; city: 'Medford'; state: 'NY'; zip:'11763'}]",
            "227B Oak St Medford NY 11763                       | 1 |  [{ geo_type: ADDRESS; street_no: '227B'; street: 'Oak'; street_suffix: 'St'; city: 'Medford'; state: 'NY'; zip:'11763'}]",
            "164-176 Rollstone Avenue;West Sayville;NY 11796    | 1 |  [{ geo_type: ADDRESS; street_no: '164-176'; street: 'Rollstone'; street_suffix: 'Avenue'; city: 'West Sayville'; state: 'NY'; zip:'11796'}]",
            "0364 Vermont St Lindenhurst 11757                  | 1 |  [{ geo_type: ADDRESS; street_no: '0364'; street: 'Vermont'; street_suffix: 'St'; city: 'Lindenhurst'; zip:'11757'}]",
            "572 E Willow St Elizabethtown PA                   | 1 |  [{ geo_type: ADDRESS; street_no: '572';  street_direction: 'E'; street: 'Willow'; street_suffix: 'St'; city: 'Elizabethtown'; state:'PA'}]",
            "572 NE Willow St Elizabethtown PA                  | 1 |  [{ geo_type: ADDRESS; street_no: '572';  street_direction: 'NE'; street: 'Willow'; street_suffix: 'St'; city: 'Elizabethtown'; state:'PA'}]",
            "572 northeast Willow St Elizabethtown PA           | 1 |  [{ geo_type: ADDRESS; street_no: '572';  street_direction: 'northeast'; street: 'Willow'; street_suffix: 'St'; city: 'Elizabethtown'; state:'PA'}]",
            "572 north east Willow St Elizabethtown PA          | 2 |  [{ geo_type: ADDRESS; street_no: '572';  street_direction: 'north east'; street: 'Willow'; street_suffix: 'St'; city: 'Elizabethtown'; state:'PA'}]",
            "10776 Mieras Dr NE; Sparta;MI;49345                | 1 |  [{ geo_type: ADDRESS; street_no: '10776';  street: 'Mieras'; street_suffix: 'Dr'; street_post_direction: 'NE'; city: 'Sparta'; state:'MI'; zip: '49345'}]",
            "10776 Mieras Dr NE Sparta;MI;49345                 | 2 |  [{ geo_type: ADDRESS; street_no: '10776';  street: 'Mieras'; street_suffix: 'Dr'; street_post_direction: 'NE'; city: 'Sparta'; state:'MI'; zip: '49345'}]",
            "5870 State Route 669 NE Somerset OH                | 2 |  [{ geo_type: ADDRESS; street_no: '5870'; street: 'State Route 669'; street_post_direction: 'NE'; city: 'Somerset'; state:'OH'}]",
            "249 E 275 Rd N Marshall 47859                      | 3 |  [{ geo_type: ADDRESS; street_no: '249';  street_direction: 'E'; street: '275'; street_suffix: 'Rd'; street_post_direction: 'N'; city: 'Marshall'; zip:'47859'}]"
    })
    public void expectedStreetAddressVariationTests_1_to_8(String input, int expectNumMatches, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertNotNull("Parser response is null", actual);
        assertEquals(expectNumMatches, actual.length);
        if (expectNumMatches == 1)
            assertTrue("matchFound", actual[0].equals(expected[0]));
        else {
            boolean matchFound = false;
            for (int i = 0; i < actual.length; i++)
                if (actual[i].equals(expected[0]))
                    matchFound = true;

            assertTrue("matchFound", matchFound);
        }
    }


    @Test
    @Parameters({
            "First Ave New York; 10075                           | 1  |  [{ geo_type: STREET; street: 'First'; street_suffix: 'Ave'; city: 'New York'; state: 'NY'; zip:'10075'}]",
            "1st Ave New York; 10075                             | 99 |  [{ geo_type: STREET; street: '1st'; street_suffix: 'Ave'; city: 'New York'; state: 'NY'; zip:'10075'}]",
            "1009 W Main Ave; Chewelah;WA 99109                  | 1  |  [{ geo_type: ADDRESS; street_no: '1009'; street_direction: 'W'; street: 'Main'; street_suffix: 'Ave'; city: 'Chewelah'; state: 'WA'; zip:'99109'}]",
            "1009 W Main; Chewelah;WA 99109                      | 99 |  [{ geo_type: ADDRESS; street_no: '1009'; street_direction: 'W'; street: 'Main'; city: 'Chewelah'; state: 'WA'; zip:'99109'}]",
            "1009 W Main; Chewelah;WA 99109                      | 99 |  [{ geo_type: ADDRESS; street_no: '1009'; street_direction: 'W'; street: 'Main'; city: 'Chewelah'; state: 'WA'; zip:'99109'}]",
            "2565 Broadway St Unit 234BC; Vancouver;WA; 98663    | 99 |  [{ geo_type: ADDRESS; street_no: '2565'; street: 'Broadway'; street_suffix: 'St'; unit: 'Unit 234BC'; city: 'Vancouver'; state: 'WA'; zip:'98663'}]",
            "2565 Broadway St # 234BC; Vancouver;WA; 98663       | 99 |  [{ geo_type: ADDRESS; street_no: '2565'; street: 'Broadway'; street_suffix: 'St'; unit: '# 234BC'; city: 'Vancouver'; state: 'WA'; zip:'98663'}]",
            "2565 Broadway St 234BC; Vancouver;WA; 98663         | 99 |  [{ geo_type: ADDRESS; street_no: '2565'; street: 'Broadway'; street_suffix: 'St'; unit: '234BC'; city: 'Vancouver'; state: 'WA'; zip:'98663'}]",
            "1465 E 41st Ave; Vancouver 98661-5885               | 1  |  [{ geo_type: ADDRESS; street_no: '1465'; street_direction: 'E'; street: '41st'; street_suffix: 'Ave'; city: 'Vancouver'; zip:'98661-5885'}]"
    })
    public void expectedStreetAddressVariationTests_9_to_end(String input, int expectNumMatches, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertNotNull("Parser response is null", actual);
        if (expectNumMatches != 99)
            assertEquals(expectNumMatches, actual.length);

        if (expectNumMatches == 1)
            assertTrue("matchFound", actual[0].equals(expected[0]));
        else {
            boolean matchFound = false;
            for (int i = 0; i < actual.length; i++)
                if (actual[i].equals(expected[0]))
                    matchFound = true;

            assertTrue("matchFound", matchFound);

        }
    }

//    @Test
//    public void run12k()
//    {
//        int numFailed = 0;
//        int numSucceeded = 0;
//        List<String> failedList = new ArrayList<>();
//
//        String filename = "D:\\Dev\\github\\sds.address.parsing.api\\AddressParsing.Tests\\TestCases\\TestExpectations12k.txt";
//        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                if(line.contains("- input -"))
//                {
//                    String inputString = br.readLine();
//                    Geo[] actual = _parser.parse(inputString);
//                    if(actual.length == 0) {
//                        numFailed++;
//                        failedList.add(inputString);
//                    }
//                    else
//                        numSucceeded++;
//                }
//            }
//
//            System.out.format("num succeeded: %d", numSucceeded);
//            System.out.format("num failed: %d", numFailed);
//        }
//        catch(Exception e)
//        {
//            assertTrue(false);
//        }
//    }
}
