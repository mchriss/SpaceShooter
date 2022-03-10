// Matis Krisztian 523/1 mkim2052

package scenes;

import UX.CustomFont;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NextLevel extends JPanel implements Scene {
    private final Image background;
    private JButton homeButton;
    private JButton nextLevelButton;
    private final Game game;

    public NextLevel(Game game) {
        this.game = game;
        this.background = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "\\assets\\background.png");
        setLayout(new BorderLayout());
        initPanel();
    }

    @Override
    public void initPanel() {
        CustomFont customFont = new CustomFont();

        JLabel clear = new JLabel("LEVEL COMPLETE!", SwingConstants.CENTER);
        clear.setForeground(Color.WHITE);
        clear.setFont(customFont.deriveFont(72f));

        homeButton = new JButton("HOME");
        homeButton.setForeground(Color.WHITE);
        homeButton.setOpaque(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);
        homeButton.addActionListener(e -> game.changeScene("home"));
        homeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                homeButton.setForeground(Color.CYAN);
            }
        });
        homeButton.setFont(customFont.deriveFont(32f));

        nextLevelButton = new JButton("NEXT LEVEL");
        nextLevelButton.setForeground(Color.WHITE);
        nextLevelButton.setOpaque(false);
        nextLevelButton.setContentAreaFilled(false);
        nextLevelButton.setBorderPainted(false);
        nextLevelButton.addActionListener(e -> {
            game.incLevel();
            game.startCounter();
        });
        nextLevelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nextLevelButton.setForeground(Color.CYAN);
            }
        });
        nextLevelButton.setFont(customFont.deriveFont(32f));


        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2));

        buttons.add(homeButton);
        buttons.add(nextLevelButton);
        buttons.setOpaque(false);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new GridLayout(2, 1));
        center.add(clear);
        center.add(buttons);

        add(center, BorderLayout.CENTER);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth() + 50, this.getHeight() + 50, this);
    }
}
