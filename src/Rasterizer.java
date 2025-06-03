import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Rasterizer {
    public static void Render(Camera cam, BufferedImage image, ArrayList<Triangle> triangles) {
        ArrayList<Triangle> cameraSpaceTriangles = new ArrayList<>();
        for (Triangle t : triangles) {
            Triangle cameraT = Maths.TransformTriangleToCameraSpace(t, cam.getViewMatrix());
            cameraSpaceTriangles.add(cameraT);
        }

        cameraSpaceTriangles.sort((t1, t2) -> {
            float z1 = (t1.a.z + t1.b.z + t1.c.z) / 3f;
            float z2 = (t2.a.z + t2.b.z + t2.c.z) / 3f;
            return Float.compare(z1, z2);
        });

        ArrayList<Triangle> projectedTriangles = new ArrayList<>();
        for (Triangle t : cameraSpaceTriangles) {
            Triangle projected = Maths.MapTriangleToScreen(t, cam, image);
            projectedTriangles.add(projected);
        }

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                float2 p = new float2(x, y);
                for (Triangle t : projectedTriangles) {
                    if (Maths.IsPointWithinTriangle(p, t)) {
                        image.setRGB(x, y, t.color);
                    }
                }
            }
        }
    }
}