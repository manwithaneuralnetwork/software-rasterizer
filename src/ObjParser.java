import java.io.*;
import java.util.*;
import java.awt.Color;

public class ObjParser {

    public static ObjModel loadFromOBJ(String filePath) throws IOException {
        ObjModel model = new ObjModel();
        ArrayList<float3> vertices = model.vertices;
        ArrayList<Triangle> triangles = model.triangles;

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        Random rng = new Random();

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("v ")) {
                String[] tokens = line.split("\\s+");
                float x = Float.parseFloat(tokens[1]);
                float y = Float.parseFloat(tokens[2]);
                float z = Float.parseFloat(tokens[3]);
                vertices.add(new float3(x, y, z));
            } else if (line.startsWith("f ")) {
                String[] tokens = line.split("\\s+");
                int[] indices = new int[tokens.length - 1];
                for (int i = 1; i < tokens.length; i++) {
                    String token = tokens[i].split("/")[0];
                    indices[i - 1] = Integer.parseInt(token) - 1;
                }

                for (int i = 1; i < indices.length - 1; i++) {
                    float3 a = vertices.get(indices[0]);
                    float3 b = vertices.get(indices[i]);
                    float3 c = vertices.get(indices[i + 1]);

                    int color = getRandomBrightColor(rng);
                    triangles.add(new Triangle(a, b, c, color));
                }
            }
        }

        reader.close();
        return model;
    }

    private static int getRandomBrightColor(Random rng) {
        int r = 128 + rng.nextInt(128);
        int g = 128 + rng.nextInt(128);
        int b = 128 + rng.nextInt(128);
        return (r << 16) | (g << 8) | b; // 0xRRGGBB
    }
}
