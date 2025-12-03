import java.util.List;
import java.util.ArrayList;

public class StarTree<StarPhase> {
    
    private List<StarTree<StarPhase>> children = new ArrayList<>();
    private StarPhase parent;
    private StarPhase phaseInfo;


    public StarTree(StarPhase phaseInfo){
        this.phaseInfo = phaseInfo;
    }

    public void addPhase(StarTree<StarPhase> newPhase){
        newPhase.setParent(this);
        children.add(newPhase);
    }

    public void setParent(StarTree<StarPhase> parent){
        
    }

    public StarPhase getParent(){
        return parent;
    }
}
