package it.aichallenge.skills;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;


public class GetTime implements BotSkill{

    //commento

    public void getTime() throws IOException {
        int port = 8080;
        var server = HttpServer.create(new InetSocketAddress((port)), 0);
        server.createContext("/time", GetTime::Handler);
        server.start();
        System.out.println("Richiesta in elaborazione...");
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

    public String richiediTime() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/time"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }


    @Override
    public String tryReply(String userMessage) {
        if(!userMessage.equals("che ore sono?")){
            return null;
        }
        LocalTime myObj = LocalTime.now();
        String response = "Ciao sono le ore: " + myObj.toString();
        return response;
    }
}
