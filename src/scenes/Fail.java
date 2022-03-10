// Matis Krisztian 523/1 mkim2052

package scenes;

import UX.CustomFont;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Fail extends JPanel implements Scene {
    private final Image background;
    private JButton homeButton;
    private JButton saveButton;
    private JButton retryButton;
    private JLabel score;
    private final Game game;

    public Fail(Game game) {
        this.game = game;
        this.background = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "\\assets\\background.png");
        setLayout(new BorderLayout());
        initPanel();
    }

    @Override
    public void initPanel() {
        CustomFont customFont = new CustomFont();

        JLabel death = new JLabel("YOU DIED", SwingConstants.CENTER);
        death.setForeground(Color.WHITE);
        death.setFont(customFont.deriveFont(72f));

        score = new JLabel("Your score is: " + game.getScore() + "!", SwingConstants.CENTER);
        score.setForeground(Color.WHITE);
        score.setFont(customFont.deriveFont(50f));

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

        saveButton = new JButton("SAVE SCORE");
        saveButton.setForeground(Color.WHITE);
        saveButton.setOpaque(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);
        saveButton.addActionListener(e -> game.changeScene("save"));
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveButton.setForeground(Color.CYAN);
            }
        });
        saveButton.setFont(customFont.deriveFont(32f));

        retryButton = new JButton("RETRY");
        retryButton.setForeground(Color.WHITE);
        retryButton.setOpaque(false);
        retryButton.setContentAreaFilled(false);
        retryButton.setBorderPainted(false);
        retryButton.addActionListener(e -> game.startCounter());
        retryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                retryButton.setForeground(Color.CYAN);
            }
        });
        retryButton.setFont(customFont.deriveFont(32f));

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 3));

        buttons.add(homeButton);
        buttons.add(saveButton);
        buttons.add(retryButton);
        buttons.setOpaque(false);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new GridLayout(3, 1));
        center.add(death);
        center.add(score);
        center.add(buttons);

        add(center, BorderLayout.CENTER);
    }

    public void updateScore() {
        this.score.setText("Your score is: " + game.getScore() + "!");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth() + 50, this.getHeight() + 50, this);
    }
}
