package it.aichallenge.config;

import java.util.Optional;

/**
    Piccolo modello per la gestione delle configurazioni dal file di enviroment (.env)
 */
public record AiConfig(String apiKey,
                       String model,
                       String baseUrl) {

    public static final String DEFAULT_BASE_URL = "https://api.groq.com/openai/v1";

    public static AiConfig loadFromEnv() {
        String key = getenv("GROQ_API_KEY");
        String model = getenv("GROQ_MODEL");
        String url = Optional.ofNullable(System.getenv("GROQ_BASE_URL"))
                .filter(s -> !s.isBlank())
                .orElse(DEFAULT_BASE_URL);
        return new AiConfig(key, model, url);
    }

    private static String getenv(String name) {
        String v = System.getenv(name);
        if (v == null || v.isBlank()) {
            throw new IllegalStateException("La variabile " + name + " non e' presente nel file enviroment");
        }
        return v;
    }
}