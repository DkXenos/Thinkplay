import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;

public class SnekGame2 extends JPanel implements ActionListener, KeyListener {
    JFrame frame;

    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardHeight = boardWidth;
        SnekGame2 snakeGame = new SnekGame2(boardHeight, boardWidth);

        snakeGame.frame = new JFrame("Snake Game");
        snakeGame.frame.setSize(boardHeight, boardWidth);
        snakeGame.frame.setLocationRelativeTo(null);
        snakeGame.frame.setResizable(true);
        snakeGame.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        snakeGame.frame.setLayout(new BorderLayout());

        snakeGame.frame.add(snakeGame, BorderLayout.CENTER);

        JButton pauseButton = new JButton("||");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snakeGame.isPaused = !snakeGame.isPaused;
                pauseButton.setText(snakeGame.isPaused ? "Resume" : "Pause");

                if (!snakeGame.isPaused) {
                    snakeGame.requestFocus();
                }
            }
        });

        snakeGame.frame.add(pauseButton, BorderLayout.SOUTH);

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

    static int highscore = 0;
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

    int boardWidth = 600;
    int boardHeight = boardWidth;
    int tileSize = 25; // masukin size nya dlm pixel tiap tile

    SnekGame2(int boardHeight, int boardWidth) { // this the actual game
        this.boardWidth = boardHeight;
        this.boardWidth = boardWidth; // masukin variable ke dlm snek game e
        setPreferredSize(new Dimension(this.boardHeight, this.boardWidth));
        setBackground(Color.BLACK); // blagg color
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5); // width dan height nya ditulis 5, krn x * y jadine luas e 25 pixel total
        snakeBody = new ArrayList<Tile>(); // arraylist buat nge store body body snake e yang lain setelah makan food e

        snakeFood = new Tile(10, 10); // tulisan e x10 and x10 biar food nge spawn e gak di dlm snake e
        random = new Random();
        placeFood();

        // Set initial movement direction
        velocityX = 0; // Snake starts moving to the right
        velocityY = 0;

        gameLoop = new Timer(80, this); // nge loop biar snake e di draw ulang (pokok e ben keliatan jalan)
        gameLoop.start();
    }

    public void paintComponent(Graphics g) { // guna nya untuk menggambar snek kita, biar bisa muncul snek nya
        super.paintComponent(g); // kek deffault line dlm paintcomponent, itu hrs ada line ini biar bisa nge run
                                 // paintcomponent di JPanel
        draw(g);

    }

    public void draw(Graphics g) {

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
    }

    private double headX = 5; // Use integers for grid positions
    private double headY = 5;

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
        highscore++;
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
            snakeBody.get(0).x = (int) Math.round(headX);
            snakeBody.get(0).y = (int) Math.round(headY);
        }

        // Move the snake head
        headX += velocityX;
        headY += velocityY;

        // Snap to the grid when fully moved
        if (Math.abs(headX - Math.round(headX)) < 0.01) {
            snakeHead.x = (int) Math.round(headX);
        }
        if (Math.abs(headY - Math.round(headY)) < 0.01) {
            snakeHead.y = (int) Math.round(headY);
        }

        snakeHead.x = (int) Math.round(headX);
        snakeHead.y = (int) Math.round(headY);

        checkCollisions();
    }

    private void checkCollisions() {
        // Check for collisions with the body
        for (Tile snakePart : snakeBody) {
            if (collision(snakeHead, snakePart)) {
                gameOver = true; // Game over if the head collides with the body
                return;
            }
        }

        // Check for collisions with the walls
        if (snakeHead.x < 0 || snakeHead.x >= boardWidth / tileSize ||
                snakeHead.y < 0 || snakeHead.y >= boardHeight / tileSize) {
            gameOver = true; // Game over if the head hits the wall
        }
    }

    private boolean isPaused = false;

    // semua @override itu auto generated by vscode quick fix
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPaused) { // Only update game logic if not paused
            move();
            repaint(); // Redraw the game
            if (gameOver) {
                gameLoop.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Highscore: " + highscore, "Snake Game",
                        JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }

            if (snakeBody.size() % 5 == 0 && snakeBody.size() != 0 && snakeBody.size() != lastSize) {
                int newDelay = Math.max(50, gameLoop.getDelay() - 10);
                gameLoop.setDelay(newDelay);
                speed++;
                lastSize = snakeBody.size(); // Update `lastSize` only when speed changes
            }
            
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) { // 'P' key to pause/unpause
            isPaused = !isPaused; // Toggle pause state
        } else if (!isPaused) { // Only process movement keys if not paused
            if (e.getKeyCode() == KeyEvent.VK_W && velocityY == 0) {
                velocityX = 0;
                velocityY = -1;
            } else if (e.getKeyCode() == KeyEvent.VK_S && velocityY == 0) {
                velocityX = 0;
                velocityY = 1;
            } else if (e.getKeyCode() == KeyEvent.VK_D && velocityX == 0) {
                velocityX = 1;
                velocityY = 0;
            } else if (e.getKeyCode() == KeyEvent.VK_A && velocityX == 0) {
                velocityX = -1;
                velocityY = 0;
            }
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