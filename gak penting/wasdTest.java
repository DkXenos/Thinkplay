import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class wasdTest extends JPanel implements KeyListener{
    private JLabel labelW, labelA, labelS, labelD;

            public wasdTest(){
        JFrame frame = new JFrame("Window");
        JPanel backPanel = new JPanel();

        int width = 500;
        int height = 500;

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backPanel.setLayout(new GridBagLayout());
        frame.add(backPanel);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        backPanel.add(panel);

         labelW = new JLabel("W");
         labelA = new JLabel("A");
         labelS = new JLabel("S");
         labelD = new JLabel("D");

        labelW.setFont(new Font("Arial", Font.BOLD, 150));
        labelA.setFont(new Font("Arial", Font.BOLD, 150));
        labelS.setFont(new Font("Arial", Font.BOLD, 150));
        labelD.setFont(new Font("Arial", Font.BOLD, 150));

        labelW.setForeground(Color.WHITE);
        labelA.setForeground(Color.WHITE);
        labelS.setForeground(Color.WHITE);
        labelD.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(labelW, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelA, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(labelS, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(labelD, gbc);

        frame.setVisible(true); 
        frame.setLocationRelativeTo(null); 

        frame.addKeyListener(this);
        frame.setFocusable(true); // Make frame focusable
        frame.requestFocusInWindow();
        
    }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                labelW.setForeground(Color.GREEN);
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                labelA.setForeground(Color.GREEN);
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                labelS.setForeground(Color.GREEN);
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                labelD.setForeground(Color.GREEN);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                labelW.setForeground(Color.WHITE);
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                labelS.setForeground(Color.WHITE);
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                labelD.setForeground(Color.WHITE);
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                labelA.setForeground(Color.WHITE);
            }
        }
        @Override
        public void keyTyped(KeyEvent e) {
        
        }
    }
       
    
    

