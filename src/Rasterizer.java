import java.awt.image.BufferedImage;

public class Rasterizer {
    public static void createTestImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = x*255/width;
                int g = y*255/height;
                int rgb = (r << 16) | (g << 8);
                image.setRGB(x, y, rgb);
            }
        }
    }


    public static void updateImage(BufferedImage image) {
    }
}
