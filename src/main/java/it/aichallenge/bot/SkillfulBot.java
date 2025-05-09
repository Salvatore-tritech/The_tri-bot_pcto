package it.aichallenge.bot;

import it.aichallenge.client.AIClient;
import it.aichallenge.skills.SkillRegistry;

/**
 * Bot “furbo” che prima chiede alle sue skill:
 * se una di loro sa rispondere, usa quella risposta;
 * altrimenti passa la palla al modello LLM.
 */
public class SkillfulBot implements Bot {

    private String personaggioAttuale = "";
    private final AIClient llm;

    public SkillfulBot(SkillRegistry skills, AIClient llm) {
        this.llm = llm;
    }

    public String creaPromptPers(String userMessage){
        if(!personaggioAttuale.isEmpty()){
            return "adesso risponderò come " + personaggioAttuale + "mantieni il suo stile, personalità e rispondi a: " + userMessage;
        }
        return userMessage;
    }

    @Override
    public String reply(String userMessage) throws Exception {
        /*
        // 1️⃣ prova con le skill locali
        String local = skills.dispatch(userMessage);

        // 2️⃣ se nessuna skill ha risposto, delega al modello AI
        return (local != null) ? local : llm.chat(userMessage);
         */

        if(userMessage.startsWith("/")){
            personaggioAttuale = userMessage.substring(1).trim();
            return "adesso risponderò come " + personaggioAttuale;
        }

        String prompt = creaPromptPers(userMessage);
        return llm.chat(prompt);
    }
}
