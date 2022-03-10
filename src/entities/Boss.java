// Matis Krisztian 523/1 mkim2052

package entities;

import scenes.Play;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Boss implements Entity {
    private int x;
    private int y;
    private int dx;
    private int dy;
    private final int height = 302;
    private final int width = 289;
    private int direction;
    private ImageIcon spaceShip;
    private final Play playScene;

    public Boss(int x, Play playScene) {
        this.x = x / 2 - width / 2;
        this.y = 50;
        dx = 1;
        dy = 1;
        direction = 1;
        this.playScene = playScene;
        try {
            this.spaceShip = new ImageIcon(ImageIO.read(new File("assets\\boss.png")));
        } catch (IOException e) {
            System.out.println(this.getClass().getSimpleName() + ": " + e);
        }
    }

    public void move(int panelWidth, int panelHeight, int limitX, int limitY) {
        // korkoros mozgas implementalasa
        // mikor eljutunk a megfelelo hatarig, modositjuk a direction valtozo erteket

        if (x + width < panelWidth - limitX && direction == 1) {
            x = x + dx;
            if (x + width >= panelWidth - limitX)
                direction = 2;
        }
        if (y + height < panelHeight - limitY && direction == 2) {
            y = y + dy;
            if (y + height >= panelHeight - limitY)
                direction = 3;
        }
        if (x > limitX && direction == 3) {
            x = x - dx;
            if (x <= limitX)
                direction = 4;
        }
        if (y > 50 && direction == 4) {
            y = y - dy;
            if (y <= 50)
                direction = 1;
        }
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
        return dx;
    }

    public void setDX(int dx) {
        this.dx = dx;
    }

    @Override
    public int getDY() {
        return dy;
    }

    public void setDY(int dy) {
        this.dy = dy;
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
