
import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class splitwindow {
    public splitwindow(){
        //jadi untuk bisa menggabung snake and wasd aku harus pake split screen and refrector, itu kek e yg namane
        //extefnds jpanel di public wasdTest nya and nanti di sini makek JSpliPane buat gabungin kedua panel di jframe baeru
        //and nanti harus aku ganti manu ne biar bisa nge launch split window daripada snake e langsung
        
        JFrame frame = new JFrame("Combined WASD and Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);

        // Create instances of the panels (ensure these classes extend JPanel)
        wasdTest wasd = new wasdTest();    // WASD game panel
        SnekGame snek = new SnekGame();   // Snake game panel

        // Use a JSplitPane to divide the window
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, wasd, snek);
        splitPane.setDividerLocation(600); // Set divider at the middle
        splitPane.setResizeWeight(0.5);    // Equal weight for resizing

        // Add the splitPane to the frame
        frame.add(splitPane);

        // Make the frame visible
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        // Run the SplitWindow
        new splitwindow();
    }

}
