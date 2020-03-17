import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class RomanNumeralHandler implements HttpHandler {
    public final static String PATH = "/romannumeral";

    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Handling exchange:" + exchange);
        String requestMethod = exchange.getRequestMethod();
        URI requestURI = exchange.getRequestURI();
        String requestPath = requestURI.getPath();
        System.out.format("Received: %s, %s, %s\n", requestMethod, requestURI, requestPath);

        if(requestMethod.equals("GET") && requestPath.equals(PATH)) {
            handleRomanNumeralQueryResponse(exchange);
        } else {
            handleDefaultResponse(exchange);
        }
    }

    public void handleRomanNumeralQueryResponse(HttpExchange exchange) throws IOException {
        handleResponse(exchange, 200, "Hello World");
    }

    public void handleDefaultResponse(HttpExchange exchange) throws IOException {
        handleResponse(exchange, 404, "");
    }
    public void handleResponse(HttpExchange exchange, int responseStatus, String response) throws IOException {
        OutputStream output = exchange.getResponseBody();

        exchange.sendResponseHeaders(responseStatus, response.length());
        output.write(response.getBytes());
        output.flush();
        output.close();
    }
}
