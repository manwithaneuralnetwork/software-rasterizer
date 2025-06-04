import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Rasterizer {
    public static void Render(Camera cam, BufferedImage image, ArrayList<SceneObject> scene) {

        // transform from world to camera space
        ArrayList<Triangle> cameraSpaceTriangles = new ArrayList<>();
        float[][] camViewMatrix = cam.getViewMatrix();
        for (SceneObject sceneObject : scene) {
            ArrayList<Triangle> triangles = sceneObject.getTransformedTriangles();
            for (Triangle t : triangles) {
                Triangle cameraT = Maths.TransformTriangleToCameraSpace(t, camViewMatrix);
                if (Maths.IsTriangleBackFace(cameraT, cam)) continue;
                cameraSpaceTriangles.add(cameraT);
            }
        }

        // sort triangles to draw the closest on top
        cameraSpaceTriangles.sort((t1, t2) -> {
            float z1 = (t1.a.z + t1.b.z + t1.c.z) / 3f;
            float z2 = (t2.a.z + t2.b.z + t2.c.z) / 3f;
            return Float.compare(z2, z1);
        });

        // project from camera space to screen
        ArrayList<Triangle> projectedTriangles = new ArrayList<>();
        for (Triangle t : cameraSpaceTriangles) {
            Triangle projected = Maths.MapTriangleToScreen(t, cam, image);
            projectedTriangles.add(projected);
        }

        // clear screen once
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                image.setRGB(x, y, ColorHelper.ColorToRGB(Color.BLACK));
            }
        }

        // rasterize
        for (Triangle t : projectedTriangles) {
            int minY = (int) Math.max(Math.min(Math.min(t.a.y, t.b.y), t.c.y), 0);
            int minX = (int) Math.max(Math.min(Math.min(t.a.x, t.b.x), t.c.x), 0);
            int maxY = (int) Math.min(Math.max(Math.max(t.a.y, t.b.y), t.c.y), image.getHeight() - 1);
            int maxX = (int) Math.min(Math.max(Math.max(t.a.x, t.b.x), t.c.x), image.getWidth() - 1);
            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    float2 p = new float2(x, y);
                    if (Maths.IsPointWithinTriangle(p, t)) {
                        image.setRGB(x, y, t.color);
                    }
                }
            }
        }
    }
}