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

    private String test;

    public Players(){
    }


    public Players(String pname, String pclub, String position) {
        this.pname = pname;
        this.pclub = pclub;
        this.position = position;
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

    ///////////////////////////////
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
    //////////////////////////////////

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    public void setSprintspeed(int sprintspeed) {
        this.sprintspeed = sprintspeed;
    }

    public void setPositioning(int positioning) {
        this.positioning = positioning;
    }

    public void setFinishing(int finishing) {
        this.finishing = finishing;
    }

    public void setShotpower(int shotpower) {
        this.shotpower = shotpower;
    }

    public void setLongshot(int longshot) {
        this.longshot = longshot;
    }

    public void setVolleys(int volleys) {
        this.volleys = volleys;
    }

    public void setPenalties(int penalties) {
        this.penalties = penalties;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public void setCrossing(int crossing) {
        this.crossing = crossing;
    }

    public void setFreekick(int freekick) {
        this.freekick = freekick;
    }

    public void setShortpassing(int shortpassing) {
        this.shortpassing = shortpassing;
    }

    public void setLongpassing(int longpassing) {
        this.longpassing = longpassing;
    }

    public void setCurve(int curve) {
        this.curve = curve;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setReactions(int reactions) {
        this.reactions = reactions;
    }

    public void setBallcontrol(int ballcontrol) {
        this.ballcontrol = ballcontrol;
    }

    public void setDribbling(int dribbling) {
        this.dribbling = dribbling;
    }

    public void setInterceptions(int interceptions) {
        this.interceptions = interceptions;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public void setMarking(int marking) {
        this.marking = marking;
    }

    public void setStandtackle(int standtackle) {
        this.standtackle = standtackle;
    }

    public void setSlidingtackle(int slidingtackle) {
        this.slidingtackle = slidingtackle;
    }

    public void setJumping(int jumping) {
        this.jumping = jumping;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setAggression(int aggression) {
        this.aggression = aggression;
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
