import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class GameOver {

    public void GameOver(){
        int boardHeight = 600;
        int boardWidth = boardHeight;

        // Create JFrame
        JFrame frame = new JFrame("Game Over");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create JPanel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK); // Set background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add "Game Over" title
        JLabel failLabel = new JLabel("GAME OVER");
        failLabel.setFont(new Font("Arial", Font.BOLD, 50));
        failLabel.setForeground(Color.RED); // Set text color
        failLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        panel.add(failLabel, gbc);

        // Add "Try Again" button
        JButton tryAgainButton = new JButton("Back To Menu");
        tryAgainButton.setFont(new Font("Arial", Font.PLAIN, 20));
        tryAgainButton.setBackground(Color.GREEN);
        tryAgainButton.setForeground(Color.WHITE);
        tryAgainButton.setFocusPainted(false); // Remove focus outline
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Single column
        panel.add(tryAgainButton, gbc);

        // Add "Exit" button
        JButton exitButton = new JButton("Exit To Window");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(exitButton, gbc);

        // Add ActionListener for "Try Again" button
        tryAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu();
                
                frame.dispose(); // Close the Game Over screen
            }
        });

        // Add ActionListener for "Exit" button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the Game Over screen
            }
        });

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
        }  
    }

