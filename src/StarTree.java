import java.util.List;
import java.util.ArrayList;

public class StarTree<StarPhase> {
    
    private StarPhase phase;

    /**
     * Constructs an empty StarTree
     */
    public StarTree(){
        phase = null;
    }

    /**
     * Constructs a StarTree with a specified root
     * @param phaseInfo
     */
    protected StarTree(StarPhase phase){
        this.phase = phase;
    }

    /*Constructs a new StarTree
     */
    public StarTree(StarInfo data, StarTree<StarPhase> leftTree, StarTree<StarPhase> rightTree){
        StarPhase phase = new StarPhase(data);

        phase.left = leftTree.phase;
        leftTree.phase.parent = phase;
        phase.right = rightTree.phase;
        rightTree.phase.parent = phase;
    }

    // public StarInfo getStarInfo(){
    //     if (phase != null){
    //         return phase.data;
    //     } else {
    //         return null;
    //     }
    // }
}
