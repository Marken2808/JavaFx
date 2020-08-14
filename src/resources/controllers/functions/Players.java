package resources.controllers.functions;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Players extends RecursiveTreeObject<Players> {


    private String pname;
    private String pclub;
    private String position;
    private int statPace;
    private int acceleration;
    private int sprintspeed;
    private int statShooting;
    private int positioning;
    private int finishing;
    private int shotpower;
    private int longshot;
    private int volleys;
    private int penalties;
    private int statPassing;
    private int vision;
    private int crossing;
    private int freekick;
    private int shortpassing;
    private int longpassing;
    private int curve;
    private int statAgility;
    private int agility;
    private int balance;
    private int reactions;
    private int ballcontrol;
    private int dribbling;
    private int statDefending;
    private int interceptions;
    private int heading;
    private int marking;
    private int standtackle;
    private int slidingtackle;
    private int statPhysical;
    private int jumping;
    private int strength;
    private int aggression;

    public Players(){}



    public Players(
            String pname, String pclub, String position, int acceleration,
            int sprintspeed, int positioning, int finishing, int shotpower,
            int longshot, int volleys, int penalties, int vision, int crossing,
            int freekick, int shortpassing, int longpassing, int curve,
            int agility, int balance, int reactions, int ballcontrol, int dribbling,
            int interceptions, int heading, int marking, int standtackle,
            int slidingtackle, int jumping, int strength, int aggression
    ) {
        this.pname = pname;
        this.pclub = pclub;
        this.position = position;
        this.acceleration = acceleration;
        this.sprintspeed = sprintspeed;
        this.positioning = positioning;
        this.finishing = finishing;
        this.shotpower = shotpower;
        this.longshot = longshot;
        this.volleys = volleys;
        this.penalties = penalties;
        this.vision = vision;
        this.crossing = crossing;
        this.freekick = freekick;
        this.shortpassing = shortpassing;
        this.longpassing = longpassing;
        this.curve = curve;
        this.agility = agility;
        this.balance = balance;
        this.reactions = reactions;
        this.ballcontrol = ballcontrol;
        this.dribbling = dribbling;
        this.interceptions = interceptions;
        this.heading = heading;
        this.marking = marking;
        this.standtackle = standtackle;
        this.slidingtackle = slidingtackle;
        this.jumping = jumping;
        this.strength = strength;
        this.aggression = aggression;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPclub() {
        return pclub;
    }

    public void setPclub(String pclub) {
        this.pclub = pclub;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getStatPace() {
        return (acceleration+sprintspeed)/2;
    }

    public int getStatShooting() {
        return (positioning+finishing+shotpower+longshot+volleys+penalties)/6;
    }

    public int getStatPassing() {
        return (vision+crossing+freekick+shortpassing+longpassing+curve)/6;
    }

    public int getStatAgility() {
        return (agility+balance+reactions+ballcontrol+dribbling)/5;
    }

    public int getStatDefending() {
        return (interceptions+heading+marking+standtackle+slidingtackle)/5;
    }

    public int getStatPhysical() {
        return (jumping+strength+aggression)/3;
    }

//    public static void checkProfileName (Label typeUser){
//        typeUser.setText("@"+checkCurrentUser().get(3));
//    }
//
//    public static void checkPassword (Label typePass){
//        typePass.setText(checkCurrentUser().get(2));
//    }
//
//    public static String checkRole (){
//        return checkCurrentUser().get(0);
//    }
//
//    public static ArrayList<String> checkCurrentUser() {
//        return SignInController.getInstance().createConnectionWithRole();
//    }
}
