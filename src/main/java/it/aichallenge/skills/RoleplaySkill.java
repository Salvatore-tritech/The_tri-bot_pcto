package it.aichallenge.skills;

import it.aichallenge.client.AIClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RoleplaySkill implements BotSkill {

    private final AIClient ai;
    private String persona = null;
    private final HttpClient http = HttpClient.newHttpClient();

    public RoleplaySkill(AIClient ai) {
        this.ai = ai;
    }

    @Override
    public String tryReply(String userMessage) {
        if (userMessage.startsWith("/act ")) {
            persona = userMessage.substring(5).trim();  // era 9 prima lol :(
            return "Da ora risponderò come se fossi " + persona + ". Chiedimi qualcosa.";
        }

        if (userMessage.equalsIgnoreCase("/stop")) {
            String personaOld = persona;
            persona = null;
            return "Ho smesso di rispondere come " + personaOld + ".";
        }

        if (persona != null) {
            try {
                //  Fetch (cerca info) del contenuto da Wikipedia
                String riassunto = fetchWikipedia(persona);
                String prompt = String.format(
                        "Impersona %s usando queste informazioni da Wikipedia:\n\"%s\"\n\nRispondi in prima persona alla domanda: \"%s\"",
                        persona, riassunto, userMessage
                );
                return ai.chat(prompt);
            } catch (Exception e) {
                return "Errore nella risposta come " + persona + ": " + e.getMessage();
            }
        }

        return null;
    }

    /**
     * Recupera un breve riassunto da Wikipedia in lingua italiana
     */
    private String fetchWikipedia(String persona) throws IOException, InterruptedException {
        String encoded = persona.replace(" ", "_");
        String url = "https://it.wikipedia.org/api/rest_v1/page/summary/" + encoded;

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() == 200) {
            String json = res.body();
            int extractStart = json.indexOf("\"extract\":\"") + 10;
            int extractEnd = json.indexOf("\",", extractStart);
            if (extractStart > 9 && extractEnd > extractStart) {
                return json.substring(extractStart, extractEnd).replace("\\n", " ").replace("\\\"", "\"");
            } else {
                return "Nessun riassunto trovato.";
            }
        } else {
            throw new IOException("Wikipedia non ha trovato " + persona + " (HTTP " + res.statusCode() + ")");
        }
    }
}
