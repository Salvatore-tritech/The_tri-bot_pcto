package it.aichallenge.skills;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SkillTime implements BotSkill {

    @Override
    public String tryReply(String userMessage) {
        if (userMessage == null) return null;

        String upper= userMessage.toUpperCase();

        if (upper.contains("CHE ORA É")||upper.contains("CHE ORE SONO")||upper.contains("ORA"))
        {
            return "Sono le"+getCurrentTime();
        }

        return null;
    }

    private String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return now.format(formatter);
    }


}