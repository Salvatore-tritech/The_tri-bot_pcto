package it.aichallenge.skills;

public class SkillPersonaggio implements BotSkill
{
    @Override
    public String tryReply(String userMessage)
    {
        if(userMessage==null) return null;
        String msg=userMessage.trim().toLowerCase();

        if(msg.equals("/dexter")){
            PersonalityManager.setPersonality("dexter");
            return " \uD83E\uDE78 Modalità Dexter Morgana attivata. Attento a cosa chiedi...";
        }
        return null;
    }
}

