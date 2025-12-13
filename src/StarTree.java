public class StarTree {

    public StarPhase phase;

    public static class StarPhase {
        StarInfo data;
        StarPhase left;
        StarPhase right;

        public StarPhase(StarInfo data) {
            this.data = data;
        }

        public String getStarInfo() {
            return data.getStarInfo();
        }
    }

    public StarTree(StarInfo data, StarTree leftTree, StarTree rightTree) {
        phase = new StarPhase(data);

        if (leftTree != null) {
            phase.left = leftTree.phase;
        }

        if (rightTree != null) {
            phase.right = rightTree.phase;
        }
    }
}