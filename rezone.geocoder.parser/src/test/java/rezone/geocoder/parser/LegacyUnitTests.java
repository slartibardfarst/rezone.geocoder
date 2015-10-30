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
            "326 Ste8 BALLSTON AV;Saratoga Springs;NY;12866 |99| [{ geo_type: ADDRESS; address_line: '326 Ballston Ave';street_no: '326';street_suffix: 'Ave';city: 'Saratoga Springs';state: 'NY';zip: '12866';unit: 'Ste 8'}]",
            "1005 Ste #5 N 9th;Boise;ID;83702 |99| [{ geo_type: ADDRESS; address_line: '1005 N 9th';street_no: '1005';street: 'N 9th';city: 'Boise';state: 'ID';zip: '83702';unit: 'Ste 5'}]",
            "30900-bk East 160 Ste & Y12;Llano;CA;93544 |99| [{ geo_type: ADDRESS; address_line: '30900-bk East 160';city: 'Llano';state: 'CA';zip: '93544';unit: 'Ste Y12'}]",
            "111 S S Street STE 5;Dover;DE;19904 |99| [{ geo_type: ADDRESS; address_line: '111 S S St';street_no: '111';street_direction: '';street_suffix: 'St';city: 'Dover';state: 'DE';zip: '19904';unit: 'Ste 5'}]",
            "49 ste 106 East Brimfield Holland;Brimfield;MA;01010 |99| [{ geo_type: ADDRESS; address_line: '49 East Brimfield Holland';street_no: '49';street: 'East Brimfield Holland';city: 'Brimfield';state: 'MA';zip: '01010';unit: 'Ste 106'}]",
            "N Cataract Creek Estates Ph 1-4 --;Williams;AZ;86046 |99| [{ geo_type: ADDRESS; address_line: 'N Cataract Creek Ests';street_direction: 'N';street_suffix: 'Ests';city: 'Williams';state: 'AZ';zip: '86046';unit: 'Ph 1-4'}]",
            "727 W 7th Street PH 1522;Los Angeles (City);CA;90017 |99| [{ geo_type: ADDRESS; address_line: '727 W 7th St';street_no: '727';street_direction: 'W';street_post_direction: '';city: 'Los Angeles';state: 'CA';zip_plus_four: '3707';unit: '# 1522'}]",
            "35109 Highway 79 Spc #145;Warner Springs;CA;92086 |99| [{ geo_type: ADDRESS; address_line: '35109 Highway 79';street_no: '35109';street_direction: '';street_post_direction: '';city: 'Warner Springs';state: 'CA';zip_plus_four: '9704';unit: 'Spc 145'}]",
            "3157 East I  SPC B-11 Ave;Lancaster;CA;93535 |99| [{ geo_type: ADDRESS; address_line: '3157 E I Ave';street_no: '3157';street_direction: 'E';street_suffix: 'Ave';city: 'Lancaster';state: 'CA';zip: '93535';unit: 'Spc B11'}]",
            "212 S MC CLOUD AVE.; SPC 24;McCloud;CA;96057 |99| [{ geo_type: ADDRESS; address_line: '212 S Mc Cloud Ave';street_no: '212';street_direction: 'S';street_suffix: 'Ave';city: 'McCloud';state: 'CA';zip: '96057';unit: 'Spc 24'}]",
            "469010 Hwy Trlr 4;Sagle;ID;83864 |99| [{ geo_type: ADDRESS; address_line: '469010 Hwy';street_no: '469010';street_suffix: 'Trlr';city: 'Sagle';state: 'ID';zip: '83864';unit: 'Trlr 4'}]",
            "8 Cannery Village Marina Slip #8 8;San Juan Island;WA;98250 |99| [{ geo_type: ADDRESS; address_line: '8 Cannery Vlg Marina';street_no: '8';street_suffix: 'Vlg';city: 'San Juan Island';state: 'WA';zip: '98250';unit: null}]",
            "9 Cannery Village Marina Slip #8 8;San Juan Island;WA;98250 |99| [{ geo_type: ADDRESS; address_line: '9 Cannery Vlg Marina';street_no: '9';street_suffix: 'Vlg';city: 'San Juan Island';state: 'WA';zip: '98250';unit: 'Slip 8'}]",
            "333 Lake Ave. Slip # 21;City of Racine;WI;53403 |99| [{ geo_type: ADDRESS; address_line: '333 Lake Ave';street_no: '333';street_direction: '';street_post_direction: '';city: 'Racine';state: 'WI';zip_plus_four: '1068';unit: '# 21'}]",
            "Boat Slip C-92;Portland;OR;97202 |99| [{ geo_type: ADDRESS; address_line: 'Boat Slip';city: 'Portland';state: 'OR';zip: '97202';unit: 'Slip C92'}]",
            "15 E Spring #12 &Boat slip #10;Port Austin;MI;48467 |99| [{ geo_type: ADDRESS; address_line: '15 E Spg 12 and Boat';street_no: '15';street_direction: '';street_suffix: 'Spg';city: 'Port Austin';state: 'MI';zip: '48467';unit: '# 10'}]",
            "12505 Oaks North Unit: #: 243;San Diego;CA;92128 |99| [{ geo_type: ADDRESS; address_line: '12505 Oaks North';street_no: '12505';street: 'Oaks North';city: 'San Diego';state: 'CA';zip: '92128';unit: 'Unit 243'}]",
            "40751 NORTH SHORE #76;FAWNSKIN;CA;92333 |99| [{ geo_type: ADDRESS; address_line: '40751 North Shr';street_no: '40751';street_direction: '';street_suffix: 'Shr';city: 'Fawnskin';state: 'CA';zip: '92333';unit: '# 76'}]",
            "831-53 N 19TH ST ##1;PHILADELPHIA;PA;19130 |99| [{ geo_type: ADDRESS; address_line: '831-53 N 19th St';street_no: '831-53';street_direction: 'N';street_suffix: 'St';city: 'Philadelphia';state: 'PA';zip: '19130';unit: '# 1'}]",
            "763-65 S 8TH ST ##3F;PHILADELPHIA;PA;19147 |99| [{ geo_type: ADDRESS; address_line: '763-65 S 8th St';street_no: '763-65';street_direction: 'S';street_suffix: 'St';city: 'Philadelphia';state: 'PA';zip: '19147';unit: '# 3F'}]",
            "4500 FOUR MILE RUN DR ##P302;ARLINGTON;VA;22204 |99| [{ geo_type: ADDRESS; address_line: '4500 Four Mile Run Dr';street_no: '4500';street_suffix: 'Dr';city: 'Arlington';state: 'VA';zip: '22204';unit: 'Apt 302'}]",
            "69-4 Urb. Cataluna Calle # -4;Barceloneta;PR;00617 |99| [{ geo_type: ADDRESS; address_line: '69-4 Urb Cataluna Calle';city: 'Barceloneta';state: 'PR';zip: '00617';unit: '# 4'}]",
            "277 Main St # 6-B;Aurora;NY;14052 |99| [{ geo_type: ADDRESS; address_line: '277 Main St';street_no: '277';street_direction: '';street_post_direction: '';city: 'East Aurora';state: 'NY';zip_plus_four: '1600';unit: 'Apt 6'}]",
            "555 Vasquez Rd # C-1;Winter Park;CO;80482 |99| [{ geo_type: ADDRESS; address_line: '555 Vasquez Rd';street_no: '555';street_suffix: 'Rd';city: 'Winter Park';state: 'CO';zip: '80482';unit: '# C1'}]",
            "2300 Cienaga St Rt # - 1 68;Oceano;CA;93445 |99| [{ geo_type: ADDRESS; address_line: '2300 Cienaga St 68';street_no: '2300';street_direction: '';street_post_direction: '';city: 'Oceano';state: 'CA';zip_plus_four: '8944';unit: 'Spc 1'}]",
            "89A #216 Westwood Chateau;Marion;NC;28752 |99| [{ geo_type: ADDRESS; address_line: '89A Westwood Chateau Dr # 216';street_suffix: 'Dr';city: 'Marion';state: 'NC';zip_plus_four: '6544';unit: '# 216'}]",
            "9999 Goa Wy Unit Lot 1;Port Angeles;WA;98362 |99| [{ geo_type: ADDRESS; address_line: '9999 Goa Way';street_no: '9999';street_suffix: 'Way';city: 'Port Angeles';state: 'WA';zip: '98362';unit: 'Lot 1'}]",
            "77 Wheatland Unit 1 Unit: 1;Somerville;MA;02145 |99| [{ geo_type: ADDRESS; address_line: '77 Wheatland St Unit 1';street_no: '77';street_direction: '';street_post_direction: '';city: 'Somerville';state: 'MA';zip_plus_four: '2015';unit: 'Unit 1'}]",
            "12244 Short Unit 3 #3;Filburns Island;OH;45365 |99| [{ geo_type: ADDRESS; address_line: '12244 Short';street_no: '12244';street: 'Short';city: 'Filburns Island';state: 'OH';zip: '45365';unit: 'Unit 3'}]",
            "124 N 3820 W 21; Hurricane; UT 84737 |99| [{ geo_type: ADDRESS; address_line: '124 N 3820 W # 21';street_no: '124';street_direction: 'N';street_post_direction: 'W';city: 'Hurricane';state: 'UT';zip_plus_four: '3017';unit: '# 21'}]",
            "49TH AVE DR E; Bradenton; FL |99| [{ geo_type: ADDRESS; address_line: '49th Ave Dr E';street_post_direction: 'E';city: 'Bradenton';state: 'FL'}]",
            "98TH Av Ct; Gig Harbor; WA 98329 |99| [{ geo_type: ADDRESS; address_line: '98th Av Ct';street_suffix: 'Ct';city: 'Gig Harbor';state: 'WA';zip: '98329'}]",
            "Summit Unit: 3;4;Descanso;CA;91916 |99| [{ geo_type: ADDRESS; address_line: 'Summit Unit 3 & 4';street_no: null; street_direction: null; street_suffix: null;city: 'Descano';state: 'CA';zip: '91916'; unit: 'Units 3 & 4'}]",
            "0 Rolling Hills Way;Sequim;WA 98382 |99| [{ geo_type: ADDRESS; address_line: 'Rolling Hills Way';street_suffix: 'Way';city: 'Sequim';state: 'WA';zip: '98382'}]",
            "xxx Shoreline Drive;Panacea;FL;32346 |99| [{ geo_type: ADDRESS; address_line: 'Shoreline Dr';street_suffix: 'Dr';city: 'Panacea';state: 'FL';zip: '32346'}]",
            "Xxxx Highland Meadows Dr;Williams; AZ |99| [{ geo_type: ADDRESS; address_line: 'Highland Meadows Dr';street_suffix: 'Dr';city: 'Williams';state: 'AZ'}]",
            "5 Lot s Edenbury;Rome;GA;30161 |99| [{ geo_type: ADDRESS; address_line: '5 Lots Edenbury';street_no: '5';street: 'Edenbury';city: 'Rome';state: 'GA';zip: '30161'}]",
            "2828 N HIGHWAY 89 (C);Fish Haven;ID;83287 |99| [{ geo_type: ADDRESS; address_line: '2828 N HIGHWAY 89 # C';street_no: '2828'; street_direction: 'N';street: 'Highway 89';city: 'Fish Haven';state: 'ID';zip: '83287'; unit: '# C'}]",
            "1/2 307 S NACHES AVE; Yakima; WA |99| [{ geo_type: ADDRESS; address_line: '307 S Naches Ave # 1 & 2';street_no: '307'; street_direction: 'S'; street_suffix: 'Ave';city: 'Yakima';state: 'WA';zip: null; unit: '# 1 & 2'}]",
            "1/2/3 307 S NACHES AVE; Yakima; WA |99| [{ geo_type: ADDRESS; address_line: '307 S Naches Ave # 1; 2; 3';street_no: '307'; street_direction: 'S'; street_suffix: 'Ave';city: 'Yakima';state: 'WA';zip: null; unit: '# 1; 2; 3'}]",
            "1/2 NACHES AVE; Yakima; WA |99| [{ geo_type: ADDRESS; address_line: '1/2 Naches Ave';street_no: '1/2'; street_direction: 'S'; street_suffix: 'Ave';city: 'Yakima';state: 'WA';zip: null; unit: null}]",
            "4140 Three Oaks Blvd; Apt 4a;TROY;MI;48098 |99| [{ geo_type: ADDRESS; address_line: '4140 Three Oaks Blvd';street_no: '4140';street_suffix: 'Blvd';city: 'Troy';state: 'MI';zip: '48098';unit: 'Apt 4A'}]",
            "114 Mt. Drive Apt 2A;Mount Pocono;PA;18344 |99| [{ geo_type: ADDRESS; address_line: '114 Mt Dr';street_no: '114';street_suffix: 'Dr';city: 'Mount Pocono';state: 'PA';zip: '18344';unit: 'Apt 2A'}]",
            "3701 CRESCENT RIM DR APT;BOISE;ID;83706 |99| [{ geo_type: ADDRESS; address_line: '3701 Crescent Rim Dr';street_no: '3701';street_suffix: 'Dr';city: 'Boise';state: 'ID';zip: '83706';unit: 'Apt'}]",
            "1200 PA ave apt 1;Bridgeville;PA;15017 |99| [{ geo_type: ADDRESS; address_line: '1200 Pa Ave';street_no: '1200';street_suffix: 'Ave';city: 'Bridgeville';state: 'PA';zip: '15017';unit: 'Apt 1'}]",
            "218 W. Austin Apt 1-4;Nacogdoches;TX;75961 |99| [{ geo_type: ADDRESS; address_line: '218 W Austin Apt(s) 1-4';street_no: '218';street_direction: 'W';street: 'Austin';city: 'Nacogdoches';state: 'TX'; zip_plus_four: '2768'; unit: '# 1-4'}]",
            "826 Apt 1-4 Magnolia Ave;Long Beach;CA;90813 |99| [{ geo_type: ADDRESS; address_line: '826 Magnolia Ave';street_no: '826';street_suffix: 'Ave';city: 'Long Beach';state: 'CA';zip: '90813';unit: 'Apt 1-4'}]",
            "260-290 West Washington Apt 1-10;Martinsville;IN;46151 |99| [{ geo_type: ADDRESS; address_line: '260-290 West Washington';city: 'Martinsville';state: 'IN';zip: '46151';unit: 'Apt 1-10'}]",
            "39.5 Washington Square S - Apt: 2;New York;NY;10012 |99| [{ geo_type: ADDRESS; address_line: '39.5 Washington Sq S';street_no: '39.5';street_post_direction: 'S';city: 'New York';state: 'NY';zip: '10012';unit: 'Apt 2'}]",
            "6035 Broadway - Apt: 4C;Bronx;NY;10471 |99| [{ geo_type: ADDRESS; address_line: '6035 Broadway';street_no: '6035';street_direction: '';street_post_direction: '';city: 'Bronx';state: 'NY';zip_plus_four: '4105';unit: 'Apt 4C'}]",
            "7 Prospect St Apt403;Morristown Town;NJ;07960 |99| [{ geo_type: ADDRESS; address_line: '7 Prospect St';street_no: '7';street_direction: '';street_post_direction: '';city: 'Morristown';state: 'NJ';zip_plus_four: '9302';unit: 'Unit 403'}]",
            "209 Apt9 WATERDOWN DR.;Fayetteville;NC;28314 |99| [{ geo_type: ADDRESS; address_line: '209 Waterdown Dr';street_no: '209';street_suffix: 'Dr';city: 'Fayetteville';state: 'NC';zip: '28314';unit: 'Apt 9'}]",
            "190 Olympic Apt17;Port Ludlow;WA;98365 |99| [{ geo_type: ADDRESS; address_line: '190 Olympic';street_no: '190';street: 'Olympic';city: 'Port Ludlow';state: 'WA';zip: '98365';unit: 'Apt 17'}]",
            "Apt211 Harbor Island Inn & Villas;Harbor Island;SC;29920 |99| [{ geo_type: ADDRESS; address_line: 'Harbor Is Inn and Villas';street_suffix: 'Is';city: 'Harbor Island';state: 'SC';zip: '29920';unit: 'Apt 211'}]",
            "23238APT#1037 ROSEWOOD CT #1037;CALIFORNIA;MD;20619 |99| [{ geo_type: ADDRESS; address_line: '23238 Rosewood Ct';street_no: '23238';street_suffix: 'Ct';city: 'California';state: 'MD';zip: '20619';unit: '# 1037'}]",
            "APT#1 1459 LEARN ROAD;Tannersville;PA;18372 |99| [{ geo_type: ADDRESS; address_line: '1459 Learn Rd';street_suffix: 'Rd';city: 'Tannersville';state: 'PA';zip: '18372';unit: 'Apt 1'}]",
            "24733 Apt#6 Ridgewalk 66;Murrieta;CA;92562 |99| [{ geo_type: ADDRESS; address_line: '24733 Ridgewalk 66';street_no: '24733';street: 'Ridgewalk 66';city: 'Murrieta';state: 'CA';zip: '92562';unit: 'Apt 6'}]",
            "4051 Nostrand Ave APT.1;Brooklyn;NY;11235 |99| [{ geo_type: ADDRESS; address_line: '4051 Nostrand Ave';street_no: '4051';street_direction: '';street_post_direction: '';city: 'Brooklyn';state: 'NY';zip_plus_four: '2240';unit: 'Apt 1'}]",
            "441 apt.2 WILTON-GANSEVOORT RD;Wilton;NY;12831 |99| [{ geo_type: ADDRESS; address_line: '441 Wilton-Gansevoort Rd';street_no: '441';street_suffix: 'Rd';city: 'Wilton';state: 'NY';zip: '12831';unit: 'Apt 2'}]",
            "514 Brophy;  Apt.1;Bisbee;AZ;85603 |99| [{ geo_type: ADDRESS; address_line: '514 Brophy';street_no: '514';street: 'Brophy';city: 'Bisbee';state: 'AZ';zip: '85603';unit: 'Apt 1'}]",
            "A Liftside Dr APT.10;Hunter;NY;12442 |99| [{ geo_type: ADDRESS; address_line: 'A Liftside Dr';street_suffix: 'Dr';city: 'Hunter';state: 'NY';zip: '12442';unit: 'Apt 10'}]",
            "95th Avenue & 111th street..Spacious Studio Apt $1;South Richmond Hill;NY;11419 |99| [{ geo_type: ADDRESS; address_line: '95th Avenue and 111th St Spacious Studio';street_no: '95th';street_suffix: 'St';city: 'South Richmond Hill';state: 'NY';zip: '11419';unit: 'Apt 1'}]",
            "Decatur Avenue & E. 197th street..1Br Apt $975;bronx;NY;10458 |99| [{ geo_type: ADDRESS; address_line: 'Decatur Avenue and E 197th St 1br';street_suffix: 'St';city: 'Bronx';state: 'NY';zip: '10458';unit: 'Apt 975'}]",
            "34 Bay Ridge Ave APT.1B;Brooklyn;NY;11209 |99| [{ geo_type: ADDRESS; address_line: '34 Bay Ridge Ave';street_no: '34';street_suffix: 'Ave';city: 'Brooklyn';state: 'NY';zip: '11209';unit: 'Apt 1B'}]",
            "52-54 Avenue W APT.1A;Brooklyn;NY;11223 |99| [{ geo_type: ADDRESS; address_line: '52-54 Ave W';street_post_direction: 'W';city: 'Brooklyn';state: 'NY';zip: '11223';unit: 'Apt 1A'}]",
            "105 Prime Apt.D3 Ave D3;Huntington;NY;11743 |99| [{ geo_type: ADDRESS; address_line: '105 Prime Ave';street_no: '105';street_direction: '';street_post_direction: '';city: 'Huntington';state: 'NY';zip_plus_four: '2006';unit: 'Apt D3'}]",
            "36 Apt -2b Lake St;Highland Falls;NY;10928 |99| [{ geo_type: ADDRESS; address_line: '36 Lake St';street_no: '36';street_suffix: 'St';city: 'Highland Falls';state: 'NY';zip: '10928';unit: 'Apt 2B'}]",
            "114 Mountain Dr. Apt 2-E;Mount Pocono;PA;18344 |99| [{ geo_type: ADDRESS; address_line: '114 Mountain Dr';street_no: '114';street_suffix: 'Dr';city: 'Mount Pocono';state: 'PA';zip: '18344';unit: 'Apt 2E'}]",
            "REO COND MIRAMAR 626 APT 2-A-;San Juan;PR;00907 |99| [{ geo_type: ADDRESS; address_line: 'REO COND MIRAMAR 626';city: 'San Juan';state: 'PR';zip: '00907';unit: 'Apt 2A'}]",
            "500 Paragon Mills Road Apt E-8 ;Nashville;TN;37211 |99| [{ geo_type: ADDRESS; address_line: '500 Paragon Mills Rd';street_no: '500';street_direction: '';street_post_direction: '';city: 'Nashville';state: 'TN';zip_plus_four: '3732';unit: 'Apt E8'}]",
            "319 Laurens Street; Apt A-7;Aiken;SC;29801 |99| [{ geo_type: ADDRESS; address_line: '319 Laurens St SW';street_no: '319';street_direction: '';street_post_direction: 'SW';city: 'Aiken';state: 'SC';zip_plus_four: '4851';unit: 'Apt A7'}]",
            "Apt A-501 Balcones De Monte Real;Carolina;PR;00617 |99| [{ geo_type: ADDRESS; address_line: 'Balcones De Monte Real';city: 'Carolina';state: 'PR';zip: '00617';unit: 'Apt A501'}]",

    })
    public void test_with_a_good_name(String input, int expectNumMatches, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input, dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);

        assertNotNull("Parser response is null\n", actual);
        assertTrue("Parser returned no results\n", 0 != actual.length);
        if (expectNumMatches != 99)
            assertEquals(expectNumMatches, actual.length);

        if (expectNumMatches == 1)
            assertTrue("Parser result doesn't match expected result", actual[0].equals(expected[0]));
        else {
            boolean matchFound = false;
            for (int i = 0; i < actual.length; i++)
                if (actual[i].equals(expected[0]))
                    matchFound = true;

            assertTrue("None of the parser's responses match the expected result\n", matchFound);

        }
    }
}

