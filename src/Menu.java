import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;
import javax.sound.sampled.*;

public class Menu {
    private JFrame frame;
    private Clip bgmClip;

    public Menu() {
        showMenu();
        
    }

    public void showMenu() {
        frame = new JFrame("Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 1200);
        frame.setLocationRelativeTo(null);

        // playBackgroundMusic();  masih gatau bakal dipake atau gak, kalo gaisa nemu wav yang cocok mending jangan dipake

        // untuk background image custom 
        JPanel backgroundPanel = new JPanel() {
            private ImageIcon gifIcon;

    {
        try {
            // Load the animated GIF using ImageIcon
            URL gifUrl = new URL("https://i.pinimg.com/originals/c7/d5/9d/c7d59ddc9346ba8d41f83de6718f7d57.gif");
            gifIcon = new ImageIcon(gifUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // timer biar gif nya nge loop, pake repaint
        new Timer(50, e -> repaint()).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gifIcon != null) {
            // Draw the current frame of the GIF as the background
            g.drawImage(gifIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

        };
        backgroundPanel.setLayout(new GridBagLayout()); // For proper positioning of components

        // Transparent panel for the title and buttons 
        JPanel panel = new JPanel(new GridBagLayout()); //coba belajar grid layout
        panel.setOpaque(false); // Make the panel transparent

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 1, 10, 1);

        // Title
        JLabel title = new JLabel("ThinkPlay"){
          
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Outline color (black in this case)
        g2d.setColor(Color.BLACK);
        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent()) / 2 - 10;

        // Draw the outline by drawing the text multiple times
        g2d.drawString(getText(), x - 2, y); // Left
        g2d.drawString(getText(), x + 2, y); // Right
        g2d.drawString(getText(), x, y - 2); // Top
        g2d.drawString(getText(), x, y + 2); // Bottom

        // Foreground color (white in this case)
        g2d.setColor(Color.WHITE);
        g2d.drawString(getText(), x, y);
            }
        };
        title.setFont(new Font("Arial", Font.BOLD, 150));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.white);
        gbc.gridx = -1;
        gbc.gridy = 1;  //belajar cara pake grid bag cons
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Buttons
        JButton snakeButton = new JButton("Play Snake");
        snakeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        snakeButton.setBackground(Color.white);
        snakeButton.setForeground(Color.black);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(snakeButton, gbc);

        JButton ticTacToeButton = new JButton("Play Tic Tac Toe");
        ticTacToeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        ticTacToeButton.setBackground(Color.white);
        ticTacToeButton.setForeground(Color.black);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(ticTacToeButton, gbc);

        JButton aboutButton = new JButton("About");
        aboutButton.setFont(new Font("Arial", Font.PLAIN, 20));
        aboutButton.setBackground(Color.white);
        aboutButton.setForeground(Color.black);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(aboutButton, gbc);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setBackground(Color.white);
        exitButton.setForeground(Color.black);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(exitButton, gbc);

        JButton confirmExit = new JButton("okay :(");
        confirmExit.setFont(new Font("Arial", Font.PLAIN, 20));
        confirmExit.setBackground(Color.white);
        confirmExit.setForeground(Color.black);
        confirmExit.setVisible(false);
        int clickButton = 5;
        gbc.gridx = 1;
        gbc.gridy = clickButton;
        panel.add(confirmExit, gbc);

        // Action Listeners for Buttons
        snakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSnakeScreen();
                // stopBackgroundMusic();
                frame.dispose();
            }
        });

        ticTacToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTicTacToeScreen();
                // stopBackgroundMusic();
                frame.dispose();
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAboutScreen();
                frame.dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitButton.setVisible(false);
                confirmExit.setVisible(true);
                // stopBackgroundMusic();
            
            }
        });
        confirmExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                // stopBackgroundMusic();
            
            }
        });

        title.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                snakeButton.setText("A Game");
                snakeButton.setBackground(Color.pink);
                snakeButton.setForeground(Color.white);
                ticTacToeButton.setText("Made by");
                ticTacToeButton.setBackground(Color.pink);
                ticTacToeButton.setForeground(Color.white);
                aboutButton.setText("Jason Tio & Dave Tristian");
                aboutButton.setBackground(Color.pink);
                aboutButton.setForeground(Color.white);
                exitButton.setText("Please Enjoy!");
                exitButton.setBackground(Color.pink);
                exitButton.setForeground(Color.white);
                confirmExit.setText("Goodbye!");
                confirmExit.setBackground(Color.pink);
                confirmExit.setForeground(Color.white);
                title.setBackground(Color.MAGENTA); // Change background color on hover
                title.setForeground(Color.ORANGE); // Change text color on hover
            }
        
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                snakeButton.setText("Play Snake");
                snakeButton.setBackground(UIManager.getColor("Button.background"));
                snakeButton.setForeground(Color.BLACK);
                ticTacToeButton.setText("Play Tic Tac Toe");
                ticTacToeButton.setBackground(UIManager.getColor("Button.background"));
                ticTacToeButton.setForeground(Color.BLACK);
                aboutButton.setText("About");
                aboutButton.setBackground(UIManager.getColor("Button.background"));
                aboutButton.setForeground(Color.BLACK);
                exitButton.setText("Exit");
                exitButton.setBackground(UIManager.getColor("Button.background"));
                exitButton.setForeground(Color.BLACK);
                confirmExit.setText("okay :(");
                confirmExit.setBackground(UIManager.getColor("Button.background"));
                confirmExit.setForeground(Color.BLACK);
                title.setBackground(UIManager.getColor("Button.background")); // Reset to default background
                title.setForeground(Color.BLACK); // Reset to default text color
            }
        });

            // Add hover effect for Snake Button
snakeButton.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        snakeButton.setBackground(Color.pink); // Change background color on hover
        snakeButton.setForeground(Color.WHITE); // Change text color on hover
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        snakeButton.setBackground(UIManager.getColor("Button.background")); // Reset to default background
        snakeButton.setForeground(Color.BLACK); // Reset to default text color
    }
});

// Add hover effect for Tic Tac Toe Button
ticTacToeButton.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        ticTacToeButton.setBackground(Color.pink); // Change background color on hover
        ticTacToeButton.setForeground(Color.WHITE); // Change text color on hover
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        ticTacToeButton.setBackground(UIManager.getColor("Button.background")); // Reset to default background
        ticTacToeButton.setForeground(Color.BLACK); // Reset to default text color
    }
});

// Add hover effect for About Button
aboutButton.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        aboutButton.setBackground(Color.pink); // Change background color on hover
        aboutButton.setForeground(Color.white); // Change text color on hover
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        aboutButton.setBackground(UIManager.getColor("Button.background")); // Reset to default background
        aboutButton.setForeground(Color.BLACK); // Reset to default text color
    }
});

// Add hover effect for Exit Button
exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        exitButton.setText("pls dont leave");
        exitButton.setBackground(Color.pink); // Change background color on hover
        exitButton.setForeground(Color.magenta); // Change text color on hover
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        exitButton.setText("Exit Button");
        exitButton.setBackground(UIManager.getColor("Button.background")); // Reset to default background
        exitButton.setForeground(Color.BLACK); // Reset to default text color
    }
});
confirmExit.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        confirmExit.setBackground(Color.pink); // Change background color on hover
        confirmExit.setForeground(Color.yellow); // Change text color on hover
        if(clickButton < 8){
            // int numb = 1;
            // clickButton + numb;
        }
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent evt) {
        confirmExit.setBackground(UIManager.getColor("Button.background")); // Reset to default background
        confirmExit.setForeground(Color.BLACK); // Reset to default text color
    }
});


        // Add the transparent panel to the background panel
        backgroundPanel.add(panel);

        // Set the background panel as the content pane
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }

    //     private void playBackgroundMusic() {  
    //     try {
    //         URL musicFile = new URL(""); // Replace with the path to your music file
    //         AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
    //         bgmClip = AudioSystem.getClip();
    //         bgmClip.open(audioStream);
    //         bgmClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    // private void stopBackgroundMusic() {
    //     if (bgmClip != null && bgmClip.isRunning()) {
    //         bgmClip.stop();
    //     }
    // }


    private void showSnakeScreen() {
        JFrame snakeFrame = new JFrame("Snake Game");
    snakeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    snakeFrame.setSize(1200, 1200);
    snakeFrame.setLocationRelativeTo(null);

    // Custom background with animated GIF
    JPanel backgroundPanel = new JPanel() {
        private ImageIcon gifIcon;

        {
            try {
                // Load the animated GIF for the Snake screen background
                URL gifUrl = new URL("https://camo.githubusercontent.com/bda9dc23e05abc414c18458ed7a24981ca5e80fb792beef42c0cfe68321777d5/68747470733a2f2f69302e77702e636f6d2f6172742e706978696c6172742e636f6d2f6662373434353865663730336166612e6769663f726573697a653d3330302532433330302673736c3d31");
                gifIcon = new ImageIcon(gifUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Timer for smooth animation
            new Timer(50, e -> repaint()).start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (gifIcon != null) {
                g.drawImage(gifIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    };
    backgroundPanel.setLayout(new GridBagLayout()); // Center the content

    // Content for the Snake Game screen
    JLabel titleLabel = new JLabel("Welcome to Snake Game!");
    titleLabel.setFont(new Font("Roboto", Font.BOLD, 36));
    titleLabel.setForeground(Color.GREEN);
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JLabel descLabel = new JLabel("<html><div style='text-align: center;'>\"\r\n" + 
                "         \"Use W, A, S, D or Arrow Keys to move. Collect apples, avoid walls!.<br>\"\r\n" + 
                "         \"Press the button at the bottom or press P at your keyboard to Pause the game!\"\r\n" + 
                "         \"</div></html>");
    descLabel.setFont(new Font("Roboto", Font.PLAIN, 24));
    descLabel.setForeground(Color.RED);
    descLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JButton startButton = new JButton("Start Game");
    startButton.setFont(new Font("Arial", Font.BOLD, 20));
    startButton.setBackground(Color.WHITE);
    startButton.setForeground(Color.BLACK);

    // Start button action listener
    startButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SnekGame(Menu.this); // Launch the Snake Game
            snakeFrame.dispose(); // Close the current screen
        }
    });

    // Layout for content
    JPanel contentPanel = new JPanel(new GridLayout(3, 1, 20, 20));
    contentPanel.setOpaque(false); // Make the panel transparent
    contentPanel.add(titleLabel);
    contentPanel.add(descLabel);
    contentPanel.add(startButton);

    backgroundPanel.add(contentPanel); // Add content to the background

    // Finalize the frame
    snakeFrame.setContentPane(backgroundPanel);
    snakeFrame.setVisible(true);

    }

    private void showTicTacToeScreen() {
        JFrame ticTacToeFrame = new JFrame("Tic Tac Toe");
        ticTacToeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ticTacToeFrame.setSize(1200, 1200);
        ticTacToeFrame.setLocationRelativeTo(null);
        
        JPanel backgroundPanel = new JPanel() {
            private ImageIcon gifIcon;
    
            {
                try {
                    // Load the animated GIF
                    URL gifUrl = new URL("https://img.itch.zone/aW1nLzIxNzQ2MzAuZ2lm/original/uOr674.gif");
                    gifIcon = new ImageIcon(gifUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                // Timer to repaint for animated GIF
                new Timer(50, e -> repaint()).start();
            }
    
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (gifIcon != null) {
                    g.drawImage(gifIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new GridBagLayout()); // Center the content

        JPanel ticTacToePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        ticTacToePanel.setOpaque(false);


        JLabel tutorialLabel = new JLabel("Tutorial: Play by clicking cells to form a line of three!");
        tutorialLabel.setForeground(Color.white);
        tutorialLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        tutorialLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TicTacToe();
                ticTacToeFrame.dispose();
            }
        });

        ticTacToePanel.add(tutorialLabel);
        ticTacToePanel.add(startButton);

        backgroundPanel.add(ticTacToePanel);

        ticTacToeFrame.setContentPane(backgroundPanel);
        ticTacToeFrame.setVisible(true);

    }

    private void showAboutScreen() {
        JFrame aboutFrame = new JFrame("About");
        aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aboutFrame.setSize(1200, 1200);
        aboutFrame.setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            private ImageIcon gifIcon;
    
            {
                try {
                    // Load the animated GIF
                    URL gifUrl = new URL("https://i.gifer.com/2FxK.gif");
                    gifIcon = new ImageIcon(gifUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                // Timer to repaint for animated GIF
                new Timer(50, e -> repaint()).start();
            }
    
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (gifIcon != null) {
                    g.drawImage(gifIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new GridBagLayout()); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 1, 1, 10); //ini buat nge center button e tapi kok gaisa
        gbc.gridx = 0;              //nyerah aku plis
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel aboutLabel = new JLabel("<html><div style='text-align: center;'>"
        + "ThinkPlay: A simple game suite featuring Snake and Tic Tac Toe.<br>"
        + "This game contains a total of " + (81 + 541 + 446 + 192) + " lines of code."
        + "</div></html>");
        aboutLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        aboutLabel.setForeground(Color.white);
        aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 18));
        backButton.setPreferredSize(new Dimension(200, 40)); 
        backButton.addActionListener(e -> {
        aboutFrame.dispose();
        showMenu();
    });

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        contentPanel.add(aboutLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); //spacing diantara button and label
        contentPanel.add(backButton,gbc);  //haruse disini buat nge center button e tapi gaisa yowes

        // buat nge center label dll 
        backgroundPanel.add(contentPanel, gbc);

        aboutFrame.setContentPane(backgroundPanel);
        aboutFrame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        new Menu();
    }
}
