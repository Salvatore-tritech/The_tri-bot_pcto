package it.aichallenge.skills;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GetTime implements BotSkill {

    @Override
    public String tryReply(String msg) {
        if (msg.equalsIgnoreCase("/time")) {
            LocalTime ora = LocalTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
            return "Ora attuale: " + ora.format(formato);
        }
        return null;
    }
}
