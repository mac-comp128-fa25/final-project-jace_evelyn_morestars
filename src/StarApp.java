import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Random;

import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;

public class StarApp {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int NUM_PARTICLES = 1000;

    private CanvasWindow canvas;

    // Background “gas cloud”
    private GraphicsGroup gasGroup;
    private List<GasParticle> particles;

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

        gasGroup = new GraphicsGroup();
        uiGroup = new GraphicsGroup();
        particles = new ArrayList<>();

        canvas.add(gasGroup);
        canvas.add(uiGroup);

        setupGasCloud();
        setupUI();
        startAnimation();
    }

    /**
     * Creates a cold gas & dust cloud drifting around
     */
    private void setupGasCloud() {
        for (int i = 0; i < NUM_PARTICLES; i++) {
            double size = 6 + rand.nextDouble() * 18;      // 2–24 px
            double x = rand.nextDouble() * WIDTH;
            double y = rand.nextDouble() * HEIGHT;

            Ellipse blob = new Ellipse(x, y, size, size);

            // colors low alpha 
            Color c = new Color(
                28 + rand.nextInt(48),     // 28–75
                50 + rand.nextInt(80),     // 50–129
                130 + rand.nextInt(120),   // 130–249
                80 + rand.nextInt(120));   // alpha 80–199

            blob.setFillColor(c);
            blob.setStroked(false);

            // slow random drift, “cold” gas = not fast
            double speed = 15 + rand.nextDouble() * 25; // px per second
            double angle = rand.nextDouble() * 2 * Math.PI;
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;

            particles.add(new GasParticle(blob, vx, vy));
            gasGroup.add(blob);
        }
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

        //TODO: Collapse the dust cloud

        startButton = new Button("Start Star Formation");
        // place it near the bottom middle
        startButton.setPosition(WIDTH / 2.0 - 80, HEIGHT - 70);
        startButton.onClick(this::switchToBuilderMode);
        uiGroup.add(startButton);
    }

    /**
     * Animation loop: move gas particles while on cloud screen
     * deltaTime in secs
     */
    private void startAnimation() {
        canvas.animate(deltaTime -> {
            if (!buildingStar) {
                updateGas(deltaTime);
            }
            // later, cool star-building animations go here 
        });
    }

    private void updateGas(double dt) {
        for (GasParticle p : particles) {
            p.shape.moveBy(p.vx * dt, p.vy * dt);

            double x = p.shape.getX();
            double y = p.shape.getY();
            double w = p.shape.getWidth();
            double h = p.shape.getHeight();

            // Wrap around edges to keep the cloud continuous
            if (x > WIDTH)  p.shape.setX(-w);
            if (x + w < 0)  p.shape.setX(WIDTH);
            if (y > HEIGHT) p.shape.setY(-h);
            if (y + h < 0)  p.shape.setY(HEIGHT);
        }
    }

    /**
     * Called when the user clicks the Start button.
     * Clears the gas cloud and swaps in a placeholder star-builder UI.
     */
    private void switchToBuilderMode() {
        buildingStar = true;

        gasGroup.removeAll();
        uiGroup.removeAll();

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
        // stack of events completed that can then be pulled from?
        //Stubs for now 
        }

    /**
     * Helper class for the gas cloud.
     */
    private static class GasParticle {
        final Ellipse shape;
        final double vx;
        final double vy;

        GasParticle(Ellipse shape, double vx, double vy) {
            this.shape = shape;
            this.vx = vx;
            this.vy = vy;
        }
    }

    public static void main(String[] args) {
        new StarApp();
    }
}
