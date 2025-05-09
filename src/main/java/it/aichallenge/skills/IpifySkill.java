package it.aichallenge.skills;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IpifySkill implements BotSkill{

    @Override
    public String tryReply(String userMessage) {
        if (userMessage.contains("ipify")) {
            HttpRequest richiesta = HttpRequest.newBuilder(
                            URI.create("https://api.ipify.org?format=json"))
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
            JSONObject response = new JSONObject(responseBody);
            return response.getString("ip");
        }
        return null;
    }
}
