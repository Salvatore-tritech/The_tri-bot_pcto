package it.aichallenge.skills;

import it.aichallenge.bot.SimpleBot;
import it.aichallenge.client.GroqClient;
import it.aichallenge.config.AiConfig;

public class ActorSkill implements BotSkill{

    private demoPersonaggi demo = new demoPersonaggi();
    private boolean attore = false;
    private String personaggioDaImpersonare;

    public String funzione(String nomePersonaggio) {
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
        SimpleBot sb = new SimpleBot(new GroqClient(AiConfig.loadFromEnv()));
        try {
            return sb.reply(prompt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String tryReply(String userMessage) {
        if(!userMessage.contains("/")){
            if(!attore){
                return null;
            }else{
                return funzione(personaggioDaImpersonare);
            }
        }
        this.attore = true;
        String[] s = userMessage.split("/");
        String nomePersonaggio = s[1];
        System.out.println(nomePersonaggio);
        return funzione(nomePersonaggio);
    }
}
