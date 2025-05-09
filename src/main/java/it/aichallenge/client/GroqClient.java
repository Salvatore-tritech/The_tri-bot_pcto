package it.aichallenge.client;

import it.aichallenge.config.AiConfig;
import it.aichallenge.client.AIClient;
import okhttp3.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Client per le API Groq Cloud (compatibile OpenAI)
 */
public class GroqClient implements AIClient {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient http = new OkHttpClient.Builder()
            .callTimeout(Duration.ofSeconds(60))
            .build();

    /** ObjectMapper configurato per ignorare i campi sconosciuti */
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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
            return chat.choices.get(0).message.content;
        }
    }

    /* ---------- DTOs (package-private) ---------- */

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ChatResponse {
        public List<Choice> choices;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Choice {
        public Message message;

        @JsonProperty("finish_reason")
        public String finishReason;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Message {
        public String role;
        public String content;
    }
}
