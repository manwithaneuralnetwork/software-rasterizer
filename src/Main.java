import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {
    public static final int WIDTH = 256;
    public static final int HEIGHT = 256;

    static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    public static void main(String[] args) {
        Rasterizer.createTestImage(image);
        System.out.println("Test");

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Raster Output");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JComponent panel = new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, null);
                }

                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(WIDTH, HEIGHT);
                }
            };

            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
