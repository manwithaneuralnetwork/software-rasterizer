import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Maths {
    // vector operations
    public static float Dot(float2 a, float2 b) {
        return a.x * b.x + a.y * b.y;
    }
    public static float Dot(float3 a, float3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }
    public static float2 Perpendicular(float2 v) {
        // 90° clockwise rotation
        return new float2(v.y, -v.x);
    }
    public static float2 Opposite(float2 v) {
        return new float2(-v.x, -v.y);
    }

    // returns a vector from b to a
    public static float2 GetVector(float2 a, float2 b) {
        return new float2(a.x - b.x, a.y - b.y);
    }

    public static boolean IsPointOnRightSideOfLine(float2 a, float2 b, float2 p) {
        float2 ap = GetVector(a, p);
        float2 abPerp = Perpendicular(GetVector(a, b));
        return Dot(ap, abPerp) >= 0;
    }

    public static boolean IsPointWithinTriangle(float2 p, Triangle t) {
        float2 a = t.a.toFloat2();
        float2 b = t.b.toFloat2();
        float2 c = t.c.toFloat2();
        boolean ab = IsPointOnRightSideOfLine(a, b, p);
        boolean bc = IsPointOnRightSideOfLine(b, c, p);
        boolean ca = IsPointOnRightSideOfLine(c, a, p);
        return ab && bc && ca;
    }

    public static float3 Normalize(float3 v) {
        float magnitude = v.magnitude();
        if (magnitude == 0) {
            return new float3(0, 0, 0);
        }
        return new float3(v.x/magnitude, v.y/magnitude, v.z/magnitude);
    }
    public static float3 Cross(float3 a, float3 b) {
        return new float3(
            a.y * b.z - a.z * b.y,
            a.z * b.x - a.x * b.z,
            a.x * b.y - a.y * b.x
        );
    }

    public static float[][] Transpose(float[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        float[][] result = new float[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }

        return result;
    }

    public static Triangle TransformTriangleToCameraSpace(Triangle t, float[][] viewMatrix) {
        float3 a = TransformPoint(viewMatrix, t.a);
        float3 b = TransformPoint(viewMatrix, t.b);
        float3 c = TransformPoint(viewMatrix, t.c);

        return new Triangle(a, b, c, t.color);
    }

    public static float3 TransformPoint(float[][] m, float3 v) {
        float x = v.x * m[0][0] + v.y * m[0][1] + v.z * m[0][2] + m[0][3];
        float y = v.x * m[1][0] + v.y * m[1][1] + v.z * m[1][2] + m[1][3];
        float z = v.x * m[2][0] + v.y * m[2][1] + v.z * m[2][2] + m[2][3];
        return new float3(x, y, z);
    }

    public static float3 WorldToScreenSpace(float3 p, Camera c, BufferedImage image) {
        float fovRadians = (float) Math.toRadians(c.fov);
        float d = 1.0f / (float) Math.tan(fovRadians / 2.0f);

        float x = (p.x / -p.z) * d;
        float y = (p.y / -p.z) * d;

        float screenX = (x + 1) * 0.5f * image.getWidth();
        float screenY = (1 - y) * 0.5f * image.getHeight();

        return new float3(screenX, screenY, -p.z);
    }

    public static Triangle MapTriangleToScreen(Triangle triangle, Camera cam, BufferedImage image) {
        Triangle t = TransformTriangleToCameraSpace(triangle, cam.getViewMatrix());
        float3 a = WorldToScreenSpace(t.a, cam, image);
        float3 b = WorldToScreenSpace(t.b, cam, image);
        float3 c = WorldToScreenSpace(t.c, cam, image);

        return new Triangle(a, b, c, t.color);
    }

    public static float3 RotateX(float3 v, float angleRad) {
        float cos = (float)Math.cos(angleRad);
        float sin = (float)Math.sin(angleRad);
        return new float3(
                v.x,
                v.y * cos - v.z * sin,
                v.y * sin + v.z * cos
        );
    }

    public static float3 RotateY(float3 v, float angleRad) {
        float cos = (float)Math.cos(angleRad);
        float sin = (float)Math.sin(angleRad);
        return new float3(
                v.x * cos + v.z * sin,
                v.y,
                -v.x * sin + v.z * cos
        );
    }

}