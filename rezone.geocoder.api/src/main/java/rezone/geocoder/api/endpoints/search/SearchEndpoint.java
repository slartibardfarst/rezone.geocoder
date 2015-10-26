package rezone.geocoder.api.endpoints.search;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import org.bson.BsonDocument;
import org.bson.BsonString;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("search")
public class SearchEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String foo(@QueryParam(value="query") String query) throws IOException {
        BsonDocument doc =
                new BsonDocument()
                    .append("type", new BsonString("search"))
                    .append("message", new BsonString("hello world"))
                    .append("query", new BsonString(query));

        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(
                new HttpClientConfig
                        .Builder("http://es.geocoder.geo-dev.moveaws.com:80")
                        .multiThreaded(true)
                        .build());

        JestClient client = factory.getObject();

        client.execute(new CreateIndex.Builder("geocoder6").build());

        String esquery = "{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\":[\n" +
                "        {\n" +
                "          \"term\":{\n" +
                "            \"sc\":\"wv\"\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"match\": {\n" +
                "            \"city_full\": \"NW Fairview\"\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"match\": {\n" +
                "            \"street_full\": \"Hamilton St\"\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"should\":[\n" +
                "        {\n" +
                "          \"term\": {\n" +
                "            \"address_street_number_exact\": \"112\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";


        Search search = new Search.Builder(esquery)
                .build();

        SearchResult result = client.execute(search);

        return result.getJsonString();

    }
}
