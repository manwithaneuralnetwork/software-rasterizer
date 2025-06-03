import java.awt.*;

public class ColorHelper {
    public static int ColorToRGB(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        return (r << 16) | (g << 8) | b;
    }
}
