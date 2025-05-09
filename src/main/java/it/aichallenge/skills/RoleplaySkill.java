package it.aichallenge.skills;

import it.aichallenge.client.AIClient;

public class RoleplaySkill implements BotSkill {

    private final AIClient ai;
    private String persona = null;

    public RoleplaySkill(AIClient ai) {
        this.ai = ai;
    }

    @Override
    public String tryReply(String userMessage) {
        if (userMessage.startsWith("/act ")) {
            persona = userMessage.substring(9).trim();
            return "Da ora risponderò come se fossi " + persona + ". Chiedimi qualcosa.";
        }

        if (userMessage.equalsIgnoreCase("/stop")) { //io uso gli EqualsIgnoreCase perche' sono piu' belli degli Equals :)
            String personaOld = persona;
            persona = null;
            return "Ho smesso di rispondere come " + personaOld + ".";
        }

        if (persona != null) {
            String prompt = String.format("Rispondi come se fossi %s, in prima persona, usando il suo stile e conoscenze note. Domanda: \"%s\"", persona, userMessage);
            try {
                return ai.chat(prompt);
            } catch (Exception e) {
                return "Errore nella risposta come " + persona + ": " + e.getMessage();
            }
        }

        return null; // Lascia ad altre skill
    }
}
