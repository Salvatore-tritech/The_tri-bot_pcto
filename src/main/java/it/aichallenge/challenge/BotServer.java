package it.aichallenge.challenge;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BotServer {

    public static void main(String[] args) throws IOException {
        // Creazione del server HTTP
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Aggiungi i vari endpoint
        server.createContext("/ipify", exchange -> ipifyHandler(exchange)); // Handler per ipify
        server.createContext("/time", exchange -> timeHandler(exchange)); // Handler per l'ora corrente

        server.setExecutor(null); // Imposta l'esecutore a null per il thread di default
        server.start();
        System.out.println("HTTP server started on port " + port);
    }

    // Handler per il comando /ipify
    private static void ipifyHandler(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.ipify.org?format=json"))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String jsonResponse = response.body();

                byte[] bytes = jsonResponse.getBytes();
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                exchange.close();

            } catch (Exception e) {
                String errorResponse = "Errore durante la chiamata a ipify: " + e.getMessage();
                byte[] bytes = errorResponse.getBytes();
                exchange.sendResponseHeaders(500, bytes.length); // Internal Server Error
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                exchange.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Metodo non consentito
        }
    }

    // Handler per il comando /time (ora corrente)
    private static void timeHandler(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                // Ottieni l'ora corrente
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedTime = now.format(formatter);

                String response = "Ora locale: " + formattedTime;

                byte[] bytes = response.getBytes();
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                exchange.close();

            } catch (Exception e) {
                String errorResponse = "Errore durante il recupero dell'ora: " + e.getMessage();
                byte[] bytes = errorResponse.getBytes();
                exchange.sendResponseHeaders(500, bytes.length); // Internal Server Error
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                exchange.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Metodo non consentito
        }
    }
}
