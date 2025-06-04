import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static final int WIDTH = 512;
    public static final int HEIGHT = 512;

    static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
    static Camera camera = new Camera();
    static ArrayList<SceneObject> scene = new ArrayList<>();
    static SceneObject obj;
    static Player player;

    static {
        try {
            ObjModel model = ObjParser.loadFromOBJ("resources/queen.obj");
            float3 pos = new float3(0.0f, -0.80f, -2.0f);
            obj = new SceneObject(model, pos, "resources/queentex.png");
            player = new Player(obj);
            scene.add(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Rasterizer.Render(camera, image, scene);
        setupScreen();
    }

    static void setupScreen() {
        SwingUtilities.invokeLater(() -> {
            // display setup
            JFrame frame = new JFrame("Rasterizer Output");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ImagePanel panel = new ImagePanel(image);
            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            setupPlayer(frame);

            // initiates frame generation
            new Timer(16, e -> {
                player.update();
                Rasterizer.Render(camera, image, scene);
                panel.repaint();
            }).start();
        });
    }

    static class ImagePanel extends JComponent {
        private final BufferedImage img;

        public ImagePanel(BufferedImage img) {
            this.img = img;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, null);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(img.getWidth(), img.getHeight());
        }
    }
    private static void setupPlayer(JFrame frame) {
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                player.keyPressed(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.keyReleased(e.getKeyCode());
            }
        });
    }
}