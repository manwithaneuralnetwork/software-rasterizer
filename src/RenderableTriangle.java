import java.awt.image.BufferedImage;

public class RenderableTriangle {
    public Triangle triangle;
    public BufferedImage texture;

    public RenderableTriangle(Triangle triangle, BufferedImage texture) {
        this.triangle = triangle;
        this.texture = texture;
    }
}
