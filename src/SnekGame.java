import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnekGame extends JPanel implements ActionListener, KeyListener {
    private Menu menu;
    JFrame frame;
    JFrame endScreen;
    JFrame backgroundScreen;
    

    static JButton backButton;
    static JButton exitButton;
    


public SnekGame(Menu menu){    
        this.menu = menu;
        int boardWidth = 600;
        int boardHeight = boardWidth;
        SnekGame snakeGame = new SnekGame(boardHeight, boardWidth);

        //set up main frame 
        snakeGame.frame = new JFrame("Snake Game");
        snakeGame.frame.setSize(boardHeight, boardWidth);
        snakeGame.frame.setLocationRelativeTo(null);
        snakeGame.frame.setResizable(true);
        snakeGame.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        snakeGame.frame.setLayout(new BorderLayout());

        // //bg screen
        // snakeGame.backgroundScreen = new JFrame("Background");
        // snakeGame.backgroundScreen.setSize(1200, 1200);
        // snakeGame.backgroundScreen.setResizable(false);
        // snakeGame.backgroundScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // snakeGame.backgroundScreen.setVisible(true);
        // snakeGame.backgroundScreen.setAlwaysOnTop(true);
        // snakeGame.backgroundScreen.add(frame);
        // snakeGame.backgroundScreen.setBackground(Color.white);

        snakeGame.frame.add(snakeGame, BorderLayout.CENTER);

        

        JButton pauseButton = new JButton("||");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snakeGame.isPaused = !snakeGame.isPaused;
                // pauseButton.setText(snakeGame.isPaused ? "Resume" : "Pause");

                if (!snakeGame.isPaused) {
                    snakeGame.requestFocus();
                }
            }
        });

        snakeGame.frame.add(pauseButton, BorderLayout.SOUTH);
        pauseButton.setPreferredSize(new Dimension(200, 50));    
        snakeGame.frame.pack();
        snakeGame.frame.setVisible(true);
        snakeGame.requestFocus();

        }

    private class Tile { // bikin class tile nya ben bisa di masukin ke dlm parameter e makek this.x,
                         // this.y
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x; // ngambil ato ngabungin data pokok e biar bisa dipake dari void lainj
            this.y = y;
        }
    }

    Tile snakeHead; // masukin/bikin headnya ke dlm tile
    ArrayList<Tile> snakeBody; // bikin bodynya nek dh makan food biar bisa disimpen di dlm code

    int speed = 1;
    Tile snakeFood;
    Random random; // buat mbikin function random
    Timer gameLoop; // buat bikin timer game logic e kita
    private double velocityX;
    private double velocityY;
    boolean gameOver = false;
    int foodEatean = 0;
    int speedIncreases = 0;
    int lastSize = 0;

    int boardWidth =600;
    int boardHeight = boardWidth;
    int tileSize = 25; // masukin size nya dlm pixel tiap tile

    static int highscore = 0;

    SnekGame(int boardHeight, int boardWidth) { // this the actual game
        // basically the constructor of the game
        this.boardWidth = boardHeight;
        this.boardWidth = boardWidth; // masukin variable ke dlm snek game e
        highscore = loadHighScore();
        setPreferredSize(new Dimension(this.boardHeight, this.boardWidth));
        setBackground(Color.black); // blagg color
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5); // width dan height nya ditulis 5, krn x * y jadine luas e 25 pixel total
        snakeBody = new ArrayList<Tile>(); // arraylist buat nge store body body snake e yang lain setelah makan food e

        snakeFood = new Tile(10, 10); // tulisan e x10 and x10 biar food nge spawn e gak di dlm snake e
        random = new Random();
        placeFood();

        velocityX = 0; // Snake starts moving to the right
        velocityY = 0;

        // initializeGame();
        gameLoop = new Timer(60, this); // nge loop biar snake e di draw ulang (pokok e ben keliatan jalan)
        gameLoop.start();

    }

    protected void paintComponent(Graphics g) { // guna nya untuk menggambar snek kita, biar bisa muncul snek nya
        super.paintComponent(g); // kek deffault line dlm paintcomponent, itu hrs ada line ini biar bisa nge run
                                 // paintcomponent di JPanel
        draw(g);

    }

    public void draw(Graphics g) { // buat nggambar grid kita (biar bisa muncul pokok e)
        // for the grid (i itu buat berapa column and berapa row grid nya, makane 600 /
        // 25 buat 24 column dan 24 row)
        // (x1, y1, x2, y2)
        // top kiri (0,0) bawah kanan (600,600)
        Graphics2D g2d = (Graphics2D) g; // Cast to Graphics2D for advanced drawing options
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // To make the shapes
                                                                                                  // smoother

        // For the grid (optional, if you want to debug the grid layout)
        g2d.setColor(Color.black);
        for (int i = 0; i < boardWidth / tileSize; i++) {
            g2d.drawLine(i * tileSize, 0, i * tileSize, boardHeight); // Vertical lines
            g2d.drawLine(0, i * tileSize, boardWidth, i * tileSize); // Horizontal lines
        }
        int arcSize = tileSize / 2;
        // Draw the food
        g2d.setColor(Color.red);
        g2d.fillRoundRect(snakeFood.x * tileSize, snakeFood.y * tileSize, tileSize, tileSize, arcSize, arcSize);

        // Draw the snake head
        g2d.setColor(Color.green);
        // Controls the roundness of the edges
        g2d.fillRoundRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, arcSize, arcSize);
        // Draw the body segments with a slight overlap
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);

            // Calculate the offset for overlap
            int overlapOffset = 2; // Adjust this value for more or less overlap
            int x = snakePart.x * tileSize + overlapOffset;
            int y = snakePart.y * tileSize + overlapOffset;
            int size = tileSize - (2 * overlapOffset);

            g2d.fillRoundRect(x, y, size, size, arcSize, arcSize);

            // GradientPaint gradient = new GradientPaint(0, 0, Color.green, tileSize,
            // tileSize, Color.darkGray, true);
            // g2d.setPaint(gradient);

        }
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 14));
        g2d.drawString("Score: " + foodEatean, 10, 20); // Adjust position as needed
        g2d.drawString("High Score: " + highscore, 10, 40);

    }

    public void placeFood() {
        do {
            snakeFood.x = random.nextInt(boardWidth / tileSize); // 600px : 25px jadine random possition diantara 24
                                                                 // grid kita
            snakeFood.y = random.nextInt(boardHeight / tileSize);
        } while (isOnSnake(snakeFood));

    }

    private boolean isOnSnake(Tile tile) {
        if (collision(tile, snakeHead))
            return true;
            foodEatean++;
        for (Tile part : snakeBody) {
            if (collision(tile, part))
                return true;
        }
        return false;
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        // Check if the snake eats the food
        if (collision(snakeHead, snakeFood)) {
            // Add a new segment to the snake's body (at the last segment's position
            // temporarily)
            if (!snakeBody.isEmpty()) {
                Tile lastPart = snakeBody.get(snakeBody.size() - 1);
                snakeBody.add(new Tile(lastPart.x, lastPart.y));
            } else {
                snakeBody.add(new Tile(snakeHead.x, snakeHead.y));
            }
            placeFood(); // Place new food
        }

        // Move the snake body (if it exists)
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            Tile prevSnakePart = snakeBody.get(i - 1); // Get the part before the current one
            snakeBody.get(i).x = prevSnakePart.x; // Copy its x-coordinate
            snakeBody.get(i).y = prevSnakePart.y; // Copy its y-coordinate
        }
        if (!snakeBody.isEmpty()) {
            // First body segment follows the head
            snakeBody.get(0).x = snakeHead.x;
            snakeBody.get(0).y = snakeHead.y;
        }

        // Move the snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
        
        
        // Check for collisions with the body
        for (Tile snakePart : snakeBody) {
            if (collision(snakeHead, snakePart)) {
                gameOver = true; // Game over if the head collides with the body
                
            }
        }

        // Check for collisions with the walls
        if (snakeHead.x < 0 || snakeHead.x >= boardWidth / tileSize ||
                snakeHead.y < 0 || snakeHead.y >= boardHeight / tileSize) {
            gameOver = true; // Game over if the head hits the wall
        }
    }

    boolean isPaused = false;
    GameOver s = new GameOver();

    // semua @override itu auto generated by vscode quick fix
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (!isPaused) { // Only update game logic if not paused
            move();
            repaint(); // Redraw the game
            if (gameOver) {
                gameLoop.stop(); //ada issue dimana pas game over snake screen e ga ngilang kek tictoe, carane aku harus bikin 
                //sebuah pause button, dimana pas aku pencet pause button e bakal nge close snake game lalu nge buka game over class e
                JOptionPane.showMessageDialog(this, "Game Over! Score: " + foodEatean + "\nHigh Score: " + highscore,"Snake Game", JOptionPane.INFORMATION_MESSAGE);
                if (foodEatean > highscore) {
                    highscore = foodEatean; // To update the highscore
                    saveHighScore(highscore); // To save the highscore
                }
                s.GameOver();
                frame.dispose();
                // JOptionPane.showMessageDialog(this, "Game Over! Highscore: " + highscore , "Snake Game", JOptionPane.INFORMATION_MESSAGE);
            //untuk fitur highscore masih under age
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) { // 'P' key to pause/unpause
            isPaused = !isPaused; // Toggle pause state
        } else if (!isPaused) { // Only process movement keys if not paused
   
            if (e.getKeyCode() == KeyEvent.VK_W && velocityY != 1) {
                velocityX = 0;
                velocityY = -1;
            } else if (e.getKeyCode() == KeyEvent.VK_S && velocityY != -1) {
                velocityX = 0;
                velocityY = 1;
            } else if (e.getKeyCode() == KeyEvent.VK_D && velocityX != -1) {
                velocityX = 1;
                velocityY = 0;
            } else if (e.getKeyCode() == KeyEvent.VK_A && velocityX != 1) {
                velocityX = -1;
                velocityY = 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
                velocityX = 0;
                velocityY = -1;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
                velocityX = 0;
                velocityY = 1;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
                velocityX = 1;
                velocityY = 0;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
                velocityX = -1;
                velocityY = 0;
            }
        }
    }

    public void saveHighScore(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"))) {
            writer.write(String.valueOf(score));
            System.out.println("High score saved: " + score);
        } catch (IOException e) {
            System.out.println("Error saving high score: " + e.getMessage());
        }
    }
    
    public int loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"))) {
            String line = reader.readLine();
            int score = line != null ? Integer.parseInt(line) : 0;
            System.out.println("High score loaded: " + score);
            return score;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading high score: " + e.getMessage());
            return 0;
        }
    }
    

    @Override
    public void keyTyped(KeyEvent e) { // pokok e ini dua harus ada nek gk code e gk bisa jalan
    }

    @Override
    public void keyReleased(KeyEvent e) { // tapi karena aku gk butuh jadine fuction e gk dikasi instruction ato apa apa
                                          // gitu
    }

}