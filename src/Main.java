import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static final int WIDTH = 512;
    public static final int HEIGHT = 512;

    static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
    static Camera camera = new Camera();
    static ObjModel cube;

    static {
        try {
            cube = ObjParser.loadFromOBJ("resources/cube.obj");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        for (Triangle t : cube.triangles) {
            float3 offset = new float3(0, 0, -1);
            t.a = t.a.add(offset);
            t.b = t.b.add(offset);
            t.c = t.c.add(offset);
        }
        Rasterizer.Render(camera, image, cube.triangles);
        setupScreen();
    }

    static void setupScreen() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rasterizer Output");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ImagePanel panel = new ImagePanel(image);
            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

//            new Timer(16, e -> {
//                Rasterizer.Render(camera, image, cube.triangles);
//                panel.repaint();
//            }).start();
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
}