package it.aichallenge.skills;

public class FaiPersonaggio implements BotSkill {

    private String personaggioAttuale = null;

    @Override
    public String tryReply(String personaggio) {
        switch (personaggio.toLowerCase()) {
            case "/goku":
                personaggioAttuale = "goku";
                return "Io sono Goku, il Saiyan che protegge la Terra.";
            case "/batman":
                personaggioAttuale = "batman";
                return "Io sono Batman... Agisco nell’ombra per la giustizia.";
            case "/mario":
                personaggioAttuale = "mario";
                return "It's me, Mario! Let's-e go!";
            case "/steve":
                personaggioAttuale = "mario";
                return "I... am steve.";
            case "/papaleone":
                personaggioAttuale = "mario";
                return "Pax vobiscum";
            case "/toto":
                personaggioAttuale = "mario";
                return "Sono il dipendente più simpatico della Tritech!";
            case "/reset":
                personaggioAttuale = null;
                return "AHAHA Okk, rieccomi, mi sono divertito!";
        }

        if (personaggioAttuale != null) {
            switch (personaggioAttuale) {
                case "goku":
                    return "Goku dice: Non mollare mai!!";
                case "batman":
                    return "Batman dice: Il buio è mio alleato.";
                case "mario":
                    return "Mario dice: Yahoo!";
                case "steve":
                    return "La la la lava!";
                case "papaleone":
                    return "Abbi sempre fede in Dio";
                case "toto":
                    return "Cosa vi ripeto da 2 giorni!";
            }
        }
        return null;
    }
}
