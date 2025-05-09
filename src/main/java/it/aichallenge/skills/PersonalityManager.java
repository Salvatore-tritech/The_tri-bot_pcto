package it.aichallenge.skills;

public class PersonalityManager {
    private static String activePersonality=null;

    public static void setPersonality(String name){
        activePersonality=name;
    }

    public static String getActivePersonality(){
        return activePersonality;
    }

    public static boolean isDexterMode(){
        return "Dexter".equalsIgnoreCase(activePersonality);
    }
}
