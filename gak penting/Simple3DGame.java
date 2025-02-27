import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Simple3DGame extends JPanel implements KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    // Character position
    private double charX = 0;
    private double charY = 0;
    private double charZ = 0;

    // Room size
    private final double roomSize = 5;

    // Perspective parameters
    private final double scale = 200;
    private final double distance = 5;

    public Simple3DGame() {
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Clear screen
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        // Set drawing color
        g2d.setColor(Color.WHITE);

        // Center of the screen
        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;

        // Draw the room (a simple cube)
        drawCube(g2d, -roomSize, -roomSize, -roomSize, roomSize, roomSize, roomSize, centerX, centerY);

        // Draw the character (a smaller cube)
        drawCube(g2d, charX - 0.5, charY - 0.5, charZ - 0.5, charX + 0.5, charY + 0.5, charZ + 0.5, centerX, centerY);
    }

    private void drawCube(Graphics2D g2d, double x1, double y1, double z1, double x2, double y2, double z2, int centerX, int centerY) {
        // Define the vertices of the cube
        double[][] vertices = {
                {x1, y1, z1}, {x2, y1, z1}, {x2, y2, z1}, {x1, y2, z1},
                {x1, y1, z2}, {x2, y1, z2}, {x2, y2, z2}, {x1, y2, z2}
        };

        // Define the edges of the cube (pairs of vertex indices)
        int[][] edges = {
                {0, 1}, {1, 2}, {2, 3}, {3, 0},
                {4, 5}, {5, 6}, {6, 7}, {7, 4},
                {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };

        // Project each vertex onto the 2D screen
        int[][] projected = new int[vertices.length][2];
        for (int i = 0; i < vertices.length; i++) {
            double[] vertex = vertices[i];
            double perspective = scale / (vertex[2] + distance);
            projected[i][0] = (int) (vertex[0] * perspective) + centerX;
            projected[i][1] = (int) (vertex[1] * perspective) + centerY;
        }

        // Draw the edges of the cube
        for (int[] edge : edges) {
            int x1Screen = projected[edge[0]][0];
            int y1Screen = projected[edge[0]][1];
            int x2Screen = projected[edge[1]][0];
            int y2Screen = projected[edge[1]][1];
            g2d.drawLine(x1Screen, y1Screen, x2Screen, y2Screen);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Handle character movement with WASD keys
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: // Move forward
                charZ -= 0.1;
                break;
            case KeyEvent.VK_S: // Move backward
                charZ += 0.1;
                break;
            case KeyEvent.VK_A: // Move left
                charX -= 0.1;
                break;
            case KeyEvent.VK_D: // Move right
                charX += 0.1;
                break;
        }

        // Constrain character to the room bounds
        charX = Math.max(-roomSize + 0.5, Math.min(roomSize - 0.5, charX));
        charZ = Math.max(-roomSize + 0.5, Math.min(roomSize - 0.5, charZ));

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No action needed on key release
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed on key typed
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple 3D Game");
        Simple3DGame game = new Simple3DGame();
        frame.add(game);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
