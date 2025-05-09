package it.aichallenge.skills;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import it.aichallenge.bot.Bot;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;

public class SkillIpify implements BotSkill {

    public void avviaCose() throws IOException {
        int port = 8080;
        var server = HttpServer.create(new InetSocketAddress((port)), 0);
        server.createContext("/time", SkillIpify::HandlerIP);
        server.start();
        System.out.println("Richiesta in elaborazione...");
    }

    private static void HandlerIP(HttpExchange httpExchange) throws IOException {


        String response = "";

        if("GET".equals(httpExchange.getRequestMethod())){
            LocalTime myObj = LocalTime.now();
            response = myObj.toString();
        }else{
            httpExchange.sendResponseHeaders(405, -1);
            return;
        }
        byte[] b = response.getBytes();

        httpExchange.sendResponseHeaders(200, b.length);

        OutputStream os = httpExchange.getResponseBody();
        os.write(b);
        httpExchange.close();
    }

    public String rispondi() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.ipify.org?format=json"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String[] ris = response.body().split(":");
        String r = ris[1];
        String[] r2 = r.split("}");
        String risposta = "Il tuo indirizzo IP preso da ipify è: " + r2[0] + "sei contento? (Ps prossima volta fai da solo, non tutti hanno tempo libero come te nullafacente)";
        return risposta;
    }

    @Override
    public String tryReply(String userMessage) {
        if(userMessage.equals("Evoco la carta magia ipify")){
            try {
                return rispondi();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else{ return null;}

    }
}
