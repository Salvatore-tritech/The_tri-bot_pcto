package it.aichallenge.skills;

import java.net.URL;
import java.util.Scanner;

public class SkillsIpfy implements BotSkill {

    @Override
    public String tryReply(String userMessage) {
        if (userMessage == null) return null;

        String upper = userMessage.toUpperCase();

        if (upper.contains("IP") || upper.contains("/IP") || upper.contains("INDIRIZZO IP")) {
            return "Il tuo IP pubblico è: " + getPublicIp();
        }

        return null;
    }

    private String getPublicIp() {
        try (Scanner s = new Scanner(new URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
            return s.hasNext() ? s.next() : "non disponibile";
        } catch (Exception e) {
            return "Errore nel recupero dell'IP";
        }
    }
}
