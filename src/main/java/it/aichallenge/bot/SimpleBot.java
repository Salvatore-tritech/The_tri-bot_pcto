package it.aichallenge.bot;

import it.aichallenge.client.AIClient;
import it.aichallenge.skills.GetTime;
import it.aichallenge.skills.SkillIpify;
import it.aichallenge.skills.demoPersonaggi;

/** Classe di wrap per il forward delle chiamate al model  {@link AIClient}. */

public class SimpleBot implements Bot {

    private final AIClient client;
    private GetTime getTime = new GetTime();
    private SkillIpify ipify = new SkillIpify();
    private demoPersonaggi demo = new demoPersonaggi();
    private boolean attore = false;
    private String personaggioDaImpersonare;

    public SimpleBot(AIClient client) {
        this.client = client;
    }

    @Override
    public String reply(String userMessage) throws Exception {
        return client.chat(userMessage);
    }
}