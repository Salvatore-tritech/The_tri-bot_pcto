package it.aichallenge.client;

import java.io.IOException;

/**

 Very small abstraction over an LLM chat endpoint.
 */
public interface AIClient {

    /**

     Invia la richiesta (prompt) dell'user e ritorna la risposta del modello

     @param prompt user message

     @return assistant text reply

     @throws IOException network / serialization issues
     */
    String chat(String prompt) throws IOException;
}