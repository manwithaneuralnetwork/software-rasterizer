public class Maths {
    // vector operations
    public static float Dot(float2 a, float2 b) {
        return a.x * b.x + a.y * b.y;
    }
    public static float2 Perpendicular(float2 v) {
        // 90° clockwise rotation
        return new float2(v.y, -v.x);
    }
    public static float2 Opposite(float2 v) {
        return new float2(-v.x, -v.y);
    }

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
}