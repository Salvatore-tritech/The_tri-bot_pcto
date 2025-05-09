package it.aichallenge.challenge;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpsServer;
import it.aichallenge.bot.SimpleBot;
import it.aichallenge.bot.SkillfulBot;
import it.aichallenge.client.GroqClient;
import it.aichallenge.config.AiConfig;
import it.aichallenge.skills.IpSkill;
import it.aichallenge.skills.SkillRegistry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

/** Classe main che lancia un hello world al modello groq */
public class Main {

    public static void main(String[] args) throws Exception {
        SkillRegistry registry = new SkillRegistry()
                .add(new IpSkill());

        SkillfulBot bot = new SkillfulBot(registry, new GroqClient(AiConfig.loadFromEnv()));

        System.out.println("AI Challenge Bot – type something (Ctrl‑D to exit)");
        try (var in = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = in.readLine()) != null) {
                String reply = bot.reply(line);
                System.out.println("🤖 " + reply);
            }
        }

        int port = 8080;
        var server = HttpsServer.create(new InetSocketAddress(port), 0);


        server.setExecutor(null);
        server.start();
        System.out.println("HTTP server started on port " + port);



    }

    private static void headler (HttpExchange ip) throws Exception{
        if("GET".equals(ip.getRequestMethod())){

        }
    }

}