package it.aichallenge.skills;


import java.time.LocalTime;

public class GetTime implements BotSkill {


    @Override
    public String tryReply(String userMessage) {

        String reply;

        if(userMessage.contains("ore sono") || userMessage.contains("orario"))
        {
            reply="Sono le "+getTime();
        }
        if(userMessage.contains("time is")) reply="The time is "+getTime();


        return "";
    }

    public String getTime()
    {
        String orario="";
        LocalTime time=LocalTime.now();
        orario=""+time;
        String[] orarioGiusto=orario.split(".");
        orario=orarioGiusto[0];


        return orario;

    }
}
