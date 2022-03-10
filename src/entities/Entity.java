// Matis Krisztian 523/1 mkim2052

package entities;

import java.awt.*;

public interface Entity {
    int getX();

    int getY();

    void setX(int x);

    void setY(int y);

    int getDX();

    int getDY();

    int getHeight();

    int getWidth();

    boolean isEnabled();

    void disable();

    void paint(Graphics g);
}
