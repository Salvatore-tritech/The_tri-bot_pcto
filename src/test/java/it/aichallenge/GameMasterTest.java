package it.aichallenge;

import it.aichallenge.game.GameMaster;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameMasterTest {

    private GameMaster gm;

    @BeforeEach
    void setup() throws Exception {
        Files.deleteIfExists(Paths.get("leaderboard.json")); // fresh start
        gm = new GameMaster();
    }

    @Test
    void addScore_persistsAndSorts() {
        gm.addScore("alice", 5);
        gm.addScore("bob", 7);
        gm.addScore("alice", 3); // alice total 8

        List<Map.Entry<String, Integer>> top2 = gm.top(2);
        assertEquals("alice", top2.get(0).getKey());
        assertEquals(8, top2.get(0).getValue());
        assertEquals("bob", top2.get(1).getKey());
    }

}