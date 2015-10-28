package rezone.geocoder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;

//import com.wordnik.swagger.jaxrs.config.*;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        String[] packages = {"rezone.geocoder", "com.wordnik.swagger.jersey.listing"};

        // create a resource config that scans for JAX-RS resources and providers
        // in rezone.geocoder package
        final ResourceConfig rc = new ResourceConfig().packages(packages);


//        BeanConfig config = new BeanConfig();
//        config.setResourcePackage("rezone.geocoder");
//        config.setVersion("1.0.0");
//        config.setBasePath(BASE_URI);
//        config.setScan(true);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();

        CLStaticHttpHandler staticHttpHandler = new CLStaticHttpHandler(Main.class.getClassLoader(), "swagger-ui/");
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/docs");

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
      
    }
}

