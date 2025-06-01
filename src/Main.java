import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {
    public static final int WIDTH = 512;
    public static final int HEIGHT = 512;

    static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

    public static void main(String[] args) {
        Rasterizer.createTestImage(image);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rasterizer Output");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ImagePanel panel = new ImagePanel(image);
            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            new Timer(16, e -> {
                Rasterizer.updateImage(image);
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
}