package it.aichallenge.skills;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetIpify implements BotSkill {

    @Override
    public String tryReply(String userMessage) {
        if (userMessage.toLowerCase().startsWith("/ipify")) {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://api.ipify.org?format=json"))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                return "Risposta da ipify:\n" + response.body();

            } catch (Exception e) {
                return "Errore durante la chiamata a ipify: " + e.getMessage();
            }
        }

        return null;
    }
}
