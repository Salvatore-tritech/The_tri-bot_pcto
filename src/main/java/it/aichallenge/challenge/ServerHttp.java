package it.aichallenge.challenge;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import it.aichallenge.skills.SkillTime;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ServerHttp
{
    public static void server() throws IOException
    {
        int port = 8080;
        var server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", ServerHttp::handler);
        server.setExecutor(null);
        server.start();
        System.out.println("HTTP server started on port "+port);
    }

    private static void handler(HttpExchange httpExchange) throws IOException {
        if (!"GET".equals(httpExchange.getRequestMethod())) {
            httpExchange.sendResponseHeaders(405, -1);
            return;
        }
        SkillTime skill = new SkillTime();
        String response = skill.tryReply("Che ora è?");
        if (response == null)
        {
            response = "Nessuna risposta disponibile.";
        }
        byte[] bytes = response.getBytes();
        httpExchange.sendResponseHeaders(200,bytes.length);
        OutputStream os = (httpExchange.getResponseBody());
        os.write(bytes);
        os.close();
    }
}
