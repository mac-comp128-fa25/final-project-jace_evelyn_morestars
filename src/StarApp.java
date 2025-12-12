import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;

public class StarApp {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int NUM_PARTICLES = 1000;

    private CanvasWindow canvas;

    // Background “gas cloud”
    private BackgroundAnimate bgAnimate;

    // UI layer
    private GraphicsGroup uiGroup;
    private Button startButton;
    private Button enterButton;
    private Button nextButton;
    private TextField inputBox;

    //evolution tree
    private StarTree<StarInfo> evolutionTree;
    private String phaseInfo;
    private StarTree<StarInfo> currPhase;
    private int mass;
 

    // state
    private boolean buildingStar = false;
    private final Random rand = new Random();

    public StarApp() {
        canvas = new CanvasWindow("More Stars!", WIDTH, HEIGHT);
        canvas.setBackground(new Color(5, 5, 20));

        evolutionTree = buildEvolutionTree();

        // background animator
        bgAnimate = new BackgroundAnimate();
        canvas.add(bgAnimate.getGroup());

        // UI layer
        uiGroup = new GraphicsGroup();
        canvas.add(uiGroup);

        setupUI();

        // new animator loop
        canvas.animate(dt -> {
            if (!buildingStar) {
                bgAnimate.update(dt);
            }
        });
    }

    /**
     * Sets up UI: title text + Start button.
     */
    private void setupUI() {
        GraphicsText title = new GraphicsText("Cold Gas & Dust Cloud");
        title.setFont(FontStyle.BOLD, 24);
        title.setFillColor(Color.WHITE);
        title.setPosition(20, 40);
        uiGroup.add(title);

        startButton = new Button("Start Star Formation");
        startButton.setPosition(WIDTH / 2.0 - 80, HEIGHT - 70);
        startButton.onClick(this::switchToBuilderMode);
        uiGroup.add(startButton);
    }

    /**
     * Called when the user clicks the Start button.
     * Clears the gas cloud and swaps in a placeholder star-builder UI.
     */
    private void switchToBuilderMode() {
        buildingStar = true;

        canvas.remove(bgAnimate.getGroup());   // removes cloud
        uiGroup.removeAll();

        phaseInfo = evolutionTree.phase.getStarInfo();

        GraphicsGroup protostar = new GraphicsGroup();
        Ellipse core = new Ellipse(280, 280, 40, 40);
        core.setFillColor(new Color(255, 200, 80)); // warm yellow glow
        core.setStroked(false);

        GraphicsText label = new GraphicsText(phaseInfo);
        label.setFillColor(Color.WHITE);
        label.setFont(FontStyle.BOLD, 18);
        label.setCenter(300, 370);

        protostar.add(core);
        protostar.add(label);

        canvas.add(protostar);

        GraphicsText title = new GraphicsText("Star Builder");
        title.setFont(FontStyle.BOLD, 26);
        title.setFillColor(Color.WHITE);
        title.setPosition(20, 50);
        uiGroup.add(title);

        GraphicsText instructions = new GraphicsText(
                "Please enter a mass\n" +
                "between 0 and 300.");
        instructions.setFont(FontStyle.PLAIN, 16);
        instructions.setFillColor(new Color(220, 220, 240));
        instructions.setPosition(20, 90);
        instructions.setWrappingWidth(560);
        uiGroup.add(instructions);

        inputBox = new TextField();
        inputBox.setPosition(WIDTH/2.0 - 55, HEIGHT - 125);
        uiGroup.add(inputBox);

        enterButton = new Button("Enter");
        enterButton.setPosition(WIDTH / 2.0 - 35, HEIGHT - 100);
        mass = Integer.parseInt(inputBox.getText()); // TODO: make this happen on enter hit
        uiGroup.add(enterButton);
    }

    /* Builds evolution tree with star phase information
     */
    private StarTree<StarInfo> buildEvolutionTree(){ 
       
        //star phases
        StarInfo protostar1 = new StarInfo("Protostar", 0, 300);
        StarInfo lowMassStar = new StarInfo("Low Mass Star", 0, 8);
        StarInfo whiteDwarf1 = new StarInfo("White Dwarf Star", 0, 1);
        StarInfo blackDwarf1 = new StarInfo("Black Dwarf Star", 0, 1);
        StarInfo subgiantStar = new StarInfo("Subgiant Star", 2, 12);
        StarInfo degenerateStar = new StarInfo("Degenerate Core Star", 2, 3);
        StarInfo redGiant = new StarInfo("Red Giant Star", 3, 12);
        StarInfo planetaryNebula1 = new StarInfo("Planetary Nebula", 2, 3);
        StarInfo whiteDwarf2 = new StarInfo("White Dwarf Star", 2, 3);
        StarInfo blackDwarf2 = new StarInfo("Black Dwarf Star", 2, 3);
        StarInfo planetaryNebula2 = new StarInfo("Planetary Nebula", 3, 12);
        StarInfo whiteDwarf3 = new StarInfo("White Dwarf Star", 3, 12);
        StarInfo blackDwarf3 = new StarInfo("Black Dwarf Star", 3, 12);

        StarInfo highMassStar = new StarInfo("High Mass Star", 9, 300);

        StarTree<StarInfo> starTree = new StarTree<StarInfo>(protostar1, 
            new StarTree<StarInfo>(lowMassStar, 
                new StarTree<StarInfo> (whiteDwarf1, 
                    new StarTree<StarInfo> (blackDwarf1, null, null),
                    null),
                new StarTree<StarInfo> (subgiantStar, 
                    new StarTree<StarInfo> (degenerateStar, 
                        new StarTree<StarInfo> (planetaryNebula1, 
                            new StarTree<StarInfo> (whiteDwarf2, 
                                new StarTree<StarInfo> (blackDwarf2, null, null), 
                                null),
                            null),
                        null),
                    new StarTree<StarInfo> (redGiant, 
                        new StarTree<StarInfo> (planetaryNebula2, 
                            new StarTree<StarInfo> (whiteDwarf3, 
                                new StarTree<StarInfo> (blackDwarf3, null, null),
                                null),
                            null),
                        null))),
            new StarTree<StarInfo>(highMassStar, null, null)
        );

        return starTree;
    } 

    public static void main(String[] args) {
        new StarApp();
    }
}
