package it.aichallenge.skills;

import it.aichallenge.client.GroqClient;
import it.aichallenge.config.AiConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class SkillMovie implements BotSkill{

    @Override
    public String tryReply(String userMessage) {
        GroqClient groq = new GroqClient(AiConfig.loadFromEnv());
        String[] strings = userMessage.split(" ");
        if (strings[0].equals("/movie")) {
            String film = userMessage.substring("/movie ".length());
            film = film.replaceAll(" ", "%20");

            HttpRequest richiesta = HttpRequest.newBuilder(
                            URI.create("https://it.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + film + "&format=json"))
                    .GET()
                    .build();
            String responseBody = null;
            try {
                responseBody = HttpClient.newHttpClient()
                        .send(richiesta, HttpResponse.BodyHandlers.ofString())
                        .body();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray searchResults = jsonResponse
                    .getJSONObject("query")
                    .getJSONArray("search");

            StringBuilder fullSnippets = new StringBuilder();

            for (int i = 0; i < searchResults.length(); i++) {
                JSONObject object = searchResults.getJSONObject(i);
                String snippet = object.getString("snippet");
                fullSnippets.append(snippet).append(" ");
            }

            String descrizione = fullSnippets.toString()
                    .replaceAll("<[^>]*>", "")
                    .replace("&nbsp;", " ")
                    .replace("&amp;", "&")
                    .replace("&#039;", "'");

            try {
                return groq.chat("Data questa descrizione, parlami di questo film in italiano, parlo solo dei film e tralascia il resto " + descrizione);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}