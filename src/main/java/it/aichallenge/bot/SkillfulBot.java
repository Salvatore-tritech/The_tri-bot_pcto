package it.aichallenge.bot;

import it.aichallenge.client.AIClient;
import it.aichallenge.skills.PersonalityManager;
import it.aichallenge.skills.SkillRegistry;

/**
 * Bot “furbo” che prima chiede alle sue skill:
 * se una di loro sa rispondere, usa quella risposta;
 * altrimenti passa la palla al modello LLM.
 */
public class SkillfulBot implements Bot {

    private final SkillRegistry skills;
    private final AIClient llm;

    public SkillfulBot(SkillRegistry skills, AIClient llm) {
        this.skills = skills;
        this.llm = llm;
    }

    @Override
    public String reply(String userMessage) throws Exception {
        // 1️⃣ prova con le skill locali
        String local = skills.dispatch(userMessage);

        if(local!=null) return local;

        String personality = PersonalityManager.getActivePersonality();
        if(PersonalityManager.isDexterMode()){
            userMessage= "Rispondi come se fossi Dexter Morgan di *Dexter*, usa il suo tono riflessivo e distaccatto. Domanda utente "+ userMessage;
        }




        // 2️⃣ se nessuna skill ha risposto, delega al modello AI
        return (local != null) ? local : llm.chat(userMessage);
    }
}
