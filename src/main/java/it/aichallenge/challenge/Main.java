package it.aichallenge.challenge;

import it.aichallenge.bot.Bot;
import it.aichallenge.bot.SimpleBot;
import it.aichallenge.bot.SkillfulBot;
import it.aichallenge.client.GroqClient;
import it.aichallenge.config.AiConfig;
import it.aichallenge.skills.ActorSkill;
import it.aichallenge.skills.GetTime;
import it.aichallenge.skills.SkillRegistry;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/** Classe main che lancia un hello world al modello groq */
public class Main {

    public static void main(String[] args) throws Exception {
        //var bot = new SimpleBot(new GroqClient(AiConfig.loadFromEnv()));
        SkillRegistry registry = new SkillRegistry()
                .add(new GetTime())
                .add(new ActorSkill());

        Bot bot = new SkillfulBot(registry, new GroqClient(AiConfig.loadFromEnv()));
        System.out.println("AI Challenge Bot – type something (Ctrl‑D to exit)");
        try (var in = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = in.readLine()) != null) {
                String reply = bot.reply(line);
                System.out.println("🤖 " + reply);
            }
        }
    }

}