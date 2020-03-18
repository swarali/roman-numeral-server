package org.example;

import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import static org.mockito.Mockito.*;

public class RomanNumeralHandlerTest {
    @Test
    public void handleQuery() throws IOException {
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        OutputStream mockOutputStream = mock(OutputStream.class);

        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        when(mockHttpExchange.getRequestURI()).thenReturn(URI.create("/romannumeral?query=49"));
        when(mockHttpExchange.getResponseBody()).thenReturn(mockOutputStream);

        new RomanNumeralHandler().handle(mockHttpExchange);

        String expectedOutput = "XLIX";
        verify(mockOutputStream).write(expectedOutput.getBytes());
        verify(mockHttpExchange).sendResponseHeaders(200, expectedOutput.length());
    }

    @Test
    public void handleUnknownPath() throws IOException {
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        OutputStream mockOutputStream = mock(OutputStream.class);

        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        when(mockHttpExchange.getRequestURI()).thenReturn(URI.create("/"));
        when(mockHttpExchange.getResponseBody()).thenReturn(mockOutputStream);

        new RomanNumeralHandler().handle(mockHttpExchange);

        String expectedOutput = "Request method not implemented";
        verify(mockOutputStream).write(expectedOutput.getBytes());
        verify(mockHttpExchange).sendResponseHeaders(404, expectedOutput.length());
    }

    @Test
    public void handleNoQuery() throws IOException {
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        OutputStream mockOutputStream = mock(OutputStream.class);

        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        when(mockHttpExchange.getRequestURI()).thenReturn(URI.create("/romannumeral"));
        when(mockHttpExchange.getResponseBody()).thenReturn(mockOutputStream);

        new RomanNumeralHandler().handle(mockHttpExchange);

        String expectedOutput = "Cannot find query in query string";
        verify(mockOutputStream).write(expectedOutput.getBytes());
        verify(mockHttpExchange).sendResponseHeaders(404, expectedOutput.length());
    }

    @Test
    public void handleWrongValue() throws IOException {
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        OutputStream mockOutputStream = mock(OutputStream.class);

        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        when(mockHttpExchange.getRequestURI()).thenReturn(URI.create("/romannumeral?query=abc"));
        when(mockHttpExchange.getResponseBody()).thenReturn(mockOutputStream);

        new RomanNumeralHandler().handle(mockHttpExchange);

        String expectedOutput = "Invalid value for query in query string";
        verify(mockOutputStream).write(expectedOutput.getBytes());
        verify(mockHttpExchange).sendResponseHeaders(404, expectedOutput.length());
    }

    @Test
    public void handleOutOfBoundsValue() throws IOException {
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        OutputStream mockOutputStream = mock(OutputStream.class);

        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        when(mockHttpExchange.getRequestURI()).thenReturn(URI.create("/romannumeral?query=0"));
        when(mockHttpExchange.getResponseBody()).thenReturn(mockOutputStream);

        new RomanNumeralHandler().handle(mockHttpExchange);

        String expectedOutput = "Value: 0 is out of range";
        verify(mockOutputStream).write(expectedOutput.getBytes());
        verify(mockHttpExchange).sendResponseHeaders(404, expectedOutput.length());
    }
}
