package it.aichallenge.bot;

/** Minima astrazione comune a tutti i modelli llm. */

 public interface Bot {
     /*
     * @param userMessage prompt di testo
     * @return risposta della llm
     */
    String reply(String userMessage) throws Exception;
}