public class float3 {
    public float x, y, z;

    public float3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float3(float2 f2) {
        this.x = f2.x;
        this.y = f2.y;
    }

    public float2 toFloat2(){
        return new float2(x, y);
    }
}