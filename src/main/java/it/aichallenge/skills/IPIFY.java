package it.aichallenge.skills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class IPIFY implements BotSkill {
    @Override
    public String tryReply(String userMessage) throws IOException {

        String reply = "";

        if (userMessage.contains("IPIFY") || userMessage.contains("ipify")) {
            String indirizzo = "https://api.ipify.org/?format=json";
            URL url = new URL(indirizzo);
            try (var in = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }
            }
        }
        return reply;
    }
}
