package it.aichallenge.skills;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SkillTimeEndPoint
{
    private static void main(String[] args) throws IOException
    {
        int port = 8080;
        var server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", SkillTimeEndPoint::handler);
        server.setExecutor(null);
        server.start();
        System.out.println("HTTP server started on port "+port);
    }

    private static void handler(HttpExchange httpExchange) throws IOException {
        if (!"GET".equals(httpExchange.getRequestMethod())) {
            httpExchange.sendResponseHeaders(405, -1);
            return;
        }
        String response = "Ora attuale: ";
        byte[] bytes = response.getBytes();
        httpExchange.sendResponseHeaders(200,bytes.length);
        OutputStream os = (httpExchange.getResponseBody());
        os.write(bytes);
        httpExchange.close();
    }
}
