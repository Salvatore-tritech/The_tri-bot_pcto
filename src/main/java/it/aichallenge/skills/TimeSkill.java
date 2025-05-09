package it.aichallenge.skills;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

/**
 * Risponde al comando /time restituendo l'ora locale (Europe/Rome).
 */
public class TimeSkill implements BotSkill {

    private static final String COMMAND = "/time";
    private static final DateTimeFormatter CLOCK =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public String tryReply(String userMessage) {
        if (COMMAND.equalsIgnoreCase(userMessage.trim())) {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Rome"));
            return "⏰ Sono le " + now.format(CLOCK) + " (ora locale).";
        }
        return null; // passa la palla ad altre skill o al fallback LLM
    }
}
