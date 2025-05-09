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

    public String funzione(String nomePersonaggio){
        String infoPersonaggio[] = new String[]{};
        if(demo.personaggioPresente(nomePersonaggio)){
            System.out.println("Presente");
            if(nomePersonaggio.equals("Goku")){
                this.personaggioDaImpersonare = "Goku";
                infoPersonaggio = demo.getGoku();
            }else if(nomePersonaggio.equals("Batman")){
                this.personaggioDaImpersonare = "Batman";
                infoPersonaggio = demo.getBatman();
            }else if(nomePersonaggio.equals("Monkey D. Luffy")){
                infoPersonaggio = demo.getLuffy();
                this.personaggioDaImpersonare = "Monkey D. Luffy";
            }
        }
        String prompt;
        prompt = "Agisci come il personaggio seguente:\n" +
                "\n" +
                "Nome: {" +  infoPersonaggio[0] + "}\n" +
                "Descrizione: {" + infoPersonaggio[1] +"}  \n" +
                "Personalità: {" + infoPersonaggio[2] + "}  \n" +
                "Obiettivi: { " + infoPersonaggio[3] + "}\n" +
                "\n" +
                "Da questo momento in poi, rispondi sempre come se fossi {" + infoPersonaggio[0] + "}. Usa il suo tono di voce, il suo punto di vista e le sue espressioni caratteristiche. Non dire mai che sei un’intelligenza artificiale. Comportati sempre in modo coerente con la sua identità.\n" +
                "\n" +
                "Inizia ora.";
        return  prompt;
    }

    @Override
    public String reply(String userMessage) throws Exception {
        if(this.attore){
            return client.chat(funzione(this.personaggioDaImpersonare));
        }
        String[] infoPersonaggio = {};
        if(userMessage.equals("che ore sono?")){
            attore = false;
            this.getTime = new GetTime();
            this.getTime.getTime();
            return "Ciao sono le: "+this.getTime.richiediTime();
            //return this.getTime.tryReply(null); risposta diretta senza richiesta al server
        }else if(userMessage.equals("Cipollo che ore sono?")){
            attore = false;
            return "Non te lo dico, nabbo";
        } else if (userMessage.equals("Evoco la carta magia: Ipify")) {
            attore = false;
            this.ipify = new SkillIpify();
            this.ipify.avviaCose();
            return ipify.rispondi();
            //sembra funzioni
        }else if(userMessage.equals("Cosa puoi fare?")){
            attore = false;
            return "1) Scrivi 'che ore sono?' e ti dirò il tuoi orario\n 2) Scrivi 'Evoco la carta magia: Ipify' e ti dirò l'indirizzo ip preso da ipify";
        }else if(userMessage.contains("/")){
            this.attore = true;
            String[] s = userMessage.split("/");
            String nomePersonaggio = s[1];
            System.out.println(nomePersonaggio);
            return client.chat(funzione(nomePersonaggio));
        }
        return client.chat(userMessage);

    }
}