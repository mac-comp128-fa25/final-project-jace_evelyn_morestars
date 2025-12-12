public class StarInfo {

    private String phaseName;
    private int minMass;
    private int maxMass;

    public StarInfo(String phaseName, int minMass, int maxMass){
        this.phaseName = phaseName;
        // this.temperature = temperature;
        // this.luminosity = luminosity;
        this.minMass = minMass;
        this.maxMass = maxMass;
    }

    public String getStarInfo(){
        return "This is a " + phaseName + "!";
    }

}
