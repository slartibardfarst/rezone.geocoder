package rezone.geocoder.parser;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import rezone.geocoder.parser.Geo;
import rezone.geocoder.parser.ParseDebug;
import rezone.geocoder.parser.QueryParser;

public class Hello {
    public static QueryParser _parser = null;

    public Hello() {
    }

    public TestResponse TestLambda(String foo, Context context)
    {
        //QueryParser _parser = null;
        LambdaLogger logger = context.getLogger();

        logger.log("In TestLambda\n");
        logger.log("foo is: " + foo + "\n");

        TestResponse response = new TestResponse();
        response.stringParam = foo;

        if(null == _parser) {
            long start = System.nanoTime();
            _parser = new QueryParser();
            response.parserExistsOnStartup = false;
            response.parserConstructionMicros = (System.nanoTime() - start)/(1000);
            response.parserConstructionMillis = (response.parserConstructionMicros)/(1000);
        }
        else
            response.parserExistsOnStartup = true;

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
