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

    //unused rn
    private Button addTemplateButton;
    private TextField templateNameField;
    private GraphicsText matchLabel;

    // state
    private boolean buildingStar = false;
    private final Random rand = new Random();

    // action stack
    private Deque<String> actionStack = new ArrayDeque(); // tracks previous actions, so it can be undone later

    public StarApp() {
        canvas = new CanvasWindow("More Stars!", WIDTH, HEIGHT);
        canvas.setBackground(new Color(5, 5, 20)); 

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
        // place it near the bottom middle
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
                "text\n" +
                "testing.");
        instructions.setFont(FontStyle.PLAIN, 16);
        instructions.setFillColor(new Color(220, 220, 240));
        instructions.setPosition(20, 90);
        instructions.setWrappingWidth(560);
        uiGroup.add(instructions);

        //Add back button
        
        //Stubs for now 
        }

    public static void main(String[] args) {
        new StarApp();
    }
}
