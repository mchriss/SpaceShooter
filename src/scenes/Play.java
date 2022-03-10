// Matis Krisztian 523/1 mkim2052

package scenes;

import UX.CustomFont;
import UX.Sound;
import entities.*;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Play extends JPanel implements Scene {
    private final Game game;
    private final Image background;
    private JLabel levelLabel;
    private JLabel powerLabel;
    private JLabel scoreLabel;
    private Player player;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Asteroid> asteroids;
    private final ArrayList<Projectile> projectiles;
    private final ArrayList<Projectile> enemyProjectiles;
    private Boss boss;
    private int power;
    private int prevPower;
    private int prevScore;
    private int score;
    private int level;
    private int bossHealth;
    private Timer timer;
    private final Sound shotSound1;
    private final Sound shotSound2;
    private final Sound explosionSound;
    private final Random rand;

    public Play(Game game) {
        this.game = game;
        setLayout(new BorderLayout());
        background = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "\\assets\\bg2.jpg");

        game.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (player != null)
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_A, KeyEvent.VK_LEFT -> player.setDX(-8);
                        case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> player.setDX(8);
                        case KeyEvent.VK_SPACE -> shoot();
                    }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (player != null)
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> player.setDX(0);
                    }
            }
        });

        projectiles = new ArrayList<>();
        enemyProjectiles = new ArrayList<>();
        enemies = new ArrayList<>();
        asteroids = new ArrayList<>();

        power = 1;
        prevPower = 1;
        score = 0;
        prevScore = 0;
        level = 1;

        shotSound1 = new Sound(System.getProperty("user.dir") + "\\assets\\shot1.wav");
        shotSound2 = new Sound(System.getProperty("user.dir") + "\\assets\\shot2.wav");
        explosionSound = new Sound(System.getProperty("user.dir") + "\\assets\\explosion.wav");
        rand = new Random();

        initPanel();
    }

    public void initLevel() {       // palya szerkezetenek, adatainak inicializalasa
        levelLabel.setText("Level: " + level);
        projectiles.clear();
        enemyProjectiles.clear();
        if (level == 1) {
            power = 1;
            score = 0;
        } else {            // ujrakezdeskor, kovetkezo szintre lepeskor megmaradnak az elozo szint vegen szerzett pontjaink es tuzeloeronk
            power = prevPower;
            powerLabel.setText("Power: " + power);
            score = prevScore;
            scoreLabel.setText("Score: " + score);
        }
        initPlayer();
        initEnemies();
        setVisible(true);
        play();
    }

    @Override
    public void initPanel() {           // a UI resz inicializalasa
        CustomFont customFont = new CustomFont();
        JPanel uiPanel = new JPanel(new GridLayout(1, 3, 400, 0));
        uiPanel.setOpaque(false);

        levelLabel = new JLabel("Level: " + level);
        levelLabel.setOpaque(false);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(customFont.deriveFont(20f));

        powerLabel = new JLabel("Power: " + power);
        powerLabel.setOpaque(false);
        powerLabel.setForeground(Color.WHITE);
        powerLabel.setFont(customFont.deriveFont(20f));

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setOpaque(false);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(customFont.deriveFont(20f));

        uiPanel.add(levelLabel);
        uiPanel.add(powerLabel);
        uiPanel.add(scoreLabel);
        uiPanel.setVisible(true);

        add(uiPanel, BorderLayout.NORTH);
    }

    private void initPlayer() {         // jatekos hajojanak inicializalasa/visszaallitasa kezdeti poziciora
        player = new Player(game.getWidth(), game.getHeight(), this);

        int x = player.getX();
        int y = player.getY();
        int width = player.getWidth();
        int height = player.getHeight();

        // ha talalat erte a playert, ujra inicializaljuk a poziciojat, es a korulotte levo asteroidakat/lovedekeket kikapcsoljuk, hogy ne sebezzenek meg egybol
        for (Projectile projectile : enemyProjectiles)
            if (projectile.getX() >= x && projectile.getX() <= x + width && projectile.getY() >= y - 50 && projectile.getY() <= y + height)
                projectile.disable();

        for (Asteroid asteroid : asteroids) {
            if (asteroid.getX() >= x - width && asteroid.getX() <= x + 2 * width && asteroid.getY() >= y - height && asteroid.getY() <= y + height)
                asteroid.disable();
        }
    }

    private void initBoss() {
        boss = new Boss(game.getWidth(), this);
        bossHealth = 100;
    }

    private void initEnemies() {        // ellenfelek/akadalyok inicializalasa a szint fuggvenyeben
        enemies.clear();
        asteroids.clear();

        switch (level % 3) {            // 3 fajta palya van, melyek harmankent ismetlodnek
            case 0 -> initBoss();       // minden harmadik palya bossfight

            case 1 -> {                 // minden 3k + 1 palya hagyomanyos ellenfelekkel jatszott palya
                int numRows = rand.nextInt(3) + 3;
                int numCols = rand.nextInt(7) + 7;
                for (int y = 0; y < numRows; ++y) {
                    for (int x = 0; x < numCols; ++x) {
                        enemies.add(new Enemy(x * game.getWidth() / (numCols + 1) + game.getWidth() / (numCols * 2) + 25, y * 75 + 50, this));
                    }
                }
            }

            case 2 -> {                 // minden 3k + 2. palya asteroid palya
                int time = rand.nextInt(5) + 10;
                for (int i = 0; i < time; ++i) {
                    for (int j = 0; j < 10 + level; ++j) {
                        asteroids.add(new Asteroid(rand.nextInt(game.getWidth() - 200) - i * 450, -rand.nextInt(600) - i * 600, 2, 3, this));
                    }
                }
            }
        }
    }

    public void play() {            // mozgasok, aktualizalasok megvalositasa ismetelten, Timer segitsegevel
        timer = new Timer(10, e -> {
            if (isOver()) {         // ha veget ert a palya, elmentjuk az addig osszeszerzett pontjainkat, tuzeloero erteket
                timer.stop();
                prevPower = power;
                prevScore = score;
                game.setScore(score);
                game.changeScene("next");
            }
            player.move();
            if (player.getX() < 10)         // mozgasi korlatok
                player.setX(10);
            if (player.getX() > game.getWidth() - 10 - player.getWidth())
                player.setX(game.getWidth() - 10 - player.getWidth());

            if (boss != null)
                boss.move(game.getWidth(), game.getHeight(), 200, player.getHeight() + 100);

            for (Asteroid asteroid : asteroids) {
                asteroid.move();
                if (asteroid.isEnabled()) {
                    if (asteroid.hit(player)) {
                        if (power == 1) {           // barmilyen talalat eseten, ha a tuzeloeronk es egyben az eletunk is 1, meghalunk
                            timer.stop();
                            game.changeScene("fail");
                        } else {
                            --power;
                            initPlayer();
                            powerLabel.setText("Power: " + power);
                        }
                    }
                    if (asteroid.getX() > game.getWidth() || asteroid.getY() > game.getHeight())
                        asteroid.disable();
                }
            }

            for (Projectile projectile : enemyProjectiles) {
                projectile.move();
                if (projectile.hit(player)) {
                    projectile.disable();
                    if (power == 1) {
                        timer.stop();
                        game.changeScene("fail");
                    } else {
                        --power;
                        initPlayer();
                        powerLabel.setText("Power: " + power);
                    }
                }
                if (projectile.getY() > game.getHeight())
                    projectile.disable();
            }

            for (Projectile projectile : projectiles) {
                projectile.move();
                if (boss != null) {
                    if (projectile.hit(boss)) {
                        projectile.disable();
                        --bossHealth;
                        if (bossHealth == 50) {
                            boss.setDX(boss.getDX() * 2);
                            boss.setDY(boss.getDY() * 2);
                        }
                        if (bossHealth == 0) {
                            boss = null;
                            score += 1000;
                        }
                        if (bossHealth <= 50 && bossHealth % 10 == 0) {
                            for (int i = 0; i < 5; ++i) {
                                asteroids.add(new Asteroid(rand.nextInt(game.getWidth()), -53, 0, 3, this));
                            }
                        }
                    }
                }
                for (Entity enemy : enemies) {
                    if (enemy.isEnabled()) {
                        if (projectile.hit(enemy)) {
                            projectile.disable();
                            enemy.disable();
                            explosionSound.play();
                            score += 10;
                            scoreLabel.setText("Score: " + score);
                            if (score % 300 == 0 && power < 3) {        // minden ellenseg es asteroid 10 pontot er, minden 300 pont utan no eggyel a tuzeloeronk
                                ++power;
                                powerLabel.setText("Power: " + power);
                            }
                        }
                    }
                }
                for (Asteroid asteroid : asteroids) {
                    if (asteroid.isEnabled()) {
                        if (projectile.hit(asteroid)) {
                            projectile.disable();
                            asteroid.disable();
                            explosionSound.play();
                            score += 10;
                            scoreLabel.setText("Score: " + score);
                            if (score % 300 == 0 && power < 3) {
                                ++power;
                                powerLabel.setText("Power: " + power);
                            }
                        }
                    }
                }
                int px = projectile.getX();
                int py = projectile.getY();
                if (px < 0 || px > game.getWidth() || py < 0) {
                    projectile.disable();
                }

            }

            int ex, ey, bx, by;
            for (Enemy enemy : enemies) {           // ellenfelek lovesei
                if (enemy.isEnabled() && rand.nextInt(1000) < 1 + level - 1) {
                    ex = enemy.getX() + enemy.getWidth() / 2;
                    ey = enemy.getY() + enemy.getHeight() - 5;
                    enemyProjectiles.add(new Projectile(ex, ey, 0, 5, this));
                    shotSound2.play();
                }
            }
            if (boss != null && rand.nextInt(1000) < 5) {
                bx = boss.getX() + boss.getWidth() / 2;
                by = boss.getY() + boss.getHeight() - 10;
                for (int i = 0; i < 5; ++i) {
                    enemyProjectiles.add(new Projectile(bx + (i - 2) * 5, by, (i - 2), 5, this));
                    shotSound2.play();
                }
            }

            repaint();
        });
        timer.start();
    }

    private boolean isOver() {          // egy palya akkor er veget, ha semmilyen tipusu ellenfel nem talalhato a palyan
        enemies.removeIf(enemy -> !enemy.isEnabled());
        asteroids.removeIf(asteroid -> !asteroid.isEnabled());
        return enemies.size() == 0 && asteroids.size() == 0 && boss == null;
    }

    private void shoot() {          // player lovesei
        shotSound1.play();
        projectiles.removeIf(projectile -> !projectile.isEnabled());

        int x = player.getX();
        int y = player.getY();
        int width = player.getWidth();

        switch (power) {
            case 3:
                projectiles.add(new Projectile(x, y - 5, 0, -10, this));
                projectiles.add(new Projectile(x + width, y - 5, 0, -10, this));

            case 2:
                projectiles.add(new Projectile(x + width / 2, y - 5, -8, -10, this));
                projectiles.add(new Projectile(x + width / 2, y - 5, 8, -10, this));

            case 1:
                projectiles.add(new Projectile(x + width / 2, y - 5, 0, -10, this));
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth() + 50, this.getHeight() + 50, this);
        player.paint(g);

        if (boss != null)
            boss.paint(g);

        for (Enemy enemy : enemies) {
            if (enemy.isEnabled()) {
                enemy.paint(g);
            }
        }

        for (Asteroid asteroid : asteroids) {
            if (asteroid.isEnabled()) {
                asteroid.paint(g);
            }
        }

        for (Projectile projectile : projectiles) {
            if (projectile.isEnabled()) {
                projectile.paint(g);
            }
        }

        for (Projectile projectile : enemyProjectiles) {
            if (projectile.isEnabled()) {
                projectile.paint(g);
            }
        }
    }

    public void setLevel(int level) {
        this.level = level;
        repaint();
    }
}
