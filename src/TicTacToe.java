import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardHeight  = 650; // 50px e buat ngasi tau turn e mana and winner game e
    int boardWidth = 600;
    GameOver s = new GameOver();

    JFrame frame = new JFrame("game tiktekto");
    JLabel textLabel = new JLabel();
    JPanel textPanel =  new JPanel();
    JPanel boardPanel = new JPanel();

    JButton pause = new JButton("Menu");
    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turn = 0;

    public TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);
  
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);
        frame.add(pause, BorderLayout.SOUTH);
        pause.setPreferredSize(new Dimension(200, 75));        
        pause.setVisible(false);

         for (int i = 0; i < 3; i++){  //untuk membuat tile and dimasukin ke dlm panel
            //rows
            for (int j = 0; j < 3; j++){
                //collumns
                JButton tile = new JButton("");
                board[i][j] = tile;
                boardPanel.add(tile);
                
                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                //tile.setText(currentPlayer);

                tile.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JButton tile = (JButton) e.getSource();
                        if (gameOver) return;
                        if (tile.getText() == ""){
                            tile.setText(currentPlayer);
                            turn++;
                            checkWinner();
                                if (!gameOver){
                                    currentPlayer = currentPlayer == playerX ? playerO : playerX;
                                    textLabel.setText(currentPlayer + "'s Turn");
                                }

                              
                            }
                    }
                });
            }
         }
         pause.addActionListener(new ActionListener() {
        
            
            @Override
            public void actionPerformed(ActionEvent e) {
                s.GameOver();
                frame.dispose();
            }
        });
    }

    
    void checkWinner(){
        //horizontal win
        for (int i = 0; i < 3; i++){
           if (board[i][0].getText() == "") continue;

           if (board[i][0].getText() == board[i][1].getText() 
           && board[i][1].getText() == board[i][2].getText()){
            for (int k = 0; k < 3; k++){ // make sure that the for loop doesnt get mixed up or flipped, the correct one is < 
                setWinner(board[i][k]);
            }
            boardHeight = 725;
            frame.setSize(boardWidth, boardHeight);
            pause.setVisible(true);
            // frame.dispose();
            // s.GameOver();  
            gameOver = true;
            return;
           }
        }
        //vertical win
        for (int col = 0; col < 3; col++) {
            if(board[0][col].getText() == "") continue;

            if (board[0][col].getText() == board[1][col].getText()
            && board[1][col].getText() == board[2][col].getText()) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][col]);
            }
            boardHeight = 725;
            frame.setSize(boardWidth, boardHeight);
            pause.setVisible(true);
                gameOver =  true;
                // frame.dispose();
                // s.GameOver();
                return; 
            }
        }
        //diagon win condition
        if(board[0][0].getText() == board[1][1].getText() &&
        board[1][1].getText() == board[2][2].getText() && 
        board[0][0].getText() != ""){
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            boardHeight = 725;
            frame.setSize(boardWidth, boardHeight);
            pause.setVisible(true);
            return;
        }
        //reverse diagonal
        if(board[0][2].getText() == board[1][1].getText() &&
        board[1][1].getText() == board[2][0].getText() &&
        board[0][2].getText() != ""){
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][2-i]);
            }
            gameOver = true;
            boardHeight = 725;
            frame.setSize(boardWidth, boardHeight);
            pause.setVisible(true);
            return;
        }
        //draw
        if(turn == 9){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    setDraw(board[i][j]);
                }
            }
            gameOver = true;
            boardHeight = 725;
            frame.setSize(boardWidth, boardHeight);
            pause.setVisible(true);
            return; 
        }
    }
    void setDraw(JButton tile){
         tile.setForeground(Color.orange);
         tile.setBackground(Color.black);
         textLabel.setText("Draw");
    }
    void setWinner(JButton tile){
         tile.setForeground(Color.green);
         tile.setBackground(Color.black);
         textLabel.setText(currentPlayer + " Wins");
        }
    
}

 