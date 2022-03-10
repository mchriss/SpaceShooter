// Matis Krisztian 523/1 mkim2052

package scenes;

import UX.CustomFont;
import main.Game;

import javax.swing.*;
import java.awt.*;

public class CountDown extends JPanel implements Scene {
    private final Image background;
    private final Game game;
    private Timer timer;
    private int counter;
    private JLabel text;

    public CountDown(Game game) {
        setLayout(new BorderLayout());
        this.game = game;
        background = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "\\assets\\bg2.jpg");
        initPanel();
    }

    @Override
    public void initPanel() {
        text = new JLabel("Get Ready!", SwingConstants.CENTER);
        text.setForeground(Color.WHITE);
        text.setFont(new CustomFont().deriveFont(65f));
        add(text, BorderLayout.CENTER);
        setVisible(true);
    }

    public void countDown(int level) {
        switch (level % 3) {            // a szint fuggvenyeben mas-mas nevet jelenitunk meg az adott palyanak
            case 0 -> text.setText("Level " + level + ": Meet the boss!");
            case 1 -> text.setText("Level " + level + ": Invasion!");
            case 2 -> text.setText("Level " + level + ": Asteroids!");
        }

        counter = 5;
        timer = new Timer(1000, e -> {      // visszaszamlalas timer segitsegevel
            if (counter == 4) {
                text.setText("Get Ready!");
            } else if (counter <= 3) {
                text.setText(String.valueOf(counter));
            }
            --counter;
            if (counter < 0) {
                timer.stop();
                game.changeScene("play");
                game.start();
            }
        });
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth() + 50, this.getHeight() + 50, this);
    }
}
