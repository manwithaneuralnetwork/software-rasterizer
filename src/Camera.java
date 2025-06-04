public class Camera {
    float3 position;
    float3 forward;
    float fov;

    Camera(float3 position, float3 forward, float fov) {
        this.position = position;
        this.forward = forward;
        this.fov = fov;
    }
    Camera() {
        this.position = new float3(0.0f, 0.0f, 5.0f);
        this.forward = new float3(0.0f, 0.0f, -1.0f);
        this.fov = 70;
    }

    public float[][] getViewMatrix() {
        float3 forward = Maths.Normalize(this.forward);
        float3 up = new float3(0.0f, 1.0f, 0.0f);
        float3 right = Maths.Normalize(Maths.Cross(forward, up));
        up = Maths.Cross(forward, right);

        float tx = -Maths.Dot(right, position);
        float ty = -Maths.Dot(up, position);
        float tz = -Maths.Dot(forward, position);

        return new float[][]{
                {right.x, up.x, forward.x, 0},
                {right.y, up.y, forward.y, 0},
                {right.z, up.z, forward.z, 0},
                {tx, ty, tz, 1}
        };
    }
}