package it.aichallenge.skills;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalTime;

public class GetTime {

    public static void time(HttpExchange httpServer)throws IOException {


        if ("GET".equals(httpServer.getRequestMethod())) {

            LocalTime ora = LocalTime.now();
            String tempo = String.valueOf(ora);

            byte[] response = tempo.getBytes();
            httpServer.sendResponseHeaders(200, response.length);

            byte[] bytes =tempo.getBytes();

            OutputStream as = httpServer.getResponseBody();
            as.write(bytes);
            httpServer.close();


        }
    }


    }