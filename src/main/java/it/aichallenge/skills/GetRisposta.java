package it.aichallenge.skills;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class GetRisposta implements BotSkill {

    @Override
    public String tryReply(String userMessage) {
        if (userMessage == null) return null;

        String upper = userMessage.toUpperCase();

        if (upper.contains("IP") || upper.contains("/IP") || upper.contains("INDIRIZZO IP")) {
            return "Il tuo IP pubblico è: " + getPublicIp();
        }

        return null;
    }

    private static String getPublicIp() {
        try (Scanner s = new Scanner(new URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
            return s.hasNext() ? s.next() : "non disponibile";
        } catch (Exception e) {
            return "Errore nel recupero dell'IP";
        }
    }

    public static void handle(HttpExchange exchange) throws IOException {
        String ip = getPublicIp();
        byte[] response = ip.getBytes();
        exchange.sendResponseHeaders(200, response.length);
    }
}


