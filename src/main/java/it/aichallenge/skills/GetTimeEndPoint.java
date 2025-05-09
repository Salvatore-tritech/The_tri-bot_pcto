package it.aichallenge.skills;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalTime;

public class GetTimeEndPoint
{
    public static void time(HttpExchange htE) throws IOException{

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
}
