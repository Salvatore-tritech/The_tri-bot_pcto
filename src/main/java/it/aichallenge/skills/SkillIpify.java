package it.aichallenge.skills;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SkillIpify implements BotSkill {


    @Override
    public String tryReply(String userMessage) {
        if (userMessage == null) return null;
        String lower = userMessage.toLowerCase();
        if (lower.contains("mio ip") || lower.contains("che ip ho")) {
            try {
                String url = "https://api.ipify.org?format=text";
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

                HttpClient client= HttpClient.newHttpClient();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200){
                    return "il tuo IP publico è:\n"+response.body();

                } else{
                    return "errore nel recupero dell'indirizzo IP:"+response.statusCode();
                }

            } catch (Exception e){
                return "errore durante la chiamata ad ipify:"+e.getMessage();
            }
        }
        return null;
    }

}
