package it.aichallenge.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mini classifica locale salvata su file JSON.
 * <p>
 * ➡ Pensata per l’uso da riga di comando, quindi NON thread‑safe.
 */
public class GameMaster {

    private static final Path FILE = Paths.get("leaderboard.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** mappa: giocatore → punteggio */
    private final Map<String, Integer> scores;

    public GameMaster() {
        scores = load();
    }

    /* ---------- API pubblica ---------- */

    /** Aggiunge (o sottrae) punti a un giocatore. */
    public void addScore(String player, int delta) {
        scores.merge(player, delta, Integer::sum);
        persist();
    }

    /** Top‑N ordinato per punteggio desc, poi per nome asc. */
    public List<Map.Entry<String, Integer>> top(int n) {
        return scores.entrySet().stream()
                .sorted(Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                        .reversed()
                        .thenComparing(Map.Entry::getKey))
                .limit(n)
                .collect(Collectors.toList());
    }

    /** Ritorna un’istantanea immutabile di tutta la classifica. */
    public Map<String, Integer> snapshot() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(scores));
    }

    /* ---------- persistenza ---------- */

    /** Legge (se esiste) il file JSON e ricostruisce la mappa punteggi. */
    private Map<String, Integer> load() {
        if (Files.isRegularFile(FILE)) {
            try {
                return MAPPER.readValue(
                        Files.newBufferedReader(FILE),
                        new TypeReference<LinkedHashMap<String, Integer>>() {});
            } catch (IOException e) {
                System.err.println("[GameMaster] Errore di lettura " + FILE +
                        " – si riparte da zero: " + e);
            }
        }
        return new LinkedHashMap<>();
    }

    /** Scrive la mappa su disco in formato “pretty” JSON. */
    private void persist() {
        try {
            MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValue(Files.newBufferedWriter(FILE), scores);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile salvare la leaderboard", e);
        }
    }
}
