package it.aichallenge.skills;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalTime;


public class GetTime implements BotSkill{

    public static void getTime() throws IOException {
        int port = 8080;
        var server = HttpServer.create(new InetSocketAddress((port)), 0);
        server.createContext("/time", GetTime::Handler);
    }

    private static void Handler(HttpExchange httpExchange) throws IOException {


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

    @Override
    public String tryReply(String userMessage) {
        LocalTime myObj = LocalTime.now();
        String response = "Ciao sono le ore: " + myObj.toString();
        return response;
    }
}
