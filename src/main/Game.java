// Matis Krisztian 523/1 mkim2052

package main;

import UX.Score;
import UX.Sound;
import scenes.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

public class Game extends JFrame {
    private final JPanel scenes;
    private final Home home;
    private final CountDown countDown;
    private final Play play;
    private final NextLevel nextLevel;
    private final Fail fail;
    private final HighScore highScore;
    private final SaveScore saveScore;
    private final CardLayout card;
    private final Sound sound;
    private int level;
    private int score;
    private final String savePath;

    public Game() {
        setTitle("Space Invaders");
        setBounds(200, 100, 1400, 790);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        sound = new Sound(System.getProperty("user.dir") + "\\assets\\music.wav");
        sound.loop();

        savePath = System.getProperty("user.dir") + "\\highscores\\scores.txt";

        home = new Home(this);
        countDown = new CountDown(this);
        play = new Play(this);
        nextLevel = new NextLevel(this);
        fail = new Fail(this);
        highScore = new HighScore(this);
        saveScore = new SaveScore(this);
        level = 1;
        score = 0;

        card = new CardLayout();
        scenes = new JPanel(card);
        scenes.add(home, "home");
        scenes.add(countDown, "count");
        scenes.add(play, "play");
        scenes.add(nextLevel, "next");
        scenes.add(fail, "fail");
        scenes.add(highScore, "scores");
        scenes.add(saveScore,"save");

        readScores();
        setContentPane(scenes);
        changeScene("home");

        setVisible(true);
        setFocusable(true);
    }

    public void startCounter() {            // Visszaszamlalas inditasa minden palya elott
        changeScene("count");
        countDown.countDown(level);
    }

    public void start() {               // Palya elinditasa - megfelelo szit beallitasa, es a palya kihivasanak inicializalasa
        play.setLevel(level);
        play.initLevel();
    }

    public void readScores() {          // pontszamok betoltese, rendezese csokkeno sorrendbe
        File dir = new File(System.getProperty("user.dir") + "\\highscores");
        if(! dir.exists()) {
            dir.mkdir();
        }
        File saveFile = new File(savePath);
        try {
            saveFile.createNewFile();
        }
        catch (IOException ignored) {}
        ArrayList<Score> scoreList = new ArrayList<>();
        try {
            Stream<String> stream = new BufferedReader(new FileReader(savePath)).lines();
            if(stream.findAny().isPresent()) {
                stream =  new BufferedReader(new FileReader(savePath)).lines();
                stream.forEach(row -> {
                    String[] data = row.split(",");
                    String username = data[0];
                    int scoreNumber = Integer.parseInt(data[1]);
                    scoreList.add(new Score(username, scoreNumber));
                });
                scoreList.sort(Comparator.comparing(Score::getScore));
                Collections.reverse(scoreList);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        if(!scoreList.isEmpty())
            highScore.initScoreList(scoreList);
    }

    public void changeScene(String name) {          // a megjelenitett nezet valtoztatasa CardLayouttal
        card.show(scenes, name);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void incLevel() {
        this.level++;
        play.setLevel(level);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        fail.updateScore();
        saveScore.updateScore();
        readScores();
    }

    public String getSavePath() {
        return savePath;
    }

    public static void main(String[] args) {
        new Game();
    }
}
