package it.aichallenge.skills;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Piccolo modulo agganciabile (“skill”) che può rispondere
 * direttamente a comandi chiari dell’utente
 * — per esempio «che giorno è?», «/help», mini‑utility, ecc. —
 * senza dover passare dal modello LLM.
 * <p>
 * Regole d’ingaggio:
 * • deve essere sincrono (risponde subito),
 * • non mantiene stato fra una richiesta e l’altra,
 * • input e output solo testo.
 */
@FunctionalInterface
public interface BotSkill {

    /**
     * @param userMessage l’intero messaggio digitato dall’utente
     *
     * @return una stringa se la skill se ne occupa;
     *         {@code null} se vuole lasciare la palla
     *         ad altre skill o all’LLM.
     */
    String tryReply(String userMessage) throws IOException;
}
