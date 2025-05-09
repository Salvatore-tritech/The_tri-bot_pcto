package it.aichallenge.challenge;

import it.aichallenge.bot.SkillfulBot;
import it.aichallenge.client.GroqClient;
import it.aichallenge.config.AiConfig;
import it.aichallenge.skills.SkillRegistry;
import it.aichallenge.skills.SkillRihanna;
import it.aichallenge.skills.SkillTime;
import it.aichallenge.skills.SkillsIpfy;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/** Classe main che lancia un hello world al modello groq */
public class Main {

    public static void main(String[] args) throws Exception {
        ServerHttp.server();
        SkillRegistry skillRegistry = new SkillRegistry();
        skillRegistry.add(new SkillTime());
        skillRegistry.add(new SkillsIpfy());
        skillRegistry.add(new SkillRihanna());

        var botst = new GroqClient(AiConfig.loadFromEnv());

        var botint = new SkillfulBot(skillRegistry, botst);

        System.out.println("AI Challenge Bot – type something (Ctrl‑D to exit)");
        try (var in = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = in.readLine()) != null) {
                String reply = botint.reply(line);
                System.out.println("🤖 " + reply);
            }
        }
    }

}