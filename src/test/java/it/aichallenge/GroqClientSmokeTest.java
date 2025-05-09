package it.aichallenge;

import it.aichallenge.client.GroqClient;
import it.aichallenge.config.AiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroqClientSmokeTest {

    @Test
    void chat_returnsReply_whenApiKeyPresent() throws Exception {
        boolean hasKey = System.getenv("GROQ_API_KEY") != null &&
                !System.getenv("GROQ_API_KEY").isBlank();
        Assumptions.assumeTrue(hasKey, "No GROQ_API_KEY, skipping smoke test");

        GroqClient client = new GroqClient(AiConfig.loadFromEnv());
        String reply = client.chat("Say 'pong'");
        assertNotNull(reply);
        assertFalse(reply.isBlank());
    }

}