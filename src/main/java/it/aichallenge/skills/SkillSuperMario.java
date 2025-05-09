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

public class SkillSuperMario implements BotSkill{
    private static boolean flag = false;
    private static String carattere;
    private static final String personaggio = "Super%20Mario";

    @Override
    public String tryReply(String userMessage) {
        GroqClient groq = new GroqClient(AiConfig.loadFromEnv());
        if (userMessage.equals("/superMario")){
            flag = !flag;
            if (flag) {
                HttpRequest richiesta = HttpRequest.newBuilder(
                                URI.create("https://it.wikipedia.org/w/api.php?action=query&list=search&srsearch="+personaggio+"&format=json"))
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

                StringBuilder fullSnippet = new StringBuilder();

                for (int i = 0; i < searchResults.length(); i++) {
                    JSONObject item = searchResults.getJSONObject(i);
                    String snippet = item.getString("snippet");
                    fullSnippet.append(snippet).append(" ");
                }

                String descrizione = fullSnippet.toString()
                        .replaceAll("<[^>]*>", "")
                        .replace("&nbsp;", " ")
                        .replace("&amp;", "&")
                        .replace("&#039;", "'");

                try {
                    carattere = groq.chat("Data questa descrizione, rigenera in modo tale che ora il bot parli come questo personaggio" + descrizione);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return (flag) ? "Da ora parlerò come super Mario":"Da ora non parlerò più come Super Mario";
        } else if (flag){
            try {
                return groq.chat(carattere+". Fingi di essere questo personaggio, e rispondi a questa domanda "+userMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}