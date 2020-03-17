import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RomanNumeralServer {
    public final static String HOST = "localhost";
    public final static int PORT = 8080;

    public static void main(String[] args) {
        try {
            System.out.format("Starting http server: %s:%d\n", HOST, PORT);
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(HOST, PORT), 0);
            httpServer.createContext("/", new RomanNumeralHandler());
            httpServer.start();
            System.out.format("Started http server: %s:%d\n", HOST, PORT);
        } catch (IOException e) {
            System.out.println("Unable to start httpServer due to: " + e.getMessage());
            System.exit(1);
        }
    }
}
