// Matis Krisztian 523/1 mkim2052

package scenes;

import UX.CustomFont;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveScore extends JPanel implements Scene {
    private final Image background;
    private JTextField nameField;
    private JButton homeButton;
    private JButton saveButton;
    private JLabel scoreLabel;
    private int scoreNumber;
    private final Game game;

    public SaveScore(Game game) {
        this.game = game;
        this.background = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "\\assets\\background.png");
        setLayout(new BorderLayout());
        initPanel();
    }

    @Override
    public void initPanel() {
        CustomFont customFont = new CustomFont();
        scoreNumber = game.getScore();

        scoreLabel = new JLabel("Your score is: " + scoreNumber, SwingConstants.CENTER);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(customFont.deriveFont(65f));

        JLabel userInput = new JLabel("Your name (letters only): ");
        userInput.setForeground(Color.WHITE);
        userInput.setFont(customFont.deriveFont(32f));

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(500, 70));
        nameField.setForeground(Color.WHITE);
        nameField.setFont(customFont.deriveFont(32f));
        nameField.setOpaque(false);

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

        saveButton = new JButton("SAVE");
        saveButton.setForeground(Color.WHITE);
        saveButton.setOpaque(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);
        saveButton.addActionListener(e -> {         // pontszam es felhasznalonev kimentese a savefile-ba
            FileWriter saveFile = null;
            try {
                saveFile = new FileWriter(game.getSavePath(), true);
                BufferedWriter bw = new BufferedWriter(saveFile);
                bw.write(nameField.getText() + "," + scoreNumber);
                bw.newLine();
                bw.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } finally {
                if (saveFile != null) {
                    try {
                        saveFile.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveButton.setText("SAVED");
                game.readScores();
                saveButton.setForeground(Color.CYAN);
            }
        });
        saveButton.setFont(customFont.deriveFont(32f));

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2));
        buttons.setOpaque(false);
        buttons.add(homeButton);
        buttons.add(saveButton);

        JPanel inputRow = new JPanel(new FlowLayout());
        inputRow.setOpaque(false);
        inputRow.add(userInput);
        inputRow.add(nameField);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new GridLayout(3, 1));

        center.add(scoreLabel);
        center.add(inputRow);
        center.add(buttons);

        add(center, BorderLayout.CENTER);

        setVisible(true);
    }

    public void updateScore() {
        scoreNumber = game.getScore();
        scoreLabel.setText("Your score is: " + scoreNumber + "!");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth() + 50, this.getHeight() + 50, this);
    }
}
