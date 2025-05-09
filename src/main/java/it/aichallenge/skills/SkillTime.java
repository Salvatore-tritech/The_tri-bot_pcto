package it.aichallenge.skills;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.time.LocalTime;

public class SkillTime implements BotSkill {

    public static String extractZone(String userMessage) {
        if (userMessage.contains("zona:")){
            int size = userMessage.indexOf("zona:");
            return userMessage.substring(size+"zona:".length(), userMessage.length()-1);
        }
        return null;
    }

    @Override
    public String tryReply(String userMessage){
        if (userMessage.toLowerCase().contains("ora") || userMessage.toLowerCase().contains("orario") || userMessage.toLowerCase().contains("ore")) {
            if (userMessage.toLowerCase().contains("qual'è") || userMessage.toLowerCase().contains("che")){
                String fuso = extractZone(userMessage);
                if (fuso == null) {
                    DecimalFormat df = new DecimalFormat("#.00");
                    return "L'orario attuale è " + LocalTime.now();
                }
                else {
                    HttpRequest richiesta = HttpRequest.newBuilder(
                                    URI.create("https://timeapi.io/api/time/current/zone?timeZone=" + fuso))
                            .header("Content-Type", "application/json")
                            .GET()
                            .build();
                    try {
                        HttpResponse<String> risposta = HttpClient.newHttpClient().send(richiesta, HttpResponse.BodyHandlers.ofString());
                        JSONObject json = new JSONObject(risposta.body());
                         return "L'orario attuale per il fuso orario "+fuso+" è "+ json.getString("time");
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }
}