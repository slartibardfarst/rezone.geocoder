package rezone.geocoder.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(JUnitParamsRunner.class)
public class LegacyUnitTests {
    private static QueryParser _parser;
    private static Gson _gson;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
         _parser = new QueryParser();
        _gson = new GsonBuilder().create();
    }

    public boolean compare(String str1, String str2) {
        return ((str1 == str2) || (str1 != null && str1.equalsIgnoreCase(str2)));
    }

    @Test
    @Parameters({
            "326 Ste8 BALLSTON AV;Saratoga Springs;NY;12866 |99| [{ geo_type: ADDRESS;street_no: '326';street_direction: null;street: 'Ballston';street_suffix: 'Ave';street_post_direction: null;unit: 'Ste 8';city: 'Saratoga Springs';state: 'NY';zip: '12866'}]",
            "1005 Ste #5 N 9th;Boise;ID;83702 |99| [{ geo_type: ADDRESS;street_no: '1005';street_direction: 'N';street: '9th';street_suffix: null;street_post_direction: null;unit: 'Ste #5';city: 'Boise';state: 'ID';zip: '83702'}]",
            "30900-bk East 160 Ste & Y12;Llano;CA;93544 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: null;street_suffix: null;street_post_direction: null;unit: 'Ste Y12';city: 'Llano';state: 'CA';zip: '93544'}]",
            "111 S S Street STE 5;Dover;DE;19904 |99| [{ geo_type: ADDRESS;street_no: '111';street_direction: 'S';street: 'S';street_suffix: 'Street';street_post_direction: null;unit: 'Ste 5';city: 'Dover';state: 'DE';zip: '19904'}]",
            "49 ste 106 East Brimfield Holland;Brimfield;MA;01010 |99| [{ geo_type: ADDRESS;street_no: '49';street_direction: 'East';street: 'Brimfield Holland';street_suffix: null;street_post_direction: null;unit: 'Ste 106';city: 'Brimfield';state: 'MA';zip: '01010'}]",
            "N Cataract Creek Estates Ph 1-4 --;Williams;AZ;86046 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: 'N';street: 'Cataract Creek';street_suffix: 'Ests';street_post_direction: null;unit: 'Ph 1-4';city: 'Williams';state: 'AZ';zip: '86046'}]",
            "727 W 7th Street PH 1522;Los Angeles (City);CA;90017 |99| [{ geo_type: ADDRESS;street_no: '727';street_direction: 'W';street: '7th';street_suffix: 'Street';street_post_direction: null;unit: 'PH 1522';city: 'Los Angeles (city)';state: 'CA';zip: '90017'}]",
            "35109 Highway 79 Spc #145;Warner Springs;CA;92086 |99| [{ geo_type: ADDRESS;street_no: '35109';street_direction: null;street: 'Highway 79';street_suffix: null;street_post_direction: null;unit: 'Spc #145';city: 'Warner Springs';state: 'CA';zip: '92086'}]",
            "3157 East I  SPC B-11 Ave;Lancaster;CA;93535 |99| [{ geo_type: ADDRESS;street_no: '3157';street_direction: 'E';street: 'I';street_suffix: 'Ave';street_post_direction: null;unit: 'Spc B11';city: 'Lancaster';state: 'CA';zip: '93535'}]",
            "212 S MC CLOUD AVE.; SPC 24;McCloud;CA;96057 |99| [{ geo_type: ADDRESS;street_no: '212';street_direction: 'S';street: 'Mc Cloud';street_suffix: 'Ave';street_post_direction: null;unit: 'Spc 24';city: 'McCloud';state: 'CA';zip: '96057'}]",
            "469010 Hwy Trlr 4;Sagle;ID;83864 |99| [{ geo_type: ADDRESS;street_no: '469010';street_direction: null;street: 'Hwy';street_suffix: 'Trlr';street_post_direction: null;unit: '4';city: 'Sagle';state: 'ID';zip: '83864'}]",
            "8 Cannery Village Marina Slip #8 8;San Juan Island;WA;98250 |99| [{ geo_type: ADDRESS;street_no: '8';street_direction: null;street: 'Cannery';street_suffix: 'Vlg';street_post_direction: null;unit: null;city: 'San Juan Island';state: 'WA';zip: '98250'}]",
            "9 Cannery Village Marina Slip #8 8;San Juan Island;WA;98250 |99| [{ geo_type: ADDRESS;street_no: '9';street_direction: null;street: 'Cannery';street_suffix: 'Vlg';street_post_direction: null;unit: 'Slip 8';city: 'San Juan Island';state: 'WA';zip: '98250'}]",
            "333 Lake Ave. Slip # 21;City of Racine;WI;53403 |99| [{ geo_type: ADDRESS;street_no: '333';street_direction: null;street: 'Lake';street_suffix: 'Ave';street_post_direction: null;unit: '# 21';city: 'Racine';state: 'WI';zip: '1068'}]",
            "Boat Slip C-92;Portland;OR;97202 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: null;street_suffix: null;street_post_direction: null;unit: 'Slip C92';city: 'Portland';state: 'OR';zip: '97202'}]",
            "15 E Spring #12 &Boat slip #10;Port Austin;MI;48467 |99| [{ geo_type: ADDRESS;street_no: '15';street_direction: null;street: 'E';street_suffix: 'Spg';street_post_direction: null;unit: '# 10';city: 'Port Austin';state: 'MI';zip: '48467'}]",
            "12505 Oaks North Unit: #: 243;San Diego;CA;92128 |99| [{ geo_type: ADDRESS;street_no: '12505';street_direction: null;street: 'Oaks North';street_suffix: null;street_post_direction: null;unit: 'Unit 243';city: 'San Diego';state: 'CA';zip: '92128'}]",
            "40751 NORTH SHORE #76;FAWNSKIN;CA;92333 |99| [{ geo_type: ADDRESS;street_no: '40751';street_direction: 'north';street: 'Shore';street_suffix: null;street_post_direction: null;unit: '#76';city: 'Fawnskin';state: 'CA';zip: '92333'}]",
            "831-53 N 19TH ST ##1;PHILADELPHIA;PA;19130 |99| [{ geo_type: ADDRESS;street_no: '831-53';street_direction: 'N';street: '19th';street_suffix: 'St';street_post_direction: null;unit: '# 1';city: 'Philadelphia';state: 'PA';zip: '19130'}]",
            "763-65 S 8TH ST ##3F;PHILADELPHIA;PA;19147 |99| [{ geo_type: ADDRESS;street_no: '763-65';street_direction: 'S';street: '8th';street_suffix: 'St';street_post_direction: null;unit: '# 3F';city: 'Philadelphia';state: 'PA';zip: '19147'}]",
            "4500 FOUR MILE RUN DR ##P302;ARLINGTON;VA;22204 |99| [{ geo_type: ADDRESS;street_no: '4500';street_direction: null;street: 'Four Mile Run';street_suffix: 'Dr';street_post_direction: null;unit: 'Apt 302';city: 'Arlington';state: 'VA';zip: '22204'}]",
            "69-4 Urb. Cataluna Calle # -4;Barceloneta;PR;00617 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: null;street_suffix: null;street_post_direction: null;unit: '# 4';city: 'Barceloneta';state: 'PR';zip: '00617'}]",
            "277 Main St # 6-B;Aurora;NY;14052 |99| [{ geo_type: ADDRESS;street_no: '277';street_direction: null;street: 'Main';street_suffix: 'St';street_post_direction: null;unit: '# 6-B';city: 'Aurora';state: 'NY';zip: '14052'}]",
            "555 Vasquez Rd # C-1;Winter Park;CO;80482 |99| [{ geo_type: ADDRESS;street_no: '555';street_direction: null;street: 'Vasquez';street_suffix: 'Rd';street_post_direction: null;unit: '# C-1';city: 'Winter Park';state: 'CO';zip: '80482'}]",
            "2300 Cienaga St Rt # - 1 68;Oceano;CA;93445 |99| [{ geo_type: ADDRESS;street_no: '2300';street_direction: null;street: 'Cienaga';street_suffix: 'St';street_post_direction: null;unit: 'Spc 1';city: 'Oceano';state: 'CA';zip: '8944'}]",
            "89A #216 Westwood Chateau;Marion;NC;28752 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: 'Westwood Chateau';street_suffix: 'Dr';street_post_direction: null;unit: '# 216';city: 'Marion';state: 'NC';zip: '6544'}]",
            "9999 Goa Wy Unit Lot 1;Port Angeles;WA;98362 |99| [{ geo_type: ADDRESS;street_no: '9999';street_direction: null;street: 'Goa';street_suffix: 'Way';street_post_direction: null;unit: 'Lot 1';city: 'Port Angeles';state: 'WA';zip: '98362'}]",
            "77 Wheatland Unit 1 Unit: 1;Somerville;MA;02145 |99| [{ geo_type: ADDRESS;street_no: '77';street_direction: null;street: 'Wheatland';street_suffix: 'St';street_post_direction: null;unit: 'Unit 1';city: 'Somerville';state: 'MA';zip: '2015'}]",
            "12244 Short Unit 3 #3;Filburns Island;OH;45365 |99| [{ geo_type: ADDRESS;street_no: '12244';street_direction: null;street: 'Short';street_suffix: null;street_post_direction: null;unit: 'Unit 3';city: 'Filburns Island';state: 'OH';zip: '45365'}]",
            "124 N 3820 W 21; Hurricane; UT 84737 |99| [{ geo_type: ADDRESS;street_no: '124';street_direction: 'N';street: '3820';street_suffix: null;street_post_direction: 'W';unit: '21';city: 'Hurricane';state: 'UT';zip: '84737'}]",
            "49TH AVE DR E; Bradenton; FL |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: '49th Ave';street_suffix: 'Dr';street_post_direction: 'E';unit: null;city: 'Bradenton';state: 'FL';zip: null}]",
            "98TH Av Ct; Gig Harbor; WA 98329 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: '98th Av';street_suffix: 'Ct';street_post_direction: null;unit: null;city: 'Gig Harbor';state: 'WA';zip: '98329'}]",
            "Summit Unit: 3;4;Descanso;CA;91916 |99| [{ geo_type: ADDRESS;street_no: null; street_direction: null;street: 'Summit'; street_suffix: null;street_post_direction: null; unit: 'Units 3 & 4';city: 'Descano';state: 'CA';zip: '91916'}]",
            "0 Rolling Hills Way;Sequim;WA 98382 |99| [{ geo_type: ADDRESS;street_no: '0';street_direction: null;street: 'Rolling Hills';street_suffix: 'Way';street_post_direction: null;unit: null;city: 'Sequim';state: 'WA';zip: '98382'}]",
            "xxx Shoreline Drive;Panacea;FL;32346 |99| [{ geo_type: STREET;street_no: null;street_direction: null;street: 'XXX Shoreline';street_suffix: 'Drive';street_post_direction: null;unit: null;city: 'Panacea';state: 'FL';zip: '32346'}]",
            "Xxxx Highland Meadows Dr;Williams; AZ |99| [{ geo_type: STREET;street_no: null;street_direction: null;street: 'XXXX Highland Meadows';street_suffix: 'Dr';street_post_direction: null;unit: null;city: 'Williams';state: 'AZ';zip: null}]",
            "5 Lot s Edenbury;Rome;GA;30161 |99| [{ geo_type: ADDRESS;street_no: '5';street_direction: null;street: 'Edenbury';street_suffix: null;street_post_direction: null;unit: 'LOT S';city: 'Rome';state: 'GA';zip: '30161'}]",
            "2828 N HIGHWAY 89 (C);Fish Haven;ID;83287 |99| [{ geo_type: ADDRESS;street_no: '2828'; street_direction: 'N';street: 'Highway 89';street_suffix: null;street_post_direction: null; unit: '# C';city: 'Fish Haven';state: 'ID';zip: '83287'}]",
            "1/2 307 S NACHES AVE; Yakima; WA |99| [{ geo_type: ADDRESS;street_no: '307'; street_direction: 'S';street: 'Naches'; street_suffix: 'Ave';street_post_direction: null; unit: '# 1 & 2';city: 'Yakima';state: 'WA';zip: null}]",
            "1/2/3 307 S NACHES AVE; Yakima; WA |99| [{ geo_type: ADDRESS;street_no: '307'; street_direction: 'S';street: 'Naches'; street_suffix: 'Ave';street_post_direction: null; unit: '# 1 & 2 & 3';city: 'Yakima';state: 'WA';zip: null}]",
            "1/2 NACHES AVE; Yakima; WA |99| [{ geo_type: ADDRESS;street_no: '1/2'; street_direction: 'S';street: 'Naches'; street_suffix: 'Ave';street_post_direction: null; unit: null;city: 'Yakima';state: 'WA';zip: null}]",

            "4140 Three Oaks Blvd; Apt 4a;TROY;MI;48098 |99| [{ geo_type: ADDRESS;street_no: '4140';street_direction: null;street: 'Three Oaks';street_suffix: 'Blvd';street_post_direction: null;unit: 'Apt 4A';city: 'Troy';state: 'MI';zip: '48098'}]",
            "114 Mt. Drive Apt 2A;Mount Pocono;PA;18344 |99| [{ geo_type: ADDRESS;street_no: '114';street_direction: null;street: 'Mt';street_suffix: 'Dr';street_post_direction: null;unit: 'Apt 2A';city: 'Mount Pocono';state: 'PA';zip: '18344'}]",
            "3701 CRESCENT RIM DR APT;BOISE;ID;83706 |99| [{ geo_type: ADDRESS;street_no: '3701';street_direction: null;street: 'Crescent Rim';street_suffix: 'Dr';street_post_direction: null;unit: 'Apt';city: 'Boise';state: 'ID';zip: '83706'}]",
            "1200 PA ave apt 1;Bridgeville;PA;15017 |99| [{ geo_type: ADDRESS;street_no: '1200';street_direction: null;street: 'Pa';street_suffix: 'Ave';street_post_direction: null;unit: 'Apt 1';city: 'Bridgeville';state: 'PA';zip: '15017'}]",
            "218 W. Austin Apt 1-4;Nacogdoches;TX;75961 |99| [{ geo_type: ADDRESS;street_no: '218';street_direction: 'W';street: 'Austin';street_suffix: null;street_post_direction: null; unit: '# 1-4';city: 'Nacogdoches';state: 'TX'; zip: '2768'}]",
            "826 Apt 1-4 Magnolia Ave;Long Beach;CA;90813 |99| [{ geo_type: ADDRESS;street_no: '826';street_direction: null;street: 'Magnolia';street_suffix: 'Ave';street_post_direction: null;unit: 'Apt 1-4';city: 'Long Beach';state: 'CA';zip: '90813'}]",
            "260-290 West Washington Apt 1-10;Martinsville;IN;46151 |99| [{ geo_type: ADDRESS;street_no: '260-290';street_direction: 'west';street: 'Washington';street_suffix: null;street_post_direction: null;unit: 'Apt 1-10';city: 'Martinsville';state: 'IN';zip: '46151'}]",
            "39.5 Washington Square S - Apt: 2;New York;NY;10012 |99| [{ geo_type: ADDRESS;street_no: '39.5';street_direction: null;street: 'Washington';street_suffix: 'Sq';street_post_direction: 'S';unit: 'Apt 2';city: 'New York';state: 'NY';zip: '10012'}]",
            "6035 Broadway - Apt: 4C;Bronx;NY;10471 |99| [{ geo_type: ADDRESS;street_no: '6035';street_direction: null;street: 'Broadway';street_suffix: null;street_post_direction: null;unit: 'Apt: 4C';city: 'Bronx';state: 'NY';zip: '10471'}]",
            "7 Prospect St Apt403;Morristown Town;NJ;07960 |99| [{ geo_type: ADDRESS;street_no: '7';street_direction: null;street: 'Prospect';street_suffix: 'St';street_post_direction: null;unit: 'Unit 403';city: 'Morristown';state: 'NJ';zip: '9302'}]",
            "209 Apt9 WATERDOWN DR.;Fayetteville;NC;28314 |99| [{ geo_type: ADDRESS;street_no: '209';street_direction: null;street: 'Waterdown';street_suffix: 'Dr';street_post_direction: null;unit: 'Apt 9';city: 'Fayetteville';state: 'NC';zip: '28314'}]",
            "190 Olympic Apt17;Port Ludlow;WA;98365 |99| [{ geo_type: ADDRESS;street_no: '190';street_direction: null;street: 'Olympic';street_suffix: null;street_post_direction: null;unit: 'Apt17';city: 'Port Ludlow';state: 'WA';zip: '98365'}]",
            "Apt211 Harbor Island Inn & Villas;Harbor Island;SC;29920 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: 'Harbor';street_suffix: 'Is';street_post_direction: null;unit: 'Apt 211';city: 'Harbor Island';state: 'SC';zip: '29920'}]",
            "23238APT#1037 ROSEWOOD CT #1037;CALIFORNIA;MD;20619 |99| [{ geo_type: ADDRESS;street_no: '23238';street_direction: null;street: 'Rosewood';street_suffix: 'Ct';street_post_direction: null;unit: '# 1037';city: 'California';state: 'MD';zip: '20619'}]",
            "APT#1 1459 LEARN ROAD;Tannersville;PA;18372 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: '1459 Learn';street_suffix: 'Rd';street_post_direction: null;unit: 'Apt 1';city: 'Tannersville';state: 'PA';zip: '18372'}]",
            "24733 Apt#6 Ridgewalk 66;Murrieta;CA;92562 |99| [{ geo_type: ADDRESS;street_no: '24733';street_direction: null;street: 'Ridgewalk 66';street_suffix: null;street_post_direction: null;unit: 'Apt 6';city: 'Murrieta';state: 'CA';zip: '92562'}]",
            "4051 Nostrand Ave APT.1;Brooklyn;NY;11235 |99| [{ geo_type: ADDRESS;street_no: '4051';street_direction: null;street: 'Nostrand';street_suffix: 'Ave';street_post_direction: null;unit: 'Apt 1';city: 'Brooklyn';state: 'NY';zip: '2240'}]",
            "441 apt.2 WILTON-GANSEVOORT RD;Wilton;NY;12831 |99| [{ geo_type: ADDRESS;street_no: '441';street_direction: null;street: 'Wilton-Gansevoort';street_suffix: 'Rd';street_post_direction: null;unit: 'Apt 2';city: 'Wilton';state: 'NY';zip: '12831'}]",
            "514 Brophy;  Apt.1;Bisbee;AZ;85603 |99| [{ geo_type: ADDRESS;street_no: '514';street_direction: null;street: 'Brophy';street_suffix: null;street_post_direction: null;unit: 'Apt 1';city: 'Bisbee';state: 'AZ';zip: '85603'}]",
            "A Liftside Dr APT.10;Hunter;NY;12442 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: 'A Liftside';street_suffix: 'Dr';street_post_direction: null;unit: 'Apt 10';city: 'Hunter';state: 'NY';zip: '12442'}]",
            "95th Avenue & 111th street..Spacious Studio Apt $1;South Richmond Hill;NY;11419 |99| [{ geo_type: ADDRESS;street_no: '95th';street_direction: null;street: 'Avenue and 111th';street_suffix: 'St';street_post_direction: null;unit: 'Apt 1';city: 'South Richmond Hill';state: 'NY';zip: '11419'}]",
            "Decatur Avenue & E. 197th street..1Br Apt $975;bronx;NY;10458 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: 'Decatur Avenue and E 197th';street_suffix: 'St';street_post_direction: null;unit: 'Apt 975';city: 'Bronx';state: 'NY';zip: '10458'}]",
            "34 Bay Ridge Ave APT.1B;Brooklyn;NY;11209 |99| [{ geo_type: ADDRESS;street_no: '34';street_direction: null;street: 'Bay Ridge';street_suffix: 'Ave';street_post_direction: null;unit: 'Apt 1B';city: 'Brooklyn';state: 'NY';zip: '11209'}]",
            "52-54 Avenue W APT.1A;Brooklyn;NY;11223 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: '52-54';street_suffix: 'Ave';street_post_direction: 'W';unit: 'Apt 1A';city: 'Brooklyn';state: 'NY';zip: '11223'}]",
            "105 Prime Apt.D3 Ave D3;Huntington;NY;11743 |99| [{ geo_type: ADDRESS;street_no: '105';street_direction: null;street: 'Prime';street_suffix: 'Ave';street_post_direction: null;unit: 'Apt D3';city: 'Huntington';state: 'NY';zip: '2006'}]",
            "36 Apt -2b Lake St;Highland Falls;NY;10928 |99| [{ geo_type: ADDRESS;street_no: '36';street_direction: null;street: 'Lake';street_suffix: 'St';street_post_direction: null;unit: 'Apt 2B';city: 'Highland Falls';state: 'NY';zip: '10928'}]",
            "114 Mountain Dr. Apt 2-E;Mount Pocono;PA;18344 |99| [{ geo_type: ADDRESS;street_no: '114';street_direction: null;street: 'Mountain';street_suffix: 'Dr';street_post_direction: null;unit: 'Apt 2E';city: 'Mount Pocono';state: 'PA';zip: '18344'}]",
            "REO COND MIRAMAR 626 APT 2-A-;San Juan;PR;00907 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: null;street_suffix: null;street_post_direction: null;unit: 'Apt 2A';city: 'San Juan';state: 'PR';zip: '00907'}]",
            "500 Paragon Mills Road Apt E-8 ;Nashville;TN;37211 |99| [{ geo_type: ADDRESS;street_no: '500';street_direction: null;street: 'Paragon Mills';street_suffix: 'Road';street_post_direction: null;unit: 'Apt E-8';city: 'Nashville';state: 'TN';zip: '37211'}]",
            "319 Laurens Street; Apt A-7;Aiken;SC;29801 |99| [{ geo_type: ADDRESS;street_no: '319';street_direction: null;street: 'Laurens';street_suffix: 'St';street_post_direction: 'SW';unit: 'Apt A7';city: 'Aiken';state: 'SC';zip: '4851'}]",
            "Apt A-501 Balcones De Monte Real;Carolina;PR;00617 |99| [{ geo_type: ADDRESS;street_no: null;street_direction: null;street: null;street_suffix: null;street_post_direction: null;unit: 'Apt A501';city: 'Carolina';state: 'PR';zip: '00617'}]",

    })
    public void legacy_cases1(String input, int expectNumMatches, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input , dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);


        assertNotNull("Parser response is null\n", actual);
        assertTrue("Parser returned no results\n", 0 != actual.length);

        boolean matchFound = false;
        if (expectNumMatches != 99)
            assertEquals(expectNumMatches, actual.length);

        if (expectNumMatches == 1) {
            if (actual[0].equals(expected[0])) matchFound = true;
        } else {
            for (int i = 0; i < actual.length; i++)
                if (actual[i].equals(expected[0]))
                    matchFound = true;
        }

        if (!matchFound) {
            if (!compare(expected[0].geo_type.toString(), actual[0].geo_type.toString())) {
                System.out.println("\tExpected geo type: " + expected[0].geo_type.toString());
                System.out.println("\tActual geo type: " + actual[0].geo_type.toString());
            }
            if (!compare(expected[0].street_no, actual[0].street_no)) {
                System.out.println("\tExpected street number: " + expected[0].street_no);
                System.out.println("\tActual street number: " + actual[0].street_no +"\n");
            }
            if (!compare(expected[0].street_direction, actual[0].street_direction)) {
                System.out.println("\tExpected street direction: " + expected[0].street_direction);
                System.out.println("\tActual street direction: " + actual[0].street_direction +"\n");
            }
            if (!compare(expected[0].street, actual[0].street)) {
                System.out.println("\tExpected street: " + expected[0].street);
                System.out.println("\tActual street: " + actual[0].street +"\n");
            }
            if (!compare(expected[0].street_suffix, actual[0].street_suffix)) {
                System.out.println("\tExpected street suffix: " + expected[0].street_suffix);
                System.out.println("\tActual street suffix: " + actual[0].street_suffix +"\n");
            }
            if (!compare(expected[0].street_post_direction, actual[0].street_post_direction)) {
                System.out.println("\tExpected street post direction: " + expected[0].street_post_direction);
                System.out.println("\tActual street post direction: " + actual[0].street_post_direction +"\n");
            }
            if (!compare(expected[0].unit, actual[0].unit)) {
                System.out.println("\tExpected unit: " + expected[0].unit);
                System.out.println("\tActual unit: " + actual[0].unit +"\n");
            }
            if (!compare(expected[0].city, actual[0].city)) {
                System.out.println("\tExpected city: " + expected[0].city);
                System.out.println("\tActual city: " + actual[0].city +"\n");
            }
            if (!compare(expected[0].state, actual[0].state)) {
                System.out.println("\tExpected state: " + expected[0].state);
                System.out.println("\tActual state: " + actual[0].state +"\n");
            }
            if (!compare(expected[0].zip, actual[0].zip)) {
                System.out.println("\tExpected zip: " + expected[0].zip);
                System.out.println("\tActual zip: " + actual[0].zip +"\n");
            }
        }
        assertTrue("None of the parser's responses match the expected result.\n", matchFound);

    }


    @Test
    @Parameters({
            "1005 Ste #5 N 9th;Boise;ID;83702 |99| [{ geo_type: ADDRESS;street_no: '1005';street_direction: 'N'; street: '9th';street_suffix: null;street_post_direction: null;unit: 'Ste #5';city: 'Boise'; state: 'ID';zip: '83702'}]",
            "218 W. Austin Apt 1-4;Nacogdoches;TX;75961 |99| [{ geo_type: ADDRESS;street_no: '218';street_direction: 'W';street: 'Austin';street_suffix: null;street_post_direction: null;unit: 'Apt 1-4';city: 'Nacogdoches';state: 'TX';zip: '75961'}]",
            "500 Paragon Mills Road Apt E-8 ;Nashville;TN;37211 |99| [{ geo_type: ADDRESS;street_no: '500';street_direction: null;street: 'Paragon Mills';street_suffix: 'Road';street_post_direction: null;unit: 'Apt E-8';city: 'Nashville';state: 'TN';zip: '37211'}]",
            "5104 S E 30th Avenue;PORTLAND;OR;97202 |99| [{ geo_type: ADDRESS; street_no: '5104'; street_direction: 'S E'; street: '30th'; street_suffix: 'Avenue' ;street_post_direction: null;unit: null; city: 'Portland'; state: 'OR'; zip: '97202'}]",
            "3500 N Hayden -- Unit: 1903;Scottsdale;AZ;85251 |99| [{ geo_type: ADDRESS; street_no: '3500'; street_direction: 'N'; street: 'Hayden'; street_suffix: null;street_post_direction: null; unit:'Unit: 1903' ; city: 'Scottsdale'; state: 'AZ'; zip: '85251'}]",
            "1915 Broadway St Unit ALEXANDER;San Antonio;TX;78215 |99| [{ geo_type: ADDRESS; street_no: '1915';street_direction: null; street: 'Broadway'; street_suffix: 'St';street_post_direction: null; unit:'Unit Alexander' ; city: 'San Antonio'; state: 'TX'; zip: '78215'}]",
            "217 Town And Country Dr;Danville;CA;94526 |99| [{ geo_type: ADDRESS; street_no: '217';street_direction: null; street: 'Town and Country'; street_suffix: 'Dr';street_post_direction: null; unit:null ; city: 'Danville'; state: 'CA'; zip: '94526'}]",
            "277 Main St # 6-B;Aurora;NY;14052 |99| [{ geo_type: ADDRESS; street_no: '277';street_direction: null; street: 'Main'; street_suffix: 'St';street_post_direction: null; unit:'# 6-B' ; city: 'Aurora'; state: 'NY'; zip: '14052'}]",
            "886 Wellman Avenue Unit: 886;Chelmsford;MA;01863 |99| [{ geo_type: ADDRESS; street_no: '886';street_direction: null; street: 'Wellman'; street_suffix: 'Avenue';street_post_direction: null; unit:'Unit: 886' ; city: 'Chelmsford'; state: 'MA'; zip: '01863'}]",
            "43 Blackmount Lane Unit: 43;Fairfield;CT;06825 |99| [{ geo_type: ADDRESS; street_no: '43';street_direction: null; street: 'Blackmount'; street_suffix: 'Lane';street_post_direction: null; unit:'Unit: 43' ; city: 'Fairfield'; state: 'CT'; zip: '06825'}]",
            "234 North West Smith Street;PORTLAND;OR;97202 |99| [{ geo_type: ADDRESS; street_no: '234'; street_direction: 'North'; street: 'West Smith'; street_suffix: 'Street';street_post_direction: null; unit:null ; city: 'Portland'; state: 'OR'; zip: '97202'}]",
            "6829 CINNABAR COAST LANE;North las vegas;NV;89084-1202 |99| [{ geo_type: ADDRESS; street_no: '6829';street_direction: null; street: 'Cinnabar Coast'; street_suffix: 'Lane';street_post_direction: null; unit:null ; city: 'North Las Vegas'; state: 'NV'; zip: '89084-1202'}]",
            "1345 Commonwealth Ave.;Boston;MA;02134 |99| [{ geo_type: ADDRESS; street_no: '1345';street_direction: null; street: 'Commonwealth'; street_suffix: 'Ave' ;street_post_direction: null;unit: null; city: 'Boston'; state: 'MA'; zip: '02134'}]",
            "2030 N Via Silverado Unit: 3;Camp Verde;AZ;86322 |99| [{ geo_type: ADDRESS; street_no: '2030'; street_direction: 'N'; street: 'Via Silverado';street_suffix: null;street_post_direction: null; unit: 'Unit: 3'; city: 'Camp Verde'; state: 'AZ'; zip: '86322'}]",
            "0 BARTHOLOMEW ST;Christmas;FL;32709 |99| [{ geo_type: ADDRESS;street_no: '0';street_direction: null; street: 'Bartholomew'; street_suffix: 'St';street_post_direction: null;unit: null; city: 'Christmas'; state: 'FL'; zip: '32709'}]",
            "3214 S West Street;Covington;GA;30014 |99| [{ geo_type: ADDRESS; street_no: '3214'; street_direction: 'S'; street: 'West'; street_suffix: 'Street';street_post_direction: null;unit: null; city: 'Covington'; state: 'GA'; zip: '30014' }]",
            "133 S JACKSON St -A-7;Denver;CO;80209 |99| [{ geo_type: ADDRESS; street_no: '133'; street_direction: 'S'; street: 'Jackson'; street_suffix: 'St';street_post_direction: null; unit: '# A7' ; city: 'Denver'; state: 'CO'; zip: '80209'}]",
            "24055 NE North Valley Rd;Newberg;OR;97132 |99| [{ geo_type: ADDRESS; street_no: '24055'; street_direction: 'NE'; street: 'North Valley'; street_suffix: 'Rd';street_post_direction: null;unit: null; city: 'Newberg'; state: 'OR'; zip: '97132' }]",
            "13341 Sweet Flag - Gh315;Black Butte Ranch;OR;97759 |99| [{ geo_type: ADDRESS; street_no: '13341';street_direction: null; street: 'Sweet Flag';street_suffix: null;street_post_direction: null; unit: 'Gh315' ; city: 'Black Butte Ranch'; state: 'OR'; zip: '97759'}]",
            "xxx Shoreline Drive;Panacea;FL;32346 |99| [{ geo_type: STREET;street_no: null;street_direction: null; street: 'xxx Shoreline'; street_suffix: 'Drive';street_post_direction: null;unit: null; city: 'Panacea'; state: 'FL'; zip: '32346' }]",
            "XXX SUMMER OAKS BLVD;Bismarck;AR |99| [{ geo_type: STREET;street_no: null;street_direction: null; street: 'xxx Summer Oaks'; street_suffix: 'Blvd';street_post_direction: null;unit: null; city: 'Bismarck'; state: 'AR' ;zip: null}]",
            "618 Meadows; Dallas; PA; 18612 |99| [{ geo_type: ADDRESS; street_no: '618';street_direction: null; street: 'Meadows'; street_suffix: null;street_post_direction: null;unit: null; city: 'Dallas'; state: 'PA'; zip: '18612' }]",
            "5 Lot s Enderbury; Rome; GA; 30161 |99| [{ geo_type: ADDRESS; street_no: 5;street_direction: null; street: 'Enderbury'; street_suffix: null;street_post_direction: null;unit: 'Lot s'; city: 'Rome'; state: 'GA'; zip: '30161' }]",
            "Off Hwy 80 Lot 1-6;Burnsville;NC;28714 |99| [{ geo_type: ADDRESS; street_no: null;street_direction: null; street: 'Off Hwy 80'; street_suffix: null;street_post_direction: null; unit: 'Lot 1-6' ; city: 'Burnsville'; state: 'NC'; zip: '28714'}]",
            "Off Hwy 80 Lot 1thru6;Burnsville;NC;28714 |99| [{ geo_type: ADDRESS; street_no: null;street_direction: null; street: 'Off Hwy 80'; street_suffix: null;street_post_direction: null; unit: 'Lot 1-6' ; city: 'Burnsville'; state: 'NC'; zip: '28714'}]",
            "Lot 1 Entrada;Pecos;NM;87552 |99| [{ geo_type: ADDRESS; street_no: null;street_direction: null; street: 'Entrada'; street_suffix: null;street_post_direction: null;unit: 'Lot 1' ; city: 'Pecos'; state: 'NM'; zip: '87552'}]",
            "89A #216 Westwood Chateau;Marion;NC;28752 |99| [{ geo_type: ADDRESS; street_no: null;street_direction: null; street: 'Westwood Chateau'; street_suffix: null;street_post_direction: null;unit: '# 216' ; city: 'Marion'; state: 'NC'; zip: '28752'}]",
            "433 S Murphy Ave; San Jose; QQ; 00015 |99| [{ geo_type: ADDRESS; street_no: '433'; street_direction: 'S'; street: 'Murphy'; street_suffix: 'Ave';street_post_direction: null; unit: null ; city: 'San Jose'; state: null; zip: '00015'}]",
            "2604 ROAD 123;Meeker;CO |99| [{ geo_type: ADDRESS; street_no: '2604'; street_direction: null; street: 'Road 123'; street_suffix: null;street_post_direction: null; unit: null ; city: 'Meeker'; state: 'CO'; zip: null}]",
            "1819 Fruitvale Blvd Unit: Lt A;Yakima;WA;98902 |99| [{ geo_type: ADDRESS;street_no: '1819';street_direction: null;street: 'Fruitvale';street_suffix: 'Blvd';street_post_direction: null;unit: 'Lot A';city: 'Yakima';state: 'WA';zip: '98902'}]",
            "2559 12th Avenue North West;Columbia Falls;MT;59912 |99| [{ geo_type: ADDRESS; street_no: '2559'; street_direction: null; street: '12th'; street_suffix: 'Ave'; street_post_direction: 'North West'; unit: null ; city: 'Columbia Falls'; state: 'MT'; zip: '59912'}]",
    })
    public void legacy_cases2(String input, int expectNumMatches, String expectedAsJson) throws Exception {
        input = input.replaceAll(";", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input , dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);


        assertNotNull("Parser response is null.\n", actual);
        assertTrue("Parser returned no results.\n", 0 != actual.length);


        boolean matchFound = false;
        if (expectNumMatches != 99)
            assertEquals(expectNumMatches, actual.length);

        if (expectNumMatches == 1) {
            if (actual[0].equals(expected[0])) matchFound = true;
        } else {
            for (int i = 0; i < actual.length; i++)
                if (actual[i].equals(expected[0]))
                    matchFound = true;
        }

        if (!matchFound) {
            if (!compare(expected[0].geo_type.toString(), actual[0].geo_type.toString())) {
                System.out.println("\tExpected geo type: " + expected[0].geo_type.toString());
                System.out.println("\tActual geo type: " + actual[0].geo_type.toString());
            }
            if (!compare(expected[0].street_no, actual[0].street_no)) {
                System.out.println("\tExpected street number: " + expected[0].street_no);
                System.out.println("\tActual street number: " + actual[0].street_no +"\n");
            }
            if (!compare(expected[0].street_direction, actual[0].street_direction)) {
                System.out.println("\tExpected street direction: " + expected[0].street_direction);
                System.out.println("\tActual street direction: " + actual[0].street_direction +"\n");
            }
            if (!compare(expected[0].street, actual[0].street)) {
                System.out.println("\tExpected street: " + expected[0].street);
                System.out.println("\tActual street: " + actual[0].street +"\n");
            }
            if (!compare(expected[0].street_suffix, actual[0].street_suffix)) {
                System.out.println("\tExpected street suffix: " + expected[0].street_suffix);
                System.out.println("\tActual street suffix: " + actual[0].street_suffix +"\n");
            }
            if (!compare(expected[0].street_post_direction, actual[0].street_post_direction)) {
                System.out.println("\tExpected street post direction: " + expected[0].street_post_direction);
                System.out.println("\tActual street post direction: " + actual[0].street_post_direction +"\n");
            }
            if (!compare(expected[0].unit, actual[0].unit)) {
                System.out.println("\tExpected unit: " + expected[0].unit);
                System.out.println("\tActual unit: " + actual[0].unit +"\n");
            }
            if (!compare(expected[0].city, actual[0].city)) {
                System.out.println("\tExpected city: " + expected[0].city);
                System.out.println("\tActual city: " + actual[0].city +"\n");
            }
            if (!compare(expected[0].state, actual[0].state)) {
                System.out.println("\tExpected state: " + expected[0].state);
                System.out.println("\tActual state: " + actual[0].state +"\n");
            }
            if (!compare(expected[0].zip, actual[0].zip)) {
                System.out.println("\tExpected zip: " + expected[0].zip);
                System.out.println("\tActual zip: " + actual[0].zip +"\n");
            }
        }
        assertTrue("None of the parser's responses match the expected result.\n", matchFound);
    }


}

