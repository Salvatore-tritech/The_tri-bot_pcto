package it.aichallenge.skills;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class IpSkill implements BotSkill {

    private static final String urlIpfy = "https://api.ipify.org?format=json";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public String tryReply(String userMessage){
        if(userMessage.equalsIgnoreCase("/ip")){
            try{
                return "l'IP pubblico è: " + getPublicIp();
            } catch (Exception e){
                return "impossibile recuperare l'IP";
            }
        }
        return null;
    }

    public String getPublicIp() throws IOException {
        Request request = new Request.Builder()
                .url(urlIpfy)
                .build();
        try (Response response = client.newCall(request).execute()){
            if(!response.isSuccessful()){
                throw new IOException("Errore nella richiesta ipify" + response);
            }
            return response.body().string().trim();
        }
    }

}
