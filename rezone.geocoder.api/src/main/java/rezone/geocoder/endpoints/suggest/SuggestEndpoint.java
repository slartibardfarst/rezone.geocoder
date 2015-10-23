package rezone.geocoder.endpoints.suggest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("suggest")
public class SuggestEndpoint
{

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String foo()
    {
        return "{\n" +
                "  \"type\": \"suggest\",\n" +
                "  \"message\":\"hello world\"\n" +
                "}";

    }
}
