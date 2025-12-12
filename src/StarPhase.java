
public class StarPhase{

    private StarInfo data;

    private StarPhase left;
    private StarPhase right;
    private StarPhase parent;

    public StarPhase(StarInfo data){
        this.data = data;
        left = null;
        right = null;
        parent = null;
    }

    public String getStarInfo(){
        return data.getStarInfo();
    }

}
