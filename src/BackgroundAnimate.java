import java.awt.Color;
import java.awt.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.*;

public class BackgroundAnimate {

    // particles
    private static final int NUM_PARTICLES = 1000;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    //collapse animation 
    private boolean collapsing = false;
    private double collapseProgress = 0; // 0 → 1
    private static final double COLLAPSE_DURATION = 3.0; // seconds
    private static final double CENTER_X = 300;
    private static final double CENTER_Y = 300;


    private final Random rand = new Random();

    private GraphicsGroup gasGroup;
    private List<GasParticle> particles;

    public BackgroundAnimate() {
        gasGroup = new GraphicsGroup();
        particles = new ArrayList<>();
        setupGasCloud();
    }

    public GraphicsGroup getGroup() {
        return gasGroup;
    }

    /**
     * Creates the drifting gas cloud
     */
    private void setupGasCloud() {
        for (int i = 0; i < NUM_PARTICLES; i++) {
            double size = 6 + rand.nextDouble() * 18;
            double x = rand.nextDouble() * WIDTH;
            double y = rand.nextDouble() * HEIGHT;

            Ellipse blob = new Ellipse(x, y, size, size);

            Color c = new Color(
                28 + rand.nextInt(48),
                50 + rand.nextInt(80),
                130 + rand.nextInt(120),
                80 + rand.nextInt(120)
            );

            blob.setFillColor(c);
            blob.setStroked(false);

            double speed = 15 + rand.nextDouble() * 25;
            double angle = rand.nextDouble() * 2 * Math.PI;
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;

            particles.add(new GasParticle(blob, vx, vy));
            gasGroup.add(blob);
        }
    }

        private static class GasParticle {
        
        final Ellipse shape;
        final double vx;
        final double vy;
        final double originalSize;

        GasParticle(Ellipse shape, double vx, double vy) {

            this.shape = shape;
            this.vx = vx;
            this.vy = vy;
            this.originalSize = shape.getWidth();

        }
    }

    public void startCollapse() {
        collapsing = true;
        collapseProgress = 0;
    }

    /**
     * Move all particles each frame
     */
    public void update(double dt) {
        if (!collapsing) {
            updateNormal(dt);
        } else {
            updateCollapse(dt);
        }
    }

//update helpers 

    private void updateNormal(double dt) {
        for (GasParticle p : particles) {
            p.shape.moveBy(p.vx * dt, p.vy * dt);

            double x = p.shape.getX();
            double y = p.shape.getY();
            double w = p.shape.getWidth();
            double h = p.shape.getHeight();

            if (x > WIDTH)  p.shape.setX(-w);
            if (x + w < 0)  p.shape.setX(WIDTH);
            if (y > HEIGHT) p.shape.setY(-h);
            if (y + h < 0)  p.shape.setY(HEIGHT);
        }
    }

    private void updateCollapse(double dt) {
        collapseProgress += dt / COLLAPSE_DURATION;
        if (collapseProgress > 1) collapseProgress = 1;

        for (GasParticle p : particles) {

            // current position
            double x = p.shape.getCenter().getX();
            double y = p.shape.getCenter().getY();

            // vector toward center
            double dx = CENTER_X - x;
            double dy = CENTER_Y - y;

            // pull strength increases as it gets closer in, increase for d r a m a
            double pull = 100 * collapseProgress;  

            p.shape.moveBy(dx * 0.02 * pull * dt, dy * 0.02 * pull * dt);

            // fade out + shrink

            Paint paint = p.shape.getFillColor();

            if (paint instanceof Color c) {
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();

            int newAlpha = (int)(255 * (1 - collapseProgress));

            p.shape.setFillColor(new Color(r, g, b, newAlpha));
            }
        
            double shrink = 1 - collapseProgress;
            p.shape.setSize(p.originalSize * shrink, p.originalSize * shrink);
        }
    }
}
