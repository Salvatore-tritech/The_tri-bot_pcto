package it.aichallenge.skills;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpsServer;
import it.aichallenge.bot.SimpleBot;
import it.aichallenge.client.GroqClient;
import it.aichallenge.config.AiConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class GetTime implements BotSkill{

    public static void main(String[] args) throws Exception {

        var bot = new SimpleBot(new GroqClient(AiConfig.loadFromEnv()));
        SkillRegistry registry = new SkillRegistry();
        String stirnga;
        try (var in = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] linea = line.split(" ");
                for(String s : linea){
                    if(s.equals("che") || s.equals("ore") || s.equals("sono")){

                    }
                }
            }
        }

        int port = 8080;
        var server = HttpsServer.create(new InetSocketAddress(port), 0);
        server.createContext("/time", GetTime::handler);

        server.setExecutor(null);
        server.start();



    }




    private static void handler(HttpExchange cipolla) throws IOException {
        if ("GET".equals(cipolla.getRequestMethod())) {
            String response = "ciao sono una GET";

            byte[] bytes = response.getBytes();
            cipolla.sendResponseHeaders(200, bytes.length);
            OutputStream os = cipolla.getResponseBody();
            os.write(bytes);
            cipolla.close();
        }
        else{
            cipolla.sendResponseHeaders(405,-1);
        }
    }

}
