package it.aichallenge.challenge;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import it.aichallenge.skills.SkillTime;
import it.aichallenge.skills.SkillsIpfy;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalTime;

public class ServerHttp
{
    public static void server() throws IOException
    {
        int port = 8080;
        var server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", ServerHttp::handler);
        server.createContext("/", ServerHttp::handleripfy);
        server.setExecutor(null);
        server.start();
        System.out.println("HTTP server started on port "+port);
    }

    private static void handler(HttpExchange htE) throws IOException {
        if (!"GET".equals(htE.getRequestMethod())) {
            LocalTime ora = LocalTime.now();
            String tempo = String.valueOf(ora);

            byte[] response = tempo.getBytes();
            htE.sendResponseHeaders(200, response.length);

            byte[] bytes =tempo.getBytes();

            OutputStream as = htE.getResponseBody();
            as.write(bytes);
            htE.close();
        }
    }

    private static void handleripfy(HttpExchange htip) throws IOException {
        if (!"GET".equals(htip.getRequestMethod())) {
            SkillsIpfy ipfy = new SkillsIpfy();
            String response = ipfy.tryReply("Indirizzo IP");
            if (response == null) {
                response = "Impossibile recuperare l'indirizzo IP.";
            }

            byte[] bytes = response.getBytes();
            htip.sendResponseHeaders(200, bytes.length);
            OutputStream os = htip.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
}
