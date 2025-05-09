package it.aichallenge.skills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetIpify implements BotSkill {

    private String getPublicIP() {
        try {
            URL url = new URL("https://api.ipify.org?format=json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                int i_iniziale = response.indexOf(":") + 2;
                int i_finale = response.indexOf("\"}");
                return response.substring(i_iniziale, i_finale);
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String tryReply(String msg) {
        if (msg.equalsIgnoreCase("/ip")) {
            String ip = getPublicIP();
            if (ip != null) {
                return "IP: " + ip;
            } else {
                return "Impossibile ottenere l'IP.";
            }
        }
        return null;
    }
}