package rezone.geocoder.api.endpoints.search.scenarios;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;
import rezone.geocoder.parser.Geo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryong on 10/26/2015.
 */
public class NeighborhoodCityCounty {
    public BsonDocument getQuery(Geo[] components) {

        final Geo component = components[0];

        List<BsonValue> regions = new ArrayList<BsonValue>() {{
            add(new BsonString(component.city));

        }};

        BsonArray musts = new BsonArray();
        musts.add(
                new BsonDocument(
                        "match_phrase",
                        new BsonDocument(
                                "regions",new BsonString(component.city ))));


        BsonDocument esQuery =
                new BsonDocument()
                        .append("query",
                                new BsonDocument()
                                        .append("bool",
                                                new BsonDocument()
                                                        .append("must",musts)));


        return esQuery;
    }

}
