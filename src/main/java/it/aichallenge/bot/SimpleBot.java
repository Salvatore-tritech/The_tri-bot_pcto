package it.aichallenge.bot;

import it.aichallenge.client.AIClient;
import it.aichallenge.skills.GetTime;

/** Classe di wrap per il forward delle chiamate al model  {@link AIClient}. */

public class SimpleBot implements Bot {

    private final AIClient client;

    public SimpleBot(AIClient client) {
        this.client = client;
    }

    @Override
    public String reply(String userMessage) throws Exception {

        return client.chat(userMessage);
    }

}