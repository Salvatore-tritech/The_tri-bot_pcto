package it.aichallenge.skills;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GetTime implements BotSkill {
    @Override
    public String tryReply(String userMessage) {
        String msg = userMessage.toLowerCase();

        if (msg.matches(".*\\b(che ore?|dimmi l.?orario|che tempo è|che ora è|orario attuale|current time)\\b.*")) {
            LocalTime now = LocalTime.now(); // Oggetto dalla libreria LocalTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Libreria usata per formattare l'ora

            return "Ora locale: " + now.format(formatter);
        }

        return null;
    }
}
