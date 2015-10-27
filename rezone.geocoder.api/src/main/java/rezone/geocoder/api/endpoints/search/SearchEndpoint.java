package rezone.geocoder.api.endpoints.search;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import org.bson.BsonDocument;
import org.bson.BsonString;
import rezone.geocoder.api.endpoints.search.scenarios.NeighborhoodCityCounty;
import rezone.geocoder.parser.Geo;
import rezone.geocoder.parser.QueryParser;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

import com.wordnik.swagger.annotations.*;

@Path("search")
@Api(value = "/search", description = "Parses an Address for you")
public class SearchEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Parses an Address for you",
            notes = "returns an array of found addresses"
    )
    public String foo(@QueryParam(value="query") String query) throws IOException {

        // todo: dependency injection
        QueryParser queryParser = new QueryParser();
        NeighborhoodCityCounty neighborhoodCityCounty = new NeighborhoodCityCounty();

        Geo[] geos = queryParser.parse(query);


        BsonDocument esQuery = neighborhoodCityCounty.getQuery(geos);


        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(
                new HttpClientConfig
                        .Builder("http://es.geocoder.geo-dev.moveaws.com:80")
                        .multiThreaded(true)
                        .build());

        JestClient client = factory.getObject();

        String esquery = esQuery.toJson();


        Search search = new Search.Builder(esquery)
                .build();

        SearchResult result = client.execute(search);

        return result.getJsonString();

    }
}
