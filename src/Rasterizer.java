import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Rasterizer {

    // this creates a simple fullscreen gradient (early testing)
    public static void CreateTestImage(BufferedImage image) {
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

    // this fills in pixels within each triangle
    public static void Render(BufferedImage image, ArrayList<Triangle> triangles) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (Triangle t : triangles) {
                    float2 p = new float2(x, y);
                    if (Maths.IsPointWithinTriangle(p, t)) {
                        image.setRGB(x, y, t.color);
                    }
                }
            }
        }
    }
}
