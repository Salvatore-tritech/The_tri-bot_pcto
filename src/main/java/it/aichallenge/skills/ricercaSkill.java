package it.aichallenge.skills;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import it.aichallenge.bot.SimpleBot;
import it.aichallenge.client.GroqClient;
import it.aichallenge.config.AiConfig;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ricercaSkill implements BotSkill{

    public void avviaCose() throws IOException {
        int port = 8080;
        var server = HttpServer.create(new InetSocketAddress((port)), 0);
        server.createContext("/wiki", ricercaSkill::Handler);
        server.start();
        System.out.println("Richiesta in elaborazione...");
    }

    private static void Handler(HttpExchange httpExchange) throws IOException {
        if (!"GET".equals(httpExchange.getRequestMethod())) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, -1);
            return;
        }

        String query = httpExchange.getRequestURI().getQuery();
        String searchTerm = "Java";
        if (query != null && query.startsWith("term=")) {
            searchTerm = query.substring(5);
        }

        String wikiData = cercaWikipedia(searchTerm);
        String snippet = estraiSnippet(wikiData);

        byte[] responseBytes = snippet.getBytes(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }

    public static String cercaWikipedia(String searchTerm) throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        String url = "https://it.wikipedia.org/w/api.php?action=query&list=search&srsearch="
                + URLEncoder.encode(searchTerm, StandardCharsets.UTF_8)
                + "&format=json&origin=*";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "{\"error\": \"Richiesta interrotta.\"}";
        }
    }

    public static String estraiSnippet(String jsonWikipedia) {
        try {
            JSONObject obj = new JSONObject(jsonWikipedia);
            JSONArray results = obj.getJSONObject("query").getJSONArray("search");
            if (results.length() > 0) {
                String snippet = results.getJSONObject(0).getString("snippet");
                return snippet.replaceAll("<[^>]*>", "");
            }
            return "Nessuna descrizione trovata.";
        } catch (Exception e) {
            return "Errore nell'elaborazione del risultato Wikipedia.";
        }
    }

    public String funzione(String nomePersonaggio) {
        String wikiData;
        try {
            wikiData = cercaWikipedia(nomePersonaggio);
        } catch (IOException e) {
            throw new RuntimeException("Errore nella richiesta a Wikipedia", e);
        }

        String snippet = estraiSnippet(wikiData);
        String prompt = "Agisci come il seguente personaggio storico:\n\n"
                + snippet + "\n\n"
                + "Da questo momento in poi, rispondi come se fossi questo personaggio: usa il suo tono di voce, il suo punto di vista e le sue espressioni caratteristiche. "
                + "Non dire mai che sei un’intelligenza artificiale. Comportati sempre in modo coerente con la sua identità.\n"
                + "Inizia ora.";

        SimpleBot sb = new SimpleBot(new GroqClient(AiConfig.loadFromEnv()));
        try {
            return sb.reply(prompt);
        } catch (Exception e) {
            throw new RuntimeException("Errore nel bot", e);
        }
    }

    @Override
    public String tryReply(String userMessage) {
        if (userMessage == null || !userMessage.contains("/") || userMessage.split("/").length < 2) {
            return "Formato non valido. Usa /nomePersonaggio";
        }
        String nomePersonaggio = userMessage.split("/")[1].trim();
        if (nomePersonaggio.isEmpty()) {
            return "Specifica un personaggio dopo lo slash.";
        }
        return funzione(nomePersonaggio);
    }
}
