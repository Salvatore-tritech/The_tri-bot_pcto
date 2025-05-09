package it.aichallenge.skills;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SkillTime implements BotSkill{

   @Override
   public String tryReply(String userMessage){
       if (userMessage == null)return null;
       String lower =userMessage.toLowerCase();
       if(lower.contains("che ore sono")||lower.contains("orario")){
           LocalTime now =LocalTime.now();
           DateTimeFormatter formatter= DateTimeFormatter.ofPattern("HH:mm:ss");

           return " sono le "+ now.format(formatter);
       }
        return null;
   }

}

