// Matis Krisztian 523/1 mkim2052

package entities;

import scenes.Play;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player implements Entity {
    private int x;
    private int y;
    private int dx;
    private final int height = 123;
    private final int width = 100;
    private ImageIcon spaceShip;
    private final Play playScene;

    public Player(int x, int y, Play playScene) {
        this.x = x / 2 - width / 2;
        this.y = y - height - 50;
        this.playScene = playScene;
        try {
            this.spaceShip = new ImageIcon(ImageIO.read(new File("assets\\player.png")));
        } catch (IOException e) {
            System.out.println(this.getClass().getSimpleName() + ": " + e);
        }
    }

    public void move() {
        x = x + dx;
    }

    @Override
    public int getDX() {
        return dx;
    }

    public void setDX(int dx) {
        this.dx = dx;
    }

    @Override
    public int getDY() {
        return 0;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
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
        return true;
    }

    @Override
    public void disable() {
    }

    @Override
    public void paint(Graphics g) {
        spaceShip.paintIcon(playScene, g, x, y);
    }
}
