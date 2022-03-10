// Matis Krisztian 523/1 mkim2052

package scenes;

import UX.CustomFont;
import UX.Score;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class HighScore extends JPanel implements Scene {
    private final Image background;
    private JButton homeButton;
    private final ArrayList<JLabel> scores;
    private final Game game;

    public HighScore(Game game) {
        this.game = game;
        this.scores = new ArrayList<>();
        this.background = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "\\assets\\background.png");
        setLayout(new BorderLayout());
        initPanel();
    }

    @Override
    public void initPanel() {
        CustomFont customFont = new CustomFont();

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

        JLabel listTitle = new JLabel("TOP 10 HIGHSCORES", SwingConstants.CENTER);
        listTitle.setForeground(Color.WHITE);
        listTitle.setFont(customFont.deriveFont(72f));

        JPanel scoreList = new JPanel();
        scoreList.setLayout(new BoxLayout(scoreList, BoxLayout.Y_AXIS));
        scoreList.setOpaque(false);

        for(int i = 0; i < 10; ++i) {
            scores.add(new JLabel());
        }

        scores.forEach(score -> {
            score.setForeground(Color.WHITE);
            score.setFont(customFont.deriveFont(32f));
            score.setHorizontalAlignment(SwingConstants.CENTER);
            scoreList.add(score);
        });

        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(2, 1));
        bottom.setOpaque(false);
        bottom.add(homeButton);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1, 3));
        center.setOpaque(false);
        center.add(new Container());
        center.add(scoreList);
        center.add(new Container());

        add(listTitle, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void initScoreList(ArrayList<Score> list) {      // csak az elso 10 legnagyobb pontszamot jelenitjuk meg
        Score temp;
        for(int i = 0; i < 10 && i < list.size(); ++i) {
            temp = list.get(i);
            scores.get(i).setText(i + 1 + ". " + temp.getUsername() + ": " + temp.getScore());
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth() + 50, this.getHeight() + 50, this);
    }
}
