import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class RomanNumeralHandler implements HttpHandler {
    public final static String PATH = "/romannumeral";
    public final static String QUERYKEY = "query";

    public static class RomanNumeralHandlerException extends Exception {
        public RomanNumeralHandlerException(String message) {
            super(message);
            System.out.println(message);
        }
    }

    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Handling exchange:" + exchange);
        String requestMethod = exchange.getRequestMethod();
        URI requestURI = exchange.getRequestURI();
        String requestPath = requestURI.getPath();
        System.out.format("Received: %s, %s, %s\n", requestMethod, requestURI, requestPath);

        try {
            if(!requestMethod.equals("GET") || ! requestPath.equals(PATH)) {
                throw new RomanNumeralHandlerException("Request method not implemented");
            }

            handleRomanNumeralQueryResponse(exchange);

        } catch (RomanNumeralHandlerException e) {
            handleResponse(exchange, 404, e.getMessage());
        }
    }

    public Map<String, String> getQueryMap(String queryString) {
        String[] queries = queryString.split("&");
        Map<String, String> queryMap = new HashMap<>();
        for(String s: queries) {
            String[] splitS = s.split("=", 2);
            if(splitS.length != 2) {
                System.out.format("Cannot get key-val for %s. Skipping this query parameter\n", s);
                continue;
            }
            queryMap.put(splitS[0], splitS[1]);
        }
        return queryMap;
    }

    public void handleRomanNumeralQueryResponse(HttpExchange exchange) throws IOException, RomanNumeralHandlerException {
        String responseString;
        String queryString = exchange.getRequestURI().getQuery();
        Map<String, String> queryMap = getQueryMap(queryString);

        String queryVal = queryMap.getOrDefault(QUERYKEY, null);
        if(queryVal == null) {
            throw  new RomanNumeralHandlerException("Cannot find " + QUERYKEY + " in query string");
        }

        try {
            int intToConvert = Integer.parseInt(queryVal);
            if(intToConvert <= 0 || intToConvert >= 256) {
                throw new RomanNumeralHandlerException("Value to convert: " + intToConvert + " is out of range");
            }

            String romanInt = convertToRomanInt(intToConvert);
            responseString = romanInt;
        } catch (NumberFormatException e) {
            throw new RomanNumeralHandlerException("Invalid value for" + QUERYKEY + " in query string");
        }

        handleResponse(exchange, 200, responseString);
    }

    String convertToRomanInt(int intToConvert) {
        return Integer.toString(intToConvert);
    }

    public void handleResponse(HttpExchange exchange, int responseStatus, String response) throws IOException {
        OutputStream output = exchange.getResponseBody();

        exchange.sendResponseHeaders(responseStatus, response.length());
        output.write(response.getBytes());
        output.flush();
        output.close();
    }
}
