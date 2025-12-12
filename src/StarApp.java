import java.awt.Color;
import java.awt.Paint;
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
    private Button nextButton;
    private Button backButton;
    private TextField inputBox;

    //evolution tree
    private StarTree evolutionTree;

    // state
    private boolean buildingStar = false;
    private final Random rand = new Random();

    // action stack
    private Deque<String> actionStack = new ArrayDeque(); // tracks previous actions, so it can be undone later

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
        GraphicsText title = new GraphicsText("Cold gas & dust cloud");
        title.setFont(FontStyle.BOLD, 24);
        title.setFillColor(Color.WHITE);
        title.setPosition(20, 40);
        uiGroup.add(title);

        startButton = new Button("Start Star Formation");
        startButton.setPosition(WIDTH / 2.0 - 80, HEIGHT - 70);
        startButton.onClick(this::switchToBuilderMode);
        uiGroup.add(startButton);

        nextButton = new Button("Next Phase");
        nextButton.setPosition(WIDTH / 2.0 - 60, HEIGHT - 100);
        // nextButton.onClick((event) -> {
            // go to next
            // push new phase to action stack
        // });
        uiGroup.add(nextButton);

        backButton = new Button("Previous Phase");
        backButton.setPosition(WIDTH / 2.0 - 70, HEIGHT - 70);
        // backButton.onClick(pop from top of action stack, display it);
        uiGroup.add(backButton);
    }

    /**
     * Called when the user clicks the Start button.
     * Clears the gas cloud and swaps in a placeholder star-builder UI.
     */
    private void switchToBuilderMode() {
        buildingStar = true;

        canvas.remove(bgAnimate.getGroup());   // removes cloud
        uiGroup.removeAll();

        GraphicsGroup protostar = new GraphicsGroup();
        Ellipse core = new Ellipse(280, 280, 40, 40);
        core.setFillColor(new Color(255, 200, 80)); // warm yellow glow
        core.setStroked(false);

        GraphicsText label = new GraphicsText("Protostar");
        label.setFillColor(Color.WHITE);
        label.setFont(FontStyle.BOLD, 18);
        label.setCenter(300, 350);

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
        inputBox.setPosition(WIDTH/2.0 - 50, HEIGHT - 120);
        uiGroup.add(inputBox);
    }

    /* Builds evolution tree with star phase information
     */
    private StarTree buildEvolutionTree(){ 
       
        //star phases
        StarInfo gasCloud = new StarInfo("Gas Cloud", 0, 1000); //TODO: find bounds of mass
        StarInfo lowMassStar = new StarInfo("Low Mass Star", 0, 8);
        StarInfo highMassStar = new StarInfo("High Mass Star", 9, 1000);
        StarInfo whiteDwarf1 = new StarInfo("White Dwarf Star", 0, 1);
        StarInfo blackDwarf1 = new StarInfo("Black Dwarf", 0, 1);
        StarInfo subgiantStar = new StarInfo("Subgiant Star", 2, 12);
        StarInfo degenerateStar = new StarInfo("Degenerate Core Star", 2, 3);
        StarInfo redGiant = new StarInfo("Red Giant", 3, 12);
        StarInfo planetaryNebula1 = new StarInfo("Planetary Nebula", 2, 3);
        StarInfo whiteDwarf2 = new StarInfo("White Dwarf Star", 2, 3);
        StarInfo blackDwarf2 = new StarInfo("Black Dwarf", 2, 3);
        StarInfo planetaryNebula2 = new StarInfo("Planetary Nebula", 3, 12);
        StarInfo whiteDwarf3 = new StarInfo("White Dwarf", 3, 12);
        StarInfo blackDwarf3 = new StarInfo("Black Dwarf", 3, 12);

        StarTree<StarInfo> starTree = new StarTree<StarInfo>(gasCloud, 
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
                    new StarTree<StarInfo> (redGiant, null, null))),
            new StarTree<StarInfo>(highMassStar, null, null)
        );

        return starTree;
    } 

    public static void main(String[] args) {
        new StarApp();
    }
}
