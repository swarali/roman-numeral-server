package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Http handler for RomanNumeralServer
 */
public class RomanNumeralHandler implements HttpHandler {
    public final static String PATH = "/romannumeral";
    public final static String QUERYKEY = "query";

    /**
     * Exception class for invalid requests
     */
    private static class RomanNumeralHandlerException extends RuntimeException {
        RomanNumeralHandlerException(String message) {
            super(message);
            System.out.println(message);
        }
    }

    /**
     * Returns roman numeral from the query string of the request with request path: "http://.../romannumeral?query=number
     * @param exchange - HTTP request received on the server
     * @throws IOException
     */
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        URI requestURI = exchange.getRequestURI();
        System.out.format("Received: %s %s\n", requestMethod, requestURI);

        try {
            if(!requestMethod.equals("GET") || ! requestURI.getPath().equals(PATH)) {
                throw new RomanNumeralHandlerException("Request method not implemented");
            }

            handleRomanNumeralQueryResponse(exchange);

        } catch (RomanNumeralHandlerException e) {
            handleResponse(exchange, 404, e.getMessage());
        }
    }

    private Map<String, String> getQueryMap(String queryString) {
        Map<String, String> queryMap = new HashMap<>();
        if(queryString == null) return queryMap;

        String[] queries = queryString.split("&");
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

    private void handleRomanNumeralQueryResponse(HttpExchange exchange) throws IOException, RomanNumeralHandlerException {
        String responseString;
        String queryString = exchange.getRequestURI().getQuery();
        Map<String, String> queryMap = getQueryMap(queryString);

        String queryVal = queryMap.getOrDefault(QUERYKEY, null);
        if(queryVal == null) {
            throw  new RomanNumeralHandlerException("Cannot find " + QUERYKEY + " in query string");
        }

        try {
            int value = Integer.parseInt(queryVal);
            RomanNumeral rn = new RomanNumeral(value);
            responseString = rn.romanNumeral;
        } catch (NumberFormatException e) {
            throw new RomanNumeralHandlerException("Invalid value for " + QUERYKEY + " in query string");
        } catch (RomanNumeral.ValueOutOfBoundsException e) {
            throw new RomanNumeralHandlerException(e.getMessage());
        }

        handleResponse(exchange, 200, responseString);
    }

    private void handleResponse(HttpExchange exchange, int responseStatus, String response) throws IOException {
        OutputStream output = exchange.getResponseBody();

        exchange.sendResponseHeaders(responseStatus, response.length());
        output.write(response.getBytes());
        output.flush();
        output.close();
    }
}
