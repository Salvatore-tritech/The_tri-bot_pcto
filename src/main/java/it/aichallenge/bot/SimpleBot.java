package it.aichallenge.bot;

import it.aichallenge.client.AIClient;
import it.aichallenge.skills.GetTime;

/** Classe di wrap per il forward delle chiamate al model  {@link AIClient}. */

public class SimpleBot implements Bot {

    private final AIClient client;
    private GetTime getTime = new GetTime();

    public SimpleBot(AIClient client) {
        this.client = client;
    }

    @Override
    public String reply(String userMessage) throws Exception {
        if(userMessage.equals("che ore sono?")){
            this.getTime = new GetTime();
            this.getTime.getTime();
            return "Ciao sono le: "+this.getTime.richiediTime();
            //return this.getTime.tryReply(null); risposta diretta senza richiesta al server
        }
        return client.chat(userMessage);
    }

}