import java.awt.image.BufferedImage;

public class Rasterizer {
    public static void createTestImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = x;
                int g = 255 - y;
                int b = (x + y) / 2;
                int rgb = (r << 16) | (g << 8) | b;
                image.setRGB(x, y, rgb);
            }
        }
    }
}
