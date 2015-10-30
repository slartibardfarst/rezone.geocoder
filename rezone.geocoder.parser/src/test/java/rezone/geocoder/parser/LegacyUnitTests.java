package rezone.geocoder.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



@RunWith(JUnitParamsRunner.class)
public class LegacyUnitTests {
    private static QueryParser _parser;
    private static Gson _gson;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
         _parser = new QueryParser();
        _gson = new GsonBuilder().create();
    }


    @Test
    @Parameters({
            "First Ave New York; 10075                           | 1  |  [{ geo_type: STREET; street: 'First'; street_suffix: 'Ave'; city: 'New York'; state: 'NY'; zip:'10075'}]",
    })
    public void test_with_a_good_name(String input, int expectNumMatches, String expectedAsJson) throws Exception {
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
}

