package rezone.geocoder.parser.api;

import com.google.gson.Gson;
import rezone.geocoder.parser.Geo;
import rezone.geocoder.parser.ParseDebug;
import rezone.geocoder.parser.QueryParser;
import spark.QueryParamsMap;

import static spark.Spark.*;


public class HelloWorld {
    public static void main(String[] args) {

        Gson gson = new Gson();
        QueryParser qp = new QueryParser();

        get("/hello", "application/json", (req, res) ->
        {
            QueryParamsMap queryMap = req.queryMap();
            String address = queryMap.value("address");
            ParseDebug dbg = new ParseDebug();
            Geo[] result = qp.parse(address, dbg);

            return result;
        }, new JsonTransformer());


    }
}