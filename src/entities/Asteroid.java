// Matis Krisztian 523/1 mkim2052

package entities;

import scenes.Play;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Asteroid implements Entity {
    private int x;
    private int y;
    private final int dx;
    private final int dy;
    private final int height = 53;
    private final int width = 50;
    private boolean enabled;
    private ImageIcon asteroid;
    private final Play playScene;

    public Asteroid(int x, int y, int dx, int dy, Play playScene) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.playScene = playScene;
        enabled = true;
        try {
            this.asteroid = new ImageIcon(ImageIO.read(new File("assets\\asteroid.png")));
        } catch (IOException e) {
            System.out.println(this.getClass().getSimpleName() + ": " + e);
        }
    }

    public void paint(Graphics g) {
        asteroid.paintIcon(playScene, g, x, y);
    }

    public void move() {
        x = x + dx;
        y = y + dy;
    }

    public boolean hit(Entity e) {
        return x <= e.getX() + e.getWidth() && x + width >= e.getX() && y <= e.getY() + e.getHeight() && y + height >= e.getY();
    }

    public int getDX() {
        return dx;
    }

    public int getDY() {
        return dy;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void disable() {
        if (enabled) {
            enabled = false;
        }
    }
}
