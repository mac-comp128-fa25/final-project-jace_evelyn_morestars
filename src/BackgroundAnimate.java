import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;

public class BackgroundAnimate {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int NUM_PARTICLES = 800;

    private GraphicsGroup group;
    private List<Particle> particles;
    private Random rand;

    public BackgroundAnimate() {
        group = new GraphicsGroup();
        particles = new ArrayList<>();
        rand = new Random();

        for (int i = 0; i < NUM_PARTICLES; i++) {
            Particle p = new Particle();
            particles.add(p);
            group.add(p.shape);
        }
    }

    public GraphicsGroup getGroup() {
        return group;
    }

    public void update(double dt) {
        for (Particle p : particles) {
            p.update(dt);
        }
    }

    /* ---------------- PARTICLE CLASS ---------------- */

    private class Particle {
        Ellipse shape;
        double dx;
        double dy;

        Particle() {
            double size = rand.nextDouble() * 3 + 1;

            shape = new Ellipse(
                    rand.nextDouble() * WIDTH,
                    rand.nextDouble() * HEIGHT,
                    size,
                    size
            );

            shape.setFillColor(new Color(
                    150 + rand.nextInt(80),
                    150 + rand.nextInt(80),
                    200 + rand.nextInt(55),
                    60
            ));

            shape.setStroked(false);

            dx = rand.nextDouble() * 10 - 5;
            dy = rand.nextDouble() * 10 - 5;
        }

        void update(double dt) {
            shape.moveBy(dx * dt, dy * dt);

            // wrap around screen
            if (shape.getX() < 0) shape.setX(WIDTH);
            if (shape.getX() > WIDTH) shape.setX(0);
            if (shape.getY() < 0) shape.setY(HEIGHT);
            if (shape.getY() > HEIGHT) shape.setY(0);
        }
    }
}