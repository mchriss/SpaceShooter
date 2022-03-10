// Matis Krisztian 523/1 mkim2052

package entities;

import scenes.Play;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Enemy implements Entity {
    private int x;
    private int y;
    private final int height = 50;
    private final int width = 50;
    private boolean enabled;
    private ImageIcon spaceShip;
    private final Play playScene;

    public Enemy(int x, int y, Play playScene) {
        this.x = x;
        this.y = y;
        this.playScene = playScene;
        this.enabled = true;
        try {
            this.spaceShip = new ImageIcon(ImageIO.read(new File("assets\\enemy.png")));
        } catch (IOException e) {
            System.out.println(this.getClass().getSimpleName() + ": " + e);
        }
    }

    @Override
    public void disable() {
        if (enabled) {
            enabled = false;
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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
    public int getDX() {
        return 0;
    }

    @Override
    public int getDY() {
        return 0;
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
    public void paint(Graphics g) {
        spaceShip.paintIcon(playScene, g, x, y);
    }
}
