package it.aichallenge.challenge;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import it.aichallenge.bot.SimpleBot;
import it.aichallenge.bot.SkillfulBot;
import it.aichallenge.client.GroqClient;
import it.aichallenge.config.AiConfig;
import it.aichallenge.skills.IpifySkill;
import it.aichallenge.skills.SkillRegistry;
import it.aichallenge.skills.SkillTime;
import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/** Classe main che lancia un hello world al modello groq */
public class Main {

    public static void main(String[] args) throws Exception {
        //var bot = new SimpleBot(new GroqClient(AiConfig.loadFromEnv()));
        int port = 8080;
        var server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/ipify/", Main::handler_ipify);
        server.setExecutor(null);
        server.start();
        SkillRegistry skillRegistry = new SkillRegistry();
        skillRegistry.add(new SkillTime());
        skillRegistry.add(new IpifySkill());
        var bot = new SkillfulBot(skillRegistry, new GroqClient(AiConfig.loadFromEnv()));

        System.out.println("AI Challenge Bot – type something (Ctrl‑D to exit)");
        try (var in = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = in.readLine()) != null) {
                String reply = bot.reply(line);
                System.out.println("🤖 " + reply);
            }
        }
    }

    private static void handler_ipify(HttpExchange httpExchange) throws IOException {
        if (!httpExchange.getRequestMethod().equals("GET")) {
            httpExchange.sendResponseHeaders(405, -1);
            return;
        }
            String response = new IpifySkill().tryReply("ipify");

            byte[] bytes = response.getBytes();

            httpExchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(bytes);
            httpExchange.close();
    }
}