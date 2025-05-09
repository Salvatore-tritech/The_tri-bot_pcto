package it.aichallenge.skills;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Registro super‑semplice delle skill.
 * Le interroga nell’ordine in cui sono state aggiunte.
 */
public class SkillRegistry {

    private final List<BotSkill> skills = new ArrayList<>();

    /**
     * Aggiunge una nuova skill al registro
     * e restituisce il registry per permettere il chaining.
     */
    public SkillRegistry add(BotSkill skill) {
        skills.add(skill);
        GetTime skil=new GetTime();
        skills.add(skil);

        return this;
    }

    /**
     * Passa il messaggio utente a ogni skill, in sequenza,
     * finché trova la prima che risponde (cioè restituisce qualcosa di non‑null).
     *
     * @param userMessage l’intero messaggio digitato dall’utente
     * @return la prima risposta trovata, oppure {@code null}
     *         se nessuna skill se ne occupa.
     */
    public String dispatch(String userMessage) {
        for (BotSkill s : skills) {
            String out = null;
            try {
                out = s.tryReply(userMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (out != null) return out;   // missione compiuta, stop qui
        }
        return null;  // nessuna skill ha risposto
    }
}
