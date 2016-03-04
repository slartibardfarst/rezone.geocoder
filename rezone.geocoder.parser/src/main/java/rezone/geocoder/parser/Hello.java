package rezone.geocoder.parser;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import rezone.geocoder.parser.Geo;
import rezone.geocoder.parser.ParseDebug;
import rezone.geocoder.parser.QueryParser;

public class Hello {
    public Hello() {
    }

    public TestResponse TestLambda(String foo, Context context)
    {
        LambdaLogger logger = context.getLogger();

        logger.log("in TestLambda, foo is: " + foo);

        TestResponse response = new TestResponse();
        response.cacheHit = false;
        response.loadTime = 0;

        return response;
    }

    public Geo myHandler(Hello.RequestClass request, Context context) {
        LambdaLogger logger = context.getLogger();
        String address = request.getInput();
        logger.log("received : " + address + ".");
        Object dbg = null;
        QueryParser _parser = new QueryParser();
        Geo[] actual = _parser.parse(address, (ParseDebug)dbg);
        return actual[0];
    }

    public static class RequestClass {
        String input;

        public String getInput() {
            return this.input;
        }

        public void setInput(String input) {
            this.input = input;
        }

        public RequestClass(String input) {
            this.input = input;
        }

        public RequestClass() {
        }
    }
}
