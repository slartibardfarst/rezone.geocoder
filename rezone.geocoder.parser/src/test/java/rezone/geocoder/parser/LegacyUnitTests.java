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
            "326 Ste8 BALLSTON AV;Saratoga Springs;NY;12866 |99| [{ geo_type: ADDRESS;street_no: '326'; street: 'Ballston'; street_suffix: 'Ave';unit: 'Ste 8';city: 'Saratoga Springs';state: 'NY';zip: '12866'}]",
            "1005 Ste #5 N 9th;Boise;ID;83702 |99| [{ geo_type: ADDRESS;street_no: '1005';street_direction: 'N';street: '9th';unit: 'Ste #5';city: 'Boise';state: 'ID';zip: '83702'}]",
            "30900-bk East 160 Ste & Y12;Llano;CA;93544 |99| [{ geo_type: ADDRESS;unit: 'Ste Y12';city: 'Llano';state: 'CA';zip: '93544'}]",
            "111 S S Street STE 5;Dover;DE;19904 |99| [{ geo_type: ADDRESS;street_no: '111';street_direction: 'S'; street: 'S'; street_suffix: 'Street';unit: 'Ste 5';city: 'Dover';state: 'DE';zip: '19904'}]",
            "49 ste 106 East Brimfield Holland;Brimfield;MA;01010 |99| [{ geo_type: ADDRESS;street_no: '49'; street_direction: 'East' ; street: 'Brimfield Holland';unit: 'Ste 106';city: 'Brimfield';state: 'MA';zip: '01010'}]",
            "N Cataract Creek Estates Ph 1-4 --;Williams;AZ;86046 |99| [{ geo_type: ADDRESS;street_direction: 'N';street: 'Cataract Creek'; street_suffix: 'Ests';unit: 'Ph 1-4';city: 'Williams';state: 'AZ';zip: '86046'}]",
            "727 W 7th Street PH 1522;Los Angeles (City);CA;90017 |99| [{ geo_type: ADDRESS;street_no: '727';street_direction: 'W';street: '7th';street_post_direction: ''; street_suffix: 'Street'; unit: '# 1522';city: 'Los Angeles';state: 'CA';zip: '90017'}]",
            "35109 Highway 79 Spc #145;Warner Springs;CA;92086 |99| [{ geo_type: ADDRESS;street_no: '35109';street_direction: '';street: 'Highway 79';street_post_direction: '';unit: 'Spc #145';city: 'Warner Springs';state: 'CA';zip: '92086'}]",
            "3157 East I  SPC B-11 Ave;Lancaster;CA;93535 |99| [{ geo_type: ADDRESS;street_no: '3157';street_direction: 'E';street_suffix: 'Ave';unit: 'Spc B11';city: 'Lancaster';state: 'CA';zip: '93535'}]",
            "212 S MC CLOUD AVE.; SPC 24;McCloud;CA;96057 |99| [{ geo_type: ADDRESS;street_no: '212';street_direction: 'S';street: 'MC CLOUD'; street_suffix: 'Ave';unit: 'Spc 24';city: 'McCloud';state: 'CA';zip: '96057'}]",
            "469010 Hwy Trlr 4;Sagle;ID;83864 |99| [{ geo_type: ADDRESS;street_no: '469010';street: 'Hwy'; street_suffix: 'Trlr';unit: '4';city: 'Sagle';state: 'ID';zip: '83864'}]",
            "8 Cannery Village Marina Slip #8 8;San Juan Island;WA;98250 |99| [{ geo_type: ADDRESS;street_no: '8';street_suffix: 'Vlg';unit: null;city: 'San Juan Island';state: 'WA';zip: '98250'}]",
            "9 Cannery Village Marina Slip #8 8;San Juan Island;WA;98250 |99| [{ geo_type: ADDRESS;street_no: '9';street_suffix: 'Vlg';unit: 'Slip 8';city: 'San Juan Island';state: 'WA';zip: '98250'}]",
            "333 Lake Ave. Slip # 21;City of Racine;WI;53403 |99| [{ geo_type: ADDRESS;street_no: '333';street_direction: ''; street: 'Lake'; street_suffix: 'Ave'; street_post_direction: '';unit: '# 21';city: 'Racine';state: 'WI';zip_plus_four: '1068'}]",
            "Boat Slip C-92;Portland;OR;97202 |99| [{ geo_type: ADDRESS;unit: 'Slip C92';city: 'Portland';state: 'OR';zip: '97202'}]",
            "15 E Spring #12 &Boat slip #10;Port Austin;MI;48467 |99| [{ geo_type: ADDRESS;street_no: '15';street_direction: '';street_suffix: 'Spg';unit: '# 10';city: 'Port Austin';state: 'MI';zip: '48467'}]",
            "12505 Oaks North Unit: #: 243;San Diego;CA;92128 |99| [{ geo_type: ADDRESS;street_no: '12505';street: 'Oaks North';unit: 'Unit 243';city: 'San Diego';state: 'CA';zip: '92128'}]",
            "40751 NORTH SHORE #76;FAWNSKIN;CA;92333 |99| [{ geo_type: ADDRESS;street_no: '40751';street_direction: 'NORTH'; street: 'SHORE';unit: '# 76';city: 'Fawnskin';state: 'CA';zip: '92333'}]",
            "831-53 N 19TH ST ##1;PHILADELPHIA;PA;19130 |99| [{ geo_type: ADDRESS;street_no: '831-53';street_direction: 'N';street: '19th'; street_suffix: 'St';unit: '# 1';city: 'Philadelphia';state: 'PA';zip: '19130'}]",
            "763-65 S 8TH ST ##3F;PHILADELPHIA;PA;19147 |99| [{ geo_type: ADDRESS;street_no: '763-65';street_direction: 'S';street: '8th'; street_suffix: 'St';unit: '# 3F';city: 'Philadelphia';state: 'PA';zip: '19147'}]",
            "4500 FOUR MILE RUN DR ##P302;ARLINGTON;VA;22204 |99| [{ geo_type: ADDRESS;street_no: '4500';street: 'four mile run'; street_suffix: 'Dr';unit: 'Apt 302';city: 'Arlington';state: 'VA';zip: '22204'}]",
            "69-4 Urb. Cataluna Calle # -4;Barceloneta;PR;00617 |99| [{ geo_type: ADDRESS;unit: '# 4';city: 'Barceloneta';state: 'PR';zip: '00617'}]",
            "277 Main St # 6-B;Aurora;NY;14052 |99| [{ geo_type: ADDRESS;street_no: '277';street_direction: ''; street: 'Main'; street_suffix: 'St'; street_post_direction: '';unit: '# 6-B';city: 'Aurora';state: 'NY';zip: '14052'}]",
            "555 Vasquez Rd # C-1;Winter Park;CO;80482 |99| [{ geo_type: ADDRESS;street_no: '555'; street: 'Vasquez';street_suffix: 'Rd';unit: '# C-1';city: 'Winter Park';state: 'CO';zip: '80482'}]",
            "2300 Cienaga St Rt # - 1 68;Oceano;CA;93445 |99| [{ geo_type: ADDRESS;street_no: '2300';street_direction: '';street_post_direction: '';unit: 'Spc 1';city: 'Oceano';state: 'CA';zip_plus_four: '8944'}]",
            "89A #216 Westwood Chateau;Marion;NC;28752 |99| [{ geo_type: ADDRESS;street: 'Westwood Chateau'; street_suffix: 'Dr';unit: '# 216';city: 'Marion';state: 'NC';zip_plus_four: '6544'}]",
            "9999 Goa Wy Unit Lot 1;Port Angeles;WA;98362 |99| [{ geo_type: ADDRESS;street_no: '9999';street: 'Goa'; street_suffix: 'Way';unit: 'Lot 1';city: 'Port Angeles';state: 'WA';zip: '98362'}]",
            "77 Wheatland Unit 1 Unit: 1;Somerville;MA;02145 |99| [{ geo_type: ADDRESS;street_no: '77';street_direction: ''; street: 'Wheatland'; street_post_direction: '';unit: 'Unit 1';city: 'Somerville';state: 'MA';zip_plus_four: '2015'}]",
            "12244 Short Unit 3 #3;Filburns Island;OH;45365 |99| [{ geo_type: ADDRESS;street_no: '12244';street: 'Short';unit: 'Unit 3';city: 'Filburns Island';state: 'OH';zip: '45365'}]",
            "124 N 3820 W 21; Hurricane; UT 84737 |99| [{ geo_type: ADDRESS;street_no: '124';street_direction: 'N'; street: '3820';street_post_direction: 'W';unit: '21';city: 'Hurricane';state: 'UT';zip:'84737'}]",
            "49TH AVE DR E; Bradenton; FL |99| [{ geo_type: ADDRESS;street: '49th Ave'; street_suffix: 'Dr'; street_post_direction: 'E';city: 'Bradenton';state: 'FL'}]",
            "98TH Av Ct; Gig Harbor; WA 98329 |99| [{ geo_type: ADDRESS;street: '9th Ave'; street_suffix: 'Ct';city: 'Gig Harbor';state: 'WA';zip: '98329'}]",
            "Summit Unit: 3;4;Descanso;CA;91916 |99| [{ geo_type: ADDRESS;street_no: null; street_direction: null; street_suffix: null; unit: 'Units 3 & 4';city: 'Descano';state: 'CA';zip: '91916'}]",
            "0 Rolling Hills Way;Sequim;WA 98382 |99| [{ geo_type: ADDRESS;street_no: '0'; street: 'Rolling Hills'; street_suffix: 'Way';city: 'Sequim';state: 'WA';zip: '98382'}]",
            "xxx Shoreline Drive;Panacea;FL;32346 |99| [{ geo_type: STREET;street: 'xxx Shoreline'; street_suffix: 'Drive';city: 'Panacea';state: 'FL';zip: '32346'}]",
            "Xxxx Highland Meadows Dr;Williams; AZ |99| [{ geo_type: STREET;street: 'Xxxx Highland Meadows'; street_suffix: 'Dr';city: 'Williams';state: 'AZ'}]",
            "5 Lot s Enderbury; Rome; GA; 30161 |99| [{ geo_type: ADDRESS; street_no: 5; street: 'Enderbury'; street_suffix: null; unit: 'Lot s'; city: 'Rome'; state: 'GA'; zip: '30161' }]",
            "2828 N HIGHWAY 89 (C);Fish Haven;ID;83287 |99| [{ geo_type: ADDRESS;street_no: '2828'; street_direction: 'N';street: 'Highway 89'; unit: 'C';city: 'Fish Haven';state: 'ID';zip: '83287'}]",
            "1/2 307 S NACHES AVE; Yakima; WA |99| [{ geo_type: ADDRESS;street_no: '307'; street_direction: 'S'; street: 'Naches'; street_suffix: 'Ave'; unit: '# 1 & 2';city: 'Yakima';state: 'WA';zip: null}]",
            "1/2/3 307 S NACHES AVE; Yakima; WA |99| [{ geo_type: ADDRESS;street_no: '307'; street_direction: 'S'; street: 'Naches'; street_suffix: 'Ave'; unit: '# 1 & 2 & 3';city: 'Yakima';state: 'WA';zip: null}]",
            "1/2 NACHES AVE; Yakima; WA |99| [{ geo_type: ADDRESS;street_no: '1/2'; street: 'Naches'; street_suffix: 'Ave'; unit: null;city: 'Yakima';state: 'WA';zip: null}]",

            "4140 Three Oaks Blvd; Apt 4a;TROY;MI;48098 |99| [{ geo_type: ADDRESS;street_no: '4140';street: 'Three Oaks'; street_suffix: 'Blvd';unit: 'Apt 4A';city: 'Troy';state: 'MI';zip: '48098'}]",
            "114 Mt. Drive Apt 2A;Mount Pocono;PA;18344 |99| [{ geo_type: ADDRESS;street_no: '114'; street: 'Mt.'; street_suffix: 'Dr';unit: 'Apt 2A';city: 'Mount Pocono';state: 'PA';zip: '18344'}]",
            "3701 CRESCENT RIM DR APT;BOISE;ID;83706 |99| [{ geo_type: ADDRESS;street_no: '3701'; street: 'Crescent Rim'; street_suffix: 'Dr';unit: 'Apt';city: 'Boise';state: 'ID';zip: '83706'}]",
            "1200 PA ave apt 1;Bridgeville;PA;15017 |99| [{ geo_type: ADDRESS;street_no: '1200';street: 'PA'; street_suffix: 'Ave';unit: 'Apt 1';city: 'Bridgeville';state: 'PA';zip: '15017'}]",
            "218 W. Austin Apt 1-4;Nacogdoches;TX;75961 |99| [{ geo_type: ADDRESS;street_no: '218';street_direction: 'W';street: 'Austin'; unit: '# 1-4';city: 'Nacogdoches';state: 'TX'; zip_plus_four: '2768'}]",
            "826 Apt 1-4 Magnolia Ave;Long Beach;CA;90813 |99| [{ geo_type: ADDRESS;street_no: '826'; street: 'Magnolia'; street_suffix: 'Ave';unit: 'Apt 1-4';city: 'Long Beach';state: 'CA';zip: '90813'}]",
            "260-290 West Washington Apt 1-10;Martinsville;IN;46151 |99| [{ geo_type: ADDRESS;street_no: '260-290'; street_direction: 'West'; street: 'Washington'; unit: 'Apt 1-10';city: 'Martinsville';state: 'IN';zip: '46151'}]",
            "39.5 Washington Square S - Apt: 2;New York;NY;10012 |99| [{ geo_type: ADDRESS;street_no: '39.5';street_post_direction: 'S';unit: 'Apt 2';city: 'New York';state: 'NY';zip: '10012'}]",
            "6035 Broadway - Apt: 4C;Bronx;NY;10471 |99| [{ geo_type: ADDRESS;street_no: '6035';street_direction: '';street: 'Broadway'; street_post_direction: '';unit: 'Apt 4C';city: 'Bronx';state: 'NY';zip: '10471'}]",
            "7 Prospect St Apt403;Morristown Town;NJ;07960 |99| [{ geo_type: ADDRESS;street_no: '7';street_direction: '';street: 'Prospect'; street_suffix: 'St'; street_post_direction: '';unit: 'Apt403';city: 'Morristown Town';state: 'NJ';zip: '07960'}]",
            "209 Apt9 WATERDOWN DR.;Fayetteville;NC;28314 |99| [{ geo_type: ADDRESS;street_no: '209'; street: 'Waterdown'; street_suffix: 'Dr';unit: 'Apt 9';city: 'Fayetteville';state: 'NC';zip: '28314'}]",
            "190 Olympic Apt17;Port Ludlow;WA;98365 |99| [{ geo_type: ADDRESS;street_no: '190';street: 'Olympic';unit: 'Apt 17';city: 'Port Ludlow';state: 'WA';zip: '98365'}]",
            "Apt211 Harbor Island Inn & Villas;Harbor Island;SC;29920 |99| [{ geo_type: ADDRESS;street_suffix: 'Is';unit: 'Apt 211';city: 'Harbor Island';state: 'SC';zip: '29920'}]",
            "23238APT#1037 ROSEWOOD CT #1037;CALIFORNIA;MD;20619 |99| [{ geo_type: ADDRESS;street_no: '23238';street: 'Rosewood'; street_suffix: 'Ct';unit: '# 1037';city: 'California';state: 'MD';zip: '20619'}]",
            "APT#1 1459 LEARN ROAD;Tannersville;PA;18372 |99| [{ geo_type: ADDRESS;street_suffix: 'Rd';unit: 'Apt 1';city: 'Tannersville';state: 'PA';zip: '18372'}]",
            "24733 Apt#6 Ridgewalk 66;Murrieta;CA;92562 |99| [{ geo_type: ADDRESS;street_no: '24733';street: 'Ridgewalk 66';unit: 'Apt 6';city: 'Murrieta';state: 'CA';zip: '92562'}]",
            "4051 Nostrand Ave APT.1;Brooklyn;NY;11235 |99| [{ geo_type: ADDRESS;street_no: '4051';street_direction: '';street: 'Nostrand'; street_suffix: 'Ave'; street_post_direction: '';unit: 'Apt 1';city: 'Brooklyn';state: 'NY';zip_plus_four: '2240'}]",
            "441 apt.2 WILTON-GANSEVOORT RD;Wilton;NY;12831 |99| [{ geo_type: ADDRESS;street_no: '441';street: 'Wilton-Gansevoort'; street_suffix: 'Rd';unit: 'Apt 2';city: 'Wilton';state: 'NY';zip: '12831'}]",
            "514 Brophy;  Apt.1;Bisbee;AZ;85603 |99| [{ geo_type: ADDRESS;street_no: '514';street: 'Brophy';unit: 'Apt 1';city: 'Bisbee';state: 'AZ';zip: '85603'}]",
            "A Liftside Dr APT.10;Hunter;NY;12442 |99| [{ geo_type: ADDRESS; street: 'A Liftside'; street_suffix: 'Dr';unit: 'Apt 10';city: 'Hunter';state: 'NY';zip: '12442'}]",
            "95th Avenue & 111th street..Spacious Studio Apt $1;South Richmond Hill;NY;11419 |99| [{ geo_type: ADDRESS;street_no: '95th';street_suffix: 'St';unit: 'Apt 1';city: 'South Richmond Hill';state: 'NY';zip: '11419'}]",
            "Decatur Avenue & E. 197th street..1Br Apt $975;bronx;NY;10458 |99| [{ geo_type: ADDRESS;street_suffix: 'St';unit: 'Apt 975';city: 'Bronx';state: 'NY';zip: '10458'}]",
            "34 Bay Ridge Ave APT.1B;Brooklyn;NY;11209 |99| [{ geo_type: ADDRESS;street_no: '34';street: 'Bay Ride'; street_suffix: 'Ave';unit: 'Apt 1B';city: 'Brooklyn';state: 'NY';zip: '11209'}]",
            "52-54 Avenue W APT.1A;Brooklyn;NY;11223 |99| [{ geo_type: ADDRESS;street_post_direction: 'W';unit: 'Apt 1A';city: 'Brooklyn';state: 'NY';zip: '11223'}]",
            "105 Prime Apt.D3 Ave D3;Huntington;NY;11743 |99| [{ geo_type: ADDRESS;street_no: '105';street_direction: '';street_post_direction: '';unit: 'Apt D3';city: 'Huntington';state: 'NY';zip_plus_four: '2006'}]",
            "36 Apt -2b Lake St;Highland Falls;NY;10928 |99| [{ geo_type: ADDRESS;street_no: '36';street_suffix: 'St';unit: 'Apt 2B';city: 'Highland Falls';state: 'NY';zip: '10928'}]",
            "114 Mountain Dr. Apt 2-E;Mount Pocono;PA;18344 |99| [{ geo_type: ADDRESS;street_no: '114'; street: 'Mountain'; street_suffix: 'Dr';unit: 'Apt 2E';city: 'Mount Pocono';state: 'PA';zip: '18344'}]",
            "REO COND MIRAMAR 626 APT 2-A-;San Juan;PR;00907 |99| [{ geo_type: ADDRESS;unit: 'Apt 2A';city: 'San Juan';state: 'PR';zip: '00907'}]",
            "500 Paragon Mills Road Apt E-8 ;Nashville;TN;37211 |99| [{ geo_type: ADDRESS;street_no: '500'; street_direction: ''; street: 'Paragon Mills'; street_suffix: 'Road'; street_post_direction: '';unit: 'Apt E8';city: 'Nashville';state: 'TN';zip_plus_four: '3732'}]",
            "319 Laurens Street; Apt A-7;Aiken;SC;29801 |99| [{ geo_type: ADDRESS;street_no: '319';street_direction: ''; street: 'Laurens'; street_suffix: 'Street'; street_post_direction: 'SW';unit: 'Apt A7';city: 'Aiken';state: 'SC';zip_plus_four: '4851'}]",
            "Apt A-501 Balcones De Monte Real;Carolina;PR;00617 |99| [{ geo_type: ADDRESS;unit: 'Apt A501';city: 'Carolina';state: 'PR';zip: '00617'}]",


    })
    public void legacy_cases1(String input, int expectNumMatches, String expectedAsJson) throws Exception {
        input = input.replaceAll(";|\\.", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input , dbg);
        Geo[] expected = _gson.fromJson(expectedAsJson, Geo[].class);


        assertNotNull("Parser response is null\n", actual);
        assertTrue("Parser returned no results\n", 0 != actual.length);
        if (expectNumMatches != 99)
            assertEquals(expectNumMatches, actual.length);

        if (expectNumMatches == 1)
            assertTrue("Parser result doesn't match expected result\n", actual[0].equals(expected[0]));
        else {
            boolean matchFound = false;
            for (int i = 0; i < actual.length; i++)
                if (actual[i].equals(expected[0]))
                    matchFound = true;

                assertTrue("None of the parser's responses match the expected result\n", matchFound);
            }

    }

    @Test
    @Parameters({
            "1005 Ste #5 N 9th;Boise;ID;83702 |99| [{ geo_type: ADDRESS;street_no: '1005';street_direction: 'N'; street: '9th';unit: 'Ste #5';city: 'Boise'; state: 'ID';zip: '83702'}]",
            "218 W. Austin Apt 1-4;Nacogdoches;TX;75961 |99| [{ geo_type: ADDRESS;street_no: '218';street_direction: 'W';street: 'Austin';unit: 'Apt 1-4';city: 'Nacogdoches';state: 'TX';zip: '75961'}]",
            "500 Paragon Mills Road Apt E-8 ;Nashville;TN;37211 |99| [{ geo_type: ADDRESS;street_no: '500'; street: 'Paragon Mills'; street_suffix: 'Road';unit: 'Apt E-8';city: 'Nashville';state: 'TN';zip: '37211'}]",
            "5104 S E 30th Avenue;PORTLAND;OR;97202 |99| [{ geo_type: ADDRESS; street_no: '5104'; street_direction: 'S E'; street: '30th'; street_suffix: 'Avenue' ; city: 'Portland'; state: 'OR'; zip: '97202'}]",
            "3500 N Hayden -- Unit: 1903;Scottsdale;AZ;85251 |99| [{ geo_type: ADDRESS; street_no: '3500'; street_direction: 'N'; street: 'Hayden'; street_suffix: null; unit:'Unit: 1903' ; city: 'Scottsdale'; state: 'AZ'; zip: '85251'}]",
            "1915 Broadway St Unit ALEXANDER;San Antonio;TX;78215 |99| [{ geo_type: ADDRESS; street_no: '1915'; street: 'Broadway'; street_suffix: 'St'; unit:'Unit Alexander' ; city: 'San Antonio'; state: 'TX'; zip: '78215'}]",
            //"LUXURY BUILDING APARTMENTS;New York;NY;10016 |99| [{ geo_type: ADDRESS; street_no: null; street_suffix: null; unit:null ; city: 'New York'; state: 'NY'; zip: '10016'}]",
            "217 Town And Country Dr;Danville;CA;94526 |99| [{ geo_type: ADDRESS; street_no: '217'; street: 'Town And Country'; street_suffix: 'Dr'; unit:null ; city: 'Danville'; state: 'CA'; zip: '94526'}]",
            "277 Main St # 6-B;Aurora;NY;14052 |99| [{ geo_type: ADDRESS; street_no: '277'; street: 'Main'; street_suffix: 'St'; unit:'# 6-B' ; city: 'Aurora'; state: 'NY'; zip: '14052'}]",
            "886 Wellman Avenue Unit: 886;Chelmsford;MA;01863 |99| [{ geo_type: ADDRESS; street_no: '886'; street: 'Wellman'; street_suffix: 'Avenue'; unit:'Unit: 886' ; city: 'Chelmsford'; state: 'MA'; zip: '01863'}]",
            "43 Blackmount Lane Unit: 43;Fairfield;CT;06825 |99| [{ geo_type: ADDRESS; street_no: '43'; street: 'Blackmount'; street_suffix: 'Lane'; unit:'Unit: 43' ; city: 'Fairfield'; state: 'CT'; zip: '06825'}]",
            "234 North West Smith Street;PORTLAND;OR;97202 |99| [{ geo_type: ADDRESS; street_no: '234'; street_direction: 'North'; street: 'West Smith'; street_suffix: 'Street'; unit:null ; city: 'Portland'; state: 'OR'; zip: '97202'}]",
            "6829 CINNABAR COAST LANE;North las vegas;NV;89084-1202 |99| [{ geo_type: ADDRESS; street_no: '6829'; street: 'CINNABAR COAST'; street_suffix: 'Lane'; unit:null ; city: 'North Las Vegas'; state: 'NV'; zip: '89084-1202'}]",
            "1345 Commonwealth Ave.;Boston;MA;02134 |99| [{ geo_type: ADDRESS; street_no: '1345'; street: 'Commonwealth';street_suffix: 'Ave' ; city: 'Boston'; state: 'MA'; zip: '02134'}]",
            "2030 N Via Silverado Unit: 3;Camp Verde;AZ;86322 |99| [{ geo_type: ADDRESS; street_no: '2030'; street_direction: 'N'; street: 'Via Silverado'; unit: 'Unit: 3'; city: 'Camp Verde'; state: 'AZ'; zip: '86322'}]",
            "0 BARTHOLOMEW ST;Christmas;FL;32709 |99| [{ geo_type: ADDRESS; street_no: '0'; street: 'Bartholomew'; street_suffix: 'St'; city: 'Christmas'; state: 'FL'; zip: '32709'}]",
            "3214 S West Street;Covington;GA;30014 |99| [{ geo_type: ADDRESS; street_no: '3214'; street_direction: 'S'; street: 'West';street_suffix: 'Street'; city: 'Covington'; state: 'GA'; zip: '30014' }]",
            "133 S JACKSON St -A-7;Denver;CO;80209 |99| [{ geo_type: ADDRESS; street_no: '133'; street_direction: 'S'; street_suffix: 'St'; unit: '# A7' ; city: 'Denver'; state: 'CO'; zip: '80209'}]",
            "24055 NE North Valley Rd;Newberg;OR;97132 |99| [{ geo_type: ADDRESS; street_no: '24055'; street_direction: 'NE'; street: 'North Valley'; street_suffix: 'Rd'; city: 'Newberg'; state: 'OR'; zip: '97132' }]",
            "13341 Sweet Flag - Gh315;Black Butte Ranch;OR;97759 |99| [{ geo_type: ADDRESS; street_no: '13341'; street: 'Sweet Flag'; unit: 'Gh315' ; city: 'Black Butte Ranch'; state: 'OR'; zip: '97759'}]",
            "xxx Shoreline Drive;Panacea;FL;32346 |99| [{ geo_type: STREET; street: 'xxx Shoreline'; street_suffix: 'Drive'; city: 'Panacea'; state: 'FL'; zip: '32346' }]",
            "XXX SUMMER OAKS BLVD;Bismarck;AR |99| [{ geo_type: STREET; street: 'XXX SUMMER OAKS';street_suffix: 'Blvd'; city: 'Bismarck'; state: 'AR' }]",
            "618 Meadows; Dallas; PA; 18612 |99| [{ geo_type: ADDRESS; street_no: '618'; street: 'Meadows'; street_suffix: null; city: 'Dallas'; state: 'PA'; zip: '18612' }]",
            "5 Lot s Enderbury; Rome; GA; 30161 |99| [{ geo_type: ADDRESS; street_no: 5; street: 'Enderbury'; street_suffix: null; unit: 'Lot s'; city: 'Rome'; state: 'GA'; zip: '30161' }]",
            "Off Hwy 80 Lot 1-6;Burnsville;NC;28714 |99| [{ geo_type: ADDRESS; street_no: null; street:'Off Hwy 80'; street_suffix: null; unit: 'Lot 1-6' ; city: 'Burnsville'; state: 'NC'; zip: '28714'}]",
            "Off Hwy 80 Lot 1thru6;Burnsville;NC;28714 |99| [{ geo_type: ADDRESS; street_no: null; street:'Off Hwy 80';street_suffix: null; unit: 'Lot 1-6' ; city: 'Burnsville'; state: 'NC'; zip: '28714'}]",
            "Lot 1 Entrada;Pecos;NM;87552 |99| [{ geo_type: ADDRESS; street_no: null; street: 'Entrada';street_suffix: null;unit: 'Lot 1' ; city: 'Pecos'; state: 'NM'; zip: '87552'}]",
            "89A #216 Westwood Chateau;Marion;NC;28752 |99| [{ geo_type: ADDRESS; street_no: null; street: 'Westwood Chateau'; street_suffix: null;unit: '# 216' ; city: 'Marion'; state: 'NC'; zip: '28752'}]",
            "433 S Murphy Ave; San Jose; QQ; 00015 |99| [{ geo_type: ADDRESS; street_no: '433'; street_direction: 'S'; street: 'Murphy'; street_suffix: 'Ave'; unit: null ; city: 'San Jose'; state: null; zip: '00015'}]",
            // "2604 ROAD 123;Meeker;CO |99| [{ geo_type: ADDRESS; street_no: '2604'; street_direction: null; street_suffix: null; unit: null ; city: 'Meeker'; state: 'CO'; zip: null}]",
            "1819 Fruitvale Blvd Unit: Lt A;Yakima;WA;98902 |99| [{ geo_type: ADDRESS;street_no: '1819';street: 'Fruitvale'; street_suffix: 'Blvd';unit: 'Lot A';city: 'Yakima';state: 'WA';zip: '98902'}]",
            "2559 12th Avenue North West;Columbia Falls;MT;59912 |99| [{ geo_type: ADDRESS; street_no: '2559'; street_direction: null; street: '12th'; street_suffix: 'Avenue'; street_post_direction: 'North West'; unit: null ; city: 'Columbia Falls'; state: 'MT'; zip: '59912'}]",
    })
    public void legacy_cases2(String input, int expectNumMatches, String expectedAsJson) throws Exception {
        input = input.replaceAll(";", ",");
        expectedAsJson = expectedAsJson.replaceAll(";|\\.", ",");

        ParseDebug dbg = new ParseDebug();
        Geo[] actual = _parser.parse(input , dbg);
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

