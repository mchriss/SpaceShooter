// Matis Krisztian 523/1 mkim2052

package scenes;

import UX.CustomFont;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JPanel implements Scene {
    private final Image background;
    private JButton startButton;
    private JButton highscoreButton;
    private JButton exitButton;
    private final Game game;

    public Home(Game game) {
        this.game = game;
        setLayout(new BorderLayout());
        this.background = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "\\assets\\background.png");
        initPanel();
        setVisible(true);
    }

    @Override
    public void initPanel() {
        CustomFont customFont = new CustomFont();

        JLabel title = new JLabel("Space Invaders", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(customFont.deriveFont(64f));

        startButton = new JButton("START");
        startButton.setForeground(Color.WHITE);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.addActionListener(e -> {
            game.setLevel(1);
            game.startCounter();
        });
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startButton.setForeground(Color.CYAN);
            }
        });
        startButton.setFont(customFont.deriveFont(32f));

        highscoreButton = new JButton("HIGHSCORES");
        highscoreButton.setForeground(Color.WHITE);
        highscoreButton.setOpaque(false);
        highscoreButton.setContentAreaFilled(false);
        highscoreButton.setBorderPainted(false);
        highscoreButton.addActionListener(e -> {
            game.changeScene("scores");
            game.setScore(0);
        });
        highscoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                highscoreButton.setForeground(Color.CYAN);
            }
        });
        highscoreButton.setFont(customFont.deriveFont(32f));

        exitButton = new JButton("EXIT");
        exitButton.setForeground(Color.WHITE);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exitButton.setForeground(Color.CYAN);
            }
        });
        exitButton.setFont(customFont.deriveFont(32f));

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(4, 1));
        buttons.add(startButton);
        buttons.add(highscoreButton);
        buttons.add(exitButton);
        buttons.setOpaque(false);

        add(title, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth() + 50, this.getHeight() + 50, this);
    }
}
