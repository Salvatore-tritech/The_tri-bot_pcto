package it.aichallenge.bot;

import it.aichallenge.client.AIClient;
import it.aichallenge.skills.SkillRegistry;

/**
 * Bot “furbo” che prima chiede alle sue skill:
 * se una di loro sa rispondere, usa quella risposta;
 * altrimenti passa la palla al modello LLM.
 */
public class SkillfulBot implements Bot {

    private final SkillRegistry skills;
    private final AIClient llm;
    private String Personaggioslt = null;

    public SkillfulBot(SkillRegistry skills, AIClient llm) {
        this.skills = skills;
        this.llm = llm;
    }

    @Override
    public String reply(String userMessage) throws Exception {
        if (userMessage.startsWith("/")) {
            String prompt = userMessage.substring(1).split(" ")[0].toUpperCase();
            String PersonaggioFin = userMessage.substring(prompt.length() + 1).trim();

            switch (prompt) {
                case "PLAY":
                    if (!PersonaggioFin.isEmpty()) {
                        Personaggioslt = PersonaggioFin;
                        return ("Ok, ora mi comporterò come se fossi. Cosa vuoi chiedermi? "+ Personaggioslt);
                    } else {
                        return "Devi specificare un personaggio dopo /impersonifica. Esempio: /act Rihanna";
                    }
                case "RESET":
                    Personaggioslt = null;
                    return "Ok, torno a parlare normalmente.";
                default:
                    String local = skills.dispatch(userMessage);
                    if (local != null) {
                        return local;
                    } else {
                        return genRisposta(userMessage);
                    }
            }
        } else {
            return genRisposta(userMessage);
        }
    }

    private String genRisposta(String userMessage) throws Exception {
        if (Personaggioslt != null) {
            String prompt = "Rispondi alla seguente domanda come se fossi " + Personaggioslt + ":\n\n" + userMessage;
            return llm.chat(prompt);
        } else {
            return llm.chat(userMessage);
        }
    }
}
