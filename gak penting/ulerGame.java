// import java.awt.*;
// import java.awt.event.*;
// import java.util.ArrayList;
// import java.util.Random;
// import javax.swing.*;
// import javax.swing.border.Border;

// public class SnekGame extends JPanel implements ActionListener, KeyListener {
//     private Menu menu;
//     JFrame frame;
//     JFrame endScreen;
//     static JButton backButton;
//     static JButton exitButton;
    


// public static void main(String[] args) {
    
//         // this.menu = menu;
//         int boardWidth = 600;
//         int boardHeight = boardWidth;
//         SnekGame snakeGame = new SnekGame(boardHeight, boardWidth);

//         //set up main frame 
//         snakeGame.frame = new JFrame("Snake Game");
//         snakeGame.frame.setSize(boardHeight, boardWidth);
//         snakeGame.frame.setLocationRelativeTo(null);
//         snakeGame.frame.setResizable(true);
//         snakeGame.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         snakeGame.frame.setLayout(new BorderLayout());

//         //set up end screen
//         snakeGame.endScreen = new JFrame("GameOver");
//         snakeGame.endScreen.setSize(300, 200);
//         snakeGame.endScreen.setResizable(false);
//         snakeGame.endScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         snakeGame.endScreen.setLocationRelativeTo(null);
//         snakeGame.endScreen.setVisible(false);

//         //set exit button
//         snakeGame.exitButton = new JButton("Exit to Windows");
//         snakeGame.endScreen.add(exitButton, BorderLayout.WEST);

//         //set back to menu buton
//         snakeGame.backButton = new JButton("Back to Menu");
//         snakeGame.endScreen.add(snakeGame.backButton, BorderLayout.EAST);

//         // think this is useless stuff
//         snakeGame.endScreen.requestFocus();

        
        

//         snakeGame.frame.add(snakeGame, BorderLayout.CENTER);

//         JButton pauseButton = new JButton("||");
//         pauseButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 snakeGame.isPaused = !snakeGame.isPaused;
//                 pauseButton.setText(snakeGame.isPaused ? "Resume" : "Pause");

//                 if (!snakeGame.isPaused) {
//                     snakeGame.requestFocus();
//                 }
//             }
//         });
//         snakeGame.exitButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed (ActionEvent e) {
//                 snakeGame.endScreen.dispose();
//                 snakeGame.frame.dispose(); // Close the menu
//             }
//         });
//         snakeGame.backButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed (ActionEvent e) {
//                 snakeGame.endScreen.dispose();
//                 snakeGame.frame.dispose(); // Close the menu
//                 new Menu();
//                 // menu.showMenu();
//             }
//         });

//         snakeGame.frame.add(pauseButton, BorderLayout.SOUTH);
//         pauseButton.setPreferredSize(new Dimension(200, 50));    
//         snakeGame.frame.pack();
//         snakeGame.frame.setVisible(true);
//         snakeGame.requestFocus();
//     }

//     private class Tile { // bikin class tile nya ben bisa di masukin ke dlm parameter e makek this.x,
//                          // this.y
//         int x;
//         int y;

//         Tile(int x, int y) {
//             this.x = x; // ngambil ato ngabungin data pokok e biar bisa dipake dari void lainj
//             this.y = y;
//         }
//     }

//     Tile snakeHead; // masukin/bikin headnya ke dlm tile
//     ArrayList<Tile> snakeBody; // bikin bodynya nek dh makan food biar bisa disimpen di dlm code

//     Tile snakeFood;
//     Random random; // buat mbikin function random
//     Timer gameLoop; // buat bikin timer game logic e kita
//     int velocityX;
//     int velocityY;
//     boolean gameOver = false;

//     int boardWidth =600;
//     int boardHeight = boardWidth;
//     int tileSize = 25; // masukin size nya dlm pixel tiap tile

//     SnekGame(int boardHeight, int boardWidth) { // this the actual game
//         // basically the constructor of the game
//         this.boardWidth = boardHeight;
//         this.boardWidth = boardWidth; // masukin variable ke dlm snek game e
//         setPreferredSize(new Dimension(this.boardHeight, this.boardWidth));
//         setBackground(Color.black); // blagg color
//         addKeyListener(this);
//         setFocusable(true);

//         snakeHead = new Tile(5, 5); // width dan height nya ditulis 5, krn x * y jadine luas e 25 pixel total
//         snakeBody = new ArrayList<Tile>(); // arraylist buat nge store body body snake e yang lain setelah makan food e

//         snakeFood = new Tile(10, 10); // tulisan e x10 and x10 biar food nge spawn e gak di dlm snake e
//         random = new Random();
//         placeFood();

//         velocityX = 0; // Snake starts moving to the right
//         velocityY = 0;

//         gameLoop = new Timer(75, this); // nge loop biar snake e di draw ulang (pokok e ben keliatan jalan)
//         gameLoop.start();

//     }

//     public void paintComponent(Graphics g) { // guna nya untuk menggambar snek kita, biar bisa muncul snek nya
//         super.paintComponent(g); // kek deffault line dlm paintcomponent, itu hrs ada line ini biar bisa nge run
//                                  // paintcomponent di JPanel
//         draw(g);

//     }

//     public void draw(Graphics g) { // buat nggambar grid kita (biar bisa muncul pokok e)
//         // for the grid (i itu buat berapa column and berapa row grid nya, makane 600 /
//         // 25 buat 24 column dan 24 row)
//         // (x1, y1, x2, y2)
//         // top kiri (0,0) bawah kanan (600,600)
//         g.setColor(Color.black);
//         for (int i = 0; i < boardWidth / tileSize; i++) {
//             g.drawLine(i * tileSize, 0, i * tileSize, boardHeight); // vertical lines
//             g.drawLine(0, i * tileSize, boardWidth, i * tileSize); // horizontal lines
//             // I DUNNNO WHY SUMPA KOK GA MUNCUL GRID LINE NYA HELP
//         }
//         g.setColor(Color.red);
//         g.fillRect(snakeFood.x * tileSize, snakeFood.y * tileSize, tileSize, tileSize); // gambar food

//         // for the snek head
//         g.setColor(Color.green);
//         // fillrect buat masukin lokasi snek head nya kita dimana lokasi ne dlm grid
//         g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

//         // snek body
//         for (int i = 0; i < snakeBody.size(); i++) {
//             Tile snakePart = snakeBody.get(i);
//             g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
//         }
//     }

//     public void placeFood() {
//         do {
//             snakeFood.x = random.nextInt(boardWidth / tileSize); // 600px : 25px jadine random possition diantara 24
//                                                                  // grid kita
//             snakeFood.y = random.nextInt(boardHeight / tileSize);
//         } while (isOnSnake(snakeFood));

//     }

//     private boolean isOnSnake(Tile tile) {
//         if (collision(tile, snakeHead))
//             return true;
//         for (Tile part : snakeBody) {
//             if (collision(tile, part))
//                 return true;
//         }
//         return false;
//     }

//     public boolean collision(Tile tile1, Tile tile2) {
//         return tile1.x == tile2.x && tile1.y == tile2.y;
//     }

//     public void move() {
//         // Check if the snake eats the food
//         if (collision(snakeHead, snakeFood)) {
//             // Add a new segment to the snake's body (at the last segment's position
//             // temporarily)
//             if (!snakeBody.isEmpty()) {
//                 Tile lastPart = snakeBody.get(snakeBody.size() - 1);
//                 snakeBody.add(new Tile(lastPart.x, lastPart.y));
//             } else {
//                 snakeBody.add(new Tile(snakeHead.x, snakeHead.y));
//             }
//             placeFood(); // Place new food
//         }

//         // Move the snake body (if it exists)
//         for (int i = snakeBody.size() - 1; i > 0; i--) {
//             Tile prevSnakePart = snakeBody.get(i - 1); // Get the part before the current one
//             snakeBody.get(i).x = prevSnakePart.x; // Copy its x-coordinate
//             snakeBody.get(i).y = prevSnakePart.y; // Copy its y-coordinate
//         }
//         if (!snakeBody.isEmpty()) {
//             // First body segment follows the head
//             snakeBody.get(0).x = snakeHead.x;
//             snakeBody.get(0).y = snakeHead.y;
//         }

//         // Move the snake head
//         snakeHead.x += velocityX;
//         snakeHead.y += velocityY;

//         // Check for collisions with the body
//         for (Tile snakePart : snakeBody) {
//             if (collision(snakeHead, snakePart)) {
//                 gameOver = true; // Game over if the head collides with the body
                
//             }
//         }

//         // Check for collisions with the walls
//         if (snakeHead.x < 0 || snakeHead.x >= boardWidth / tileSize ||
//                 snakeHead.y < 0 || snakeHead.y >= boardHeight / tileSize) {
//             gameOver = true; // Game over if the head hits the wall
//         }
//     }

//     private boolean isPaused = false;

//     // semua @override itu auto generated by vscode quick fix
//     @Override
//     public void actionPerformed(ActionEvent e) {
//         if (!isPaused) { // Only update game logic if not paused
//             move();
//             repaint(); // Redraw the game
//             if (gameOver) {
//                 gameLoop.stop();
//                 endScreen.setVisible(true);
//                 //JOptionPane.showMessageDialog(this, "Game Over!", "Snake Game", JOptionPane.INFORMATION_MESSAGE);
//             }
//         }
//     }

//     @Override
//     public void keyPressed(KeyEvent e) {
//         if (e.getKeyCode() == KeyEvent.VK_P) { // 'P' key to pause/unpause
//             isPaused = !isPaused; // Toggle pause state
//         } else if (!isPaused) { // Only process movement keys if not paused
//             if (e.getKeyCode() == KeyEvent.VK_W && velocityY != 1) {
//                 velocityX = 0;
//                 velocityY = -1;
//             } else if (e.getKeyCode() == KeyEvent.VK_S && velocityY != -1) {
//                 velocityX = 0;
//                 velocityY = 1;
//             } else if (e.getKeyCode() == KeyEvent.VK_D && velocityX != -1) {
//                 velocityX = 1;
//                 velocityY = 0;
//             } else if (e.getKeyCode() == KeyEvent.VK_A && velocityX != 1) {
//                 velocityX = -1;
//                 velocityY = 0;
//             }
//             if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
//                 velocityX = 0;
//                 velocityY = -1;
//             } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
//                 velocityX = 0;
//                 velocityY = 1;
//             } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
//                 velocityX = 1;
//                 velocityY = 0;
//             } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
//                 velocityX = -1;
//                 velocityY = 0;
//             }
//         }
//     }

//     @Override
//     public void keyTyped(KeyEvent e) { // pokok e ini dua harus ada nek gk code e gk bisa jalan
//     }

//     @Override
//     public void keyReleased(KeyEvent e) { // tapi karena aku gk butuh jadine fuction e gk dikasi instruction ato apa apa
//                                           // gitu
//     }

// }