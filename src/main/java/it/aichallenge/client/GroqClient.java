package it.aichallenge.client;

import it.aichallenge.config.AiConfig;
import it.aichallenge.client.AIClient;
import okhttp3.*;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
    Classe per la configurazione di connessione con groq e la gestione delle chiamate.
 */
public class GroqClient implements AIClient {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient http = new OkHttpClient.Builder()
            .callTimeout(Duration.ofSeconds(60))
            .build();

    private final ObjectMapper mapper = new ObjectMapper();
    private final String url;
    private final String apiKey;
    private final String model;

    public GroqClient(AiConfig cfg) {
        this.url = cfg.baseUrl() + "/chat/completions";
        this.apiKey = cfg.apiKey();
        this.model = cfg.model();
    }

    @Override
    public String chat(String prompt) throws IOException {
        Map<String, Object> body = Map.of(
                "model", model,
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );
        Request req = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + apiKey)
                .post(RequestBody.create(mapper.writeValueAsBytes(body), JSON))
                .build();

        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                assert res.body() != null;
                throw new IOException("Errore API Groq: HTTP " + res.code() + " – " + res.body().string());
            }
            assert res.body() != null;
            ChatResponse chat = mapper.readValue(res.body().byteStream(), ChatResponse.class);
            return chat.choices[0].message.content;
        }

    }

    /* ---------- DTOs (package‑private) ---------- */
    static class ChatResponse {
        public Choice[] choices;
    }
    static class Choice {
        public Message message;
    }
    static class Message {
        public String role;
        public String content;
    }
}