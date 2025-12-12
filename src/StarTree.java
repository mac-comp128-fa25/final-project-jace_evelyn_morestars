public class StarTree<StarPhase> {
    
    private StarPhase phase;

    protected static class StarPhase {

        public StarInfo data;

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

    /**
     * Constructs an empty StarTree
     */
    public StarTree(){
        phase = null;
    }

    /*Constructs a new StarTree
     */
    public StarTree(StarInfo data, StarTree<StarInfo> leftTree, StarTree<StarInfo> rightTree){
        phase = new StarPhase(data);

        if (leftTree != null){
            phase.left = leftTree.phase;
            leftTree.phase.parent = phase;
        } else {
            phase.left = null;
        }

        if (rightTree != null){
            phase.right = rightTree.phase;
            rightTree.phase.parent = phase;
        } else {
            phase.right = null;
        }
        
        
    }

    public StarInfo getStarInfo(){
        return phase.data;
    }
}
