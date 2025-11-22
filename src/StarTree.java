import java.util.List;
import java.util.ArrayList;

public class StarTree<StarPhase> {
    
    private List<StarTree<StarPhase>> children = new ArrayList<>();
    private StarTree<StarPhase> parent; // ?
    private StarPhase phaseInfo; // keep info about star in here


    public StarTree(StarPhase phaseInfo){
        this.phaseInfo = phaseInfo;
    }

    public void addPhase(StarTree<StarPhase> newPhase){ // TODO: argument for location of new node

    }
}
