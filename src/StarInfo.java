public class StarInfo {

    private String phaseName;
    private int temperature;
    private int luminosity;
    private int minMass;
    private int maxMass;

    public StarInfo(String phaseName, int temperature, int luminosity, int minMass, int maxMass){
        this.phaseName = phaseName;
        this.temperature = temperature;
        this.luminosity = luminosity;
        this.minMass = minMass;
        this.maxMass = maxMass;
    }

    public String getStarInfo(){
        return "This is a " + phaseName + "star!\nTemperature: "
            + temperature + "\nLuminosity: " + luminosity;
    }

}
