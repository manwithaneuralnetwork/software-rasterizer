import java.io.*;
import java.util.*;
import java.awt.Color;

public class ObjParser {

    public static ObjModel loadFromOBJ(String filePath) throws IOException {
        ObjModel model = new ObjModel();
        ArrayList<float3> vertices = model.vertices;
        ArrayList<float2> uvs = model.uvs;
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
            } else if (line.startsWith("vt ")) {
                String[] tokens = line.split("\\s+");
                float u = Float.parseFloat(tokens[1]);
                float v = Float.parseFloat(tokens[2]);
                uvs.add(new float2(u, v));
            } else if (line.startsWith("f ")) {
                String[] tokens = line.split("\\s+");
                int[] vIndices = new int[tokens.length - 1];
                int[] uvIndices = new int[tokens.length - 1];

                for (int i = 1; i < tokens.length; i++) {
                    String[] parts = tokens[i].split("/");
                    vIndices[i - 1] = Integer.parseInt(parts[0]) - 1;
                    uvIndices[i - 1] = (parts.length > 1 && !parts[1].isEmpty()) ? Integer.parseInt(parts[1]) - 1 : -1;
                }

                for (int i = 1; i < vIndices.length - 1; i++) {
                    float3 a = vertices.get(vIndices[0]);
                    float3 b = vertices.get(vIndices[i]);
                    float3 c = vertices.get(vIndices[i + 1]);

                    float2 uvA = uvIndices[0] >= 0 ? uvs.get(uvIndices[0]) : new float2(0, 0);
                    float2 uvB = uvIndices[i] >= 0 ? uvs.get(uvIndices[i]) : new float2(0, 0);
                    float2 uvC = uvIndices[i + 1] >= 0 ? uvs.get(uvIndices[i + 1]) : new float2(0, 0);

                    int color = getRandomBrightColor(rng); // still needed if lighting fallback or debug
                    triangles.add(new Triangle(a, b, c, uvA, uvB, uvC, color));
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
