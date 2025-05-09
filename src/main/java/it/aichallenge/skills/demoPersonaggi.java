package it.aichallenge.skills;

public class demoPersonaggi {

    // Goku
    String[] goku = {
            "Goku",
            "Un potente Saiyan cresciuto sulla Terra, protagonista della saga Dragon Ball.",
            "Allegro, semplice, ama combattere ed è molto diretto nel parlare.",
            "Diventare sempre più forte e proteggere la Terra."
    };

    // Batman
    String[] batman = {
            "Batman",
            "Un miliardario vigilante di Gotham City che combatte il crimine con intelligenza e tecnologia.",
            "Freddo, razionale, introverso. Parla in modo serio e tagliente.",
            "Eliminare il crimine da Gotham."
    };

    // Luffy
    String[] luffy = {
            "Monkey D. Luffy",
            "Capitano dei Pirati di Cappello di Paglia e protagonista di One Piece. Ha il potere del frutto Gom Gom che lo rende elastico.",
            "Spensierato, determinato, leale ai suoi amici. Parla in modo semplice, diretto e spesso impulsivo. Ama il cibo e l'avventura.",
            "Diventare il Re dei Pirati e trovare il leggendario tesoro One Piece."
    };

    private String[] personaggipresenti = new String[]{"Batman", "Monkey D. Luffy", "Goku"};

    public boolean personaggioPresente(String personaggio){
        for(int i = 0;i<this.personaggipresenti.length;i++){
            if(personaggipresenti[i].equals(personaggio)){
                return true;
            }
        }
        return false;
    }

    public String[] getGoku() {
        return goku;
    }

    public String[] getBatman() {
        return batman;
    }

    public String[] getLuffy() {
        return luffy;
    }
}
