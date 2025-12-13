public class StarInfo {

    String phaseName;
    int minMass;
    int maxMass;

    public StarInfo(String phaseName, int minMass, int maxMass) {
        this.phaseName = phaseName;
        this.minMass = minMass;
        this.maxMass = maxMass;
    }

    public String getStarInfo() {
        return "This is a " + phaseName + "!";
    }
}