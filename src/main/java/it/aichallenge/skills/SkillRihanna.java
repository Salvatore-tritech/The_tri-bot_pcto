package it.aichallenge.skills;

public class SkillRihanna implements BotSkill{

    @Override
    public String tryReply(String userMessage) {
        if (userMessage == null) return null;

        String upper= userMessage.toUpperCase();
        if (upper.equals("/RIHANNA")) {
            return "Ciao, sono Rihanna! ora converserò io con te...";
        }

        return null;

    }
}
