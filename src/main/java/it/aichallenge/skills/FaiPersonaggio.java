package it.aichallenge.skills;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
                personaggioAttuale = "steve";
                return "I... am steve.";
            case "/papaleone":
                personaggioAttuale = "papaleone";
                return "Pax vobiscum";
            case "/toto":
                personaggioAttuale = "toto";
                return "Sono il dipendente più simpatico della Tritech!";
            case "/reset":
                personaggioAttuale = null;
                return "AHAHA Okk, rieccomi, mi sono divertito!";
        }

        if (personaggioAttuale != null) {
            // Ogni domanda riceve una risposta "stilizzata" da Wikipedia
            String risposta = searchEngine(personaggioAttuale);
            if (risposta != null) {
                return personaggioAttuale + ": " + risposta;
            } else {
                return personaggioAttuale + ": Non so cosa rispondere a questo...";
            }
        }

        return null;
    }

    // Metodo per prendere info da Wikipedia
    private String searchEngine(String personaggio) {
        try {
            String encoded = URLEncoder.encode(personaggio, "UTF-8");
            String url = "https://it.wikipedia.org/api/rest_v1/page/summary/" + encoded;

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            if (conn.getResponseCode() != 200) {
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder(); //Formattazione testo
            String line;
            while ((line = in.readLine()) != null) sb.append(line);
            in.close();

            String json = sb.toString();

            // Cerca la posizione dove inizia il campo "extract"
            int inizio = json.indexOf("\"extract\":\"");
            // Se non trova il campo "extract", ritorna null
            if (inizio == -1) return null;
            inizio += 10;

            // Cerca la fine del testo, cioè la chiusura del campo
            int fine = json.indexOf("\",", inizio);

            if (fine == -1) fine = json.length();
            String estratto = json.substring(inizio, fine);

            // Pulisce i caratteri speciali usati nella formattazione JSON
            estratto = estratto.replace("\\n", "\n").replace("\\\"", "\"");

            if (estratto.length() > 400) {
                estratto = estratto.substring(0, 400) + "...";
            }
            return estratto;
        } catch (
                Exception e) {
            return null;
        }
    }
}
