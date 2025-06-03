import java.awt.*;

public class Triangle {
    float3 a;
    float3 b;
    float3 c;
    int color;

    // 3d triangle
    Triangle(float3 a, float3 b, float3 c, Color color) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.color = ColorHelper.ColorToRGB(color);
    }
    // 2d triangle
    Triangle(float2 a, float2 b, float2 c, Color color) {
        this.a = new float3(a);
        this.b = new float3(b);
        this.c = new float3(c);
        this.color = ColorHelper.ColorToRGB(color);
    }
}