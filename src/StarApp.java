import java.awt.Color;

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

    private CanvasWindow canvas;
    private BackgroundAnimate bgAnimate;
    private GraphicsGroup uiGroup;

    private TextField inputBox;
    private Button enterButton;
    private Button startButton;

    private StarTree evolutionTree;
    private StarTree.StarPhase currentPhase;

    private GraphicsText phaseLabel;

    public StarApp() {
        canvas = new CanvasWindow("Star Evolution", WIDTH, HEIGHT);
        canvas.setBackground(new Color(5, 5, 20));

        bgAnimate = new BackgroundAnimate();
        canvas.add(bgAnimate.getGroup());

        evolutionTree = buildEvolutionTree();
        currentPhase = evolutionTree.phase;

        uiGroup = new GraphicsGroup();
        canvas.add(uiGroup);

        setupStartUI();

        canvas.animate(dt -> bgAnimate.update(dt));
    }

    private void setupStartUI() {
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

    private void switchToBuilderMode() {
        uiGroup.removeAll();

        Ellipse star = new Ellipse(280, 280, 40, 40);
        star.setFillColor(new Color(255, 200, 80));
        star.setStroked(false);
        canvas.add(star);

        phaseLabel = new GraphicsText(currentPhase.getStarInfo());
        phaseLabel.setFillColor(Color.WHITE);
        phaseLabel.setFont(FontStyle.BOLD, 18);
        phaseLabel.setCenter(300, 360);
        canvas.add(phaseLabel);

        GraphicsText instructions = new GraphicsText("Enter star mass (0–100):");
        instructions.setFillColor(Color.WHITE);
        instructions.setPosition(20, 60);
        uiGroup.add(instructions);

        inputBox = new TextField();
        inputBox.setPosition(WIDTH / 2.0 - 55, HEIGHT - 120);
        uiGroup.add(inputBox);

        enterButton = new Button("Enter");
        enterButton.setPosition(WIDTH / 2.0 - 35, HEIGHT - 90);
        enterButton.onClick(this::handleMassEntry);
        uiGroup.add(enterButton);
    }

    private void handleMassEntry() {
        int mass;

        try {
            mass = Integer.parseInt(inputBox.getText().trim());
        } catch (NumberFormatException e) {
            phaseLabel.setText("Invalid mass.");
            return;
        }

        StarTree.StarPhase next = findNextPhase(currentPhase, mass);

        if (next != null) {
            currentPhase = next;
            phaseLabel.setText(currentPhase.getStarInfo());
        } else {
            phaseLabel.setText("Final stage reached.");
        }
    }

    private StarTree.StarPhase findNextPhase(StarTree.StarPhase node, int mass) {
        if (node.left != null &&
            mass >= node.left.data.minMass &&
            mass <= node.left.data.maxMass) {
            return node.left;
        }

        if (node.right != null &&
            mass >= node.right.data.minMass &&
            mass <= node.right.data.maxMass) {
            return node.right;
        }

        return null;
    }

    private StarTree buildEvolutionTree() {

        StarInfo protostar = new StarInfo("Protostar", 0, 100);
        StarInfo lowMass = new StarInfo("Low Mass Star", 0, 12);
        StarInfo highMass = new StarInfo("High Mass Star", 12, 100);
        StarInfo whiteDwarf = new StarInfo("White Dwarf (Final)", 0, 12);
        StarInfo supernova = new StarInfo("Supernova", 12, 100);

        StarTree lowBranch = new StarTree(
                lowMass,
                new StarTree(whiteDwarf, null, null),
                null
        );

        StarTree highBranch = new StarTree(
                highMass,
                new StarTree(supernova, null, null),
                null
        );

        return new StarTree(protostar, lowBranch, highBranch);
    }

    public static void main(String[] args) {
        new StarApp();
    }
}