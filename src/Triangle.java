import java.awt.*;

public class Triangle {
    float3 a, b, c;
    public float2 uvA, uvB, uvC;
    int color;

    Triangle(float3 a, float3 b, float3 c, float2 uvA, float2 uvB, float2 uvC, int color) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.uvA = uvA;
        this.uvB = uvB;
        this.uvC = uvC;
        this.color = color;
    }

    public String toString() {
        return "A: " + a.x + ", " + a.y + ", " + a.z + "\n"
                + "B: " + b.x + ", " + b.y + ", " + b.z + "\n"
                + "C: " + c.x + ", " + c.y + ", " + c.z + "\n";
    }
}