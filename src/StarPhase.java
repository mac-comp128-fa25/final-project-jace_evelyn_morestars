
public class StarPhase {

    public String phaseName;

    private int temperature;
    private int solarMassMin; // in solar masses - to pick this child, must be as big or bigger
    private int solarMassMax;
    private int luminosity;

    //TODO: add UI details (color, size, animation)
    // possibly other class to hold animation and style info?

    public StarPhase(String phaseName, int temperature, int solarMassMin, int solarMassMax, int luminosity){
        this.phaseName = phaseName;
        this.temperature = temperature;
        this.solarMassMin = solarMassMin;
        this.solarMassMax = solarMassMax;
        this.luminosity = luminosity;
    }

    public String getStarInfo(){
        return "This is a " + phaseName + "star!\nTemperature: "
            + temperature + "\nLuminosity: " + luminosity;
    }

}
