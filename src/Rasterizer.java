import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Rasterizer {
    public static void Render(Camera cam, BufferedImage image, ArrayList<SceneObject> scene) {

        // transform from world to camera space
        ArrayList<RenderableTriangle> cameraSpaceTriangles = new ArrayList<>();
        float[][] camViewMatrix = cam.getViewMatrix();

        for (SceneObject sceneObject : scene) {
            ArrayList<Triangle> triangles = sceneObject.getTransformedTriangles();
            for (Triangle t : triangles) {
                Triangle cameraT = Maths.TransformTriangleToCameraSpace(t, camViewMatrix);
                if (Maths.IsTriangleBackFace(cameraT, cam)) continue;
                cameraSpaceTriangles.add(new RenderableTriangle(cameraT, sceneObject.texture));
            }
        }

        // sort triangles to draw the closest on top
        cameraSpaceTriangles.sort((rt1, rt2) -> {
            float z1 = (rt1.triangle.a.z + rt1.triangle.b.z + rt1.triangle.c.z) / 3f;
            float z2 = (rt2.triangle.a.z + rt2.triangle.b.z + rt2.triangle.c.z) / 3f;
            return Float.compare(z2, z1);
        });

        // project from camera space to screen
        ArrayList<RenderableTriangle> projectedTriangles = new ArrayList<>();
        for (RenderableTriangle rt : cameraSpaceTriangles) {
            Triangle projected = Maths.MapTriangleToScreen(rt.triangle, cam, image);
            projectedTriangles.add(new RenderableTriangle(projected, rt.texture));
        }

        // clear screen once
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                image.setRGB(x, y, ColorHelper.ColorToRGB(Color.BLACK));
            }
        }

        // rasterize
        for (RenderableTriangle rt : projectedTriangles) {
            Triangle t = rt.triangle;
            BufferedImage texture = rt.texture;
            int minY = (int) Math.max(Math.min(Math.min(t.a.y, t.b.y), t.c.y), 0);
            int minX = (int) Math.max(Math.min(Math.min(t.a.x, t.b.x), t.c.x), 0);
            int maxY = (int) Math.min(Math.max(Math.max(t.a.y, t.b.y), t.c.y), image.getHeight() - 1);
            int maxX = (int) Math.min(Math.max(Math.max(t.a.x, t.b.x), t.c.x), image.getWidth() - 1);
            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    float2 p = new float2(x, y);
                    if (Maths.IsPointWithinTriangle(p, t)) {
                        float[] bary = Maths.ComputeBarycentric(p, t.a.toFloat2(), t.b.toFloat2(), t.c.toFloat2());

                        float izA = 1.0f / t.a.z;
                        float izB = 1.0f / t.b.z;
                        float izC = 1.0f / t.c.z;

                        float uA = t.uvA.x * izA;
                        float uB = t.uvB.x * izB;
                        float uC = t.uvC.x * izC;

                        float vA = t.uvA.y * izA;
                        float vB = t.uvB.y * izB;
                        float vC = t.uvC.y * izC;

                        float iz = bary[0] * izA + bary[1] * izB + bary[2] * izC;
                        float u = (bary[0] * uA + bary[1] * uB + bary[2] * uC) / iz;
                        float v = (bary[0] * vA + bary[1] * vB + bary[2] * vC) / iz;

                        int texX = (int)(u * texture.getWidth());
                        int texY = (int)((1 - v) * texture.getHeight());

                        texX = Math.max(0, Math.min(texture.getWidth() - 1, texX));
                        texY = Math.max(0, Math.min(texture.getHeight() - 1, texY));

                        int color = texture.getRGB(texX, texY);
                        image.setRGB(x, y, color);
                    }
                }
            }
        }
    }
}