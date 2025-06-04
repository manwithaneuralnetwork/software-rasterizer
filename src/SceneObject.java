import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class SceneObject {
    public ObjModel model;
    public float3 position;
    public float3 rotation;
    public BufferedImage texture;
    public float scale = 1.0f;

    public SceneObject(ObjModel model, float3 position, String texture) {
        this.model = model;
        this.position = position;
        this.rotation = new float3(0, 0, 0);
        try{
            this.texture = ImageIO.read(new File(texture));
        }
        catch (Exception e){
            System.out.println("Error loading texture");
        }
    }

    public ArrayList<Triangle> getTransformedTriangles() {
        ArrayList<Triangle> transformed = new ArrayList<>();

        for (Triangle t : model.triangles) {
            float3 a = t.a;
            float3 b = t.b;
            float3 c = t.c;

            // Apply rotation
            a = Maths.RotateY(a, rotation.y);
            b = Maths.RotateY(b, rotation.y);
            c = Maths.RotateY(c, rotation.y);

            a = Maths.RotateX(a, rotation.x);
            b = Maths.RotateX(b, rotation.x);
            c = Maths.RotateX(c, rotation.x);

            // Apply translation
            a = a.add(position);
            b = b.add(position);
            c = c.add(position);

            transformed.add(new Triangle(a, b, c, t.uvA, t.uvB, t.uvC, t.color));
        }

        return transformed;
    }

    public void yaw(float angle) {
        this.rotation.y += angle;
    }

    public void pitch(float angle) {
        this.rotation.x += angle;
    }
}