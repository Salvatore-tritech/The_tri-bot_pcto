package it.aichallenge.challenge;

import com.sun.net.httpserver.HttpServer;
import it.aichallenge.skills.GetRisposta;
import it.aichallenge.skills.GetTime;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Serverhttp {
    public static void start() throws IOException {
        int port = 8080;
        var server = HttpServer.create(new InetSocketAddress(port), 0);


        server.createContext("/time", GetTime::time);
        server.createContext("/ip", GetRisposta::handle);
        server.setExecutor(null);
        server.start();

        System.out.println("HTTP sever started on port "+port);
    }
}
