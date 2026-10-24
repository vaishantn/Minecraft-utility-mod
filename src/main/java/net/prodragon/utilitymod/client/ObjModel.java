package net.prodragon.utilitymod.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


public final class ObjModel {

    private final List<Vertex> vertices;

    private ObjModel(List<Vertex> vertices) {
        this.vertices = List.copyOf(vertices);
    }

    public List<Vertex> vertices() {
        return vertices;
    }

    public static ObjModel load(Reader source) throws IOException {

        List<float[]> positions = new ArrayList<>();
        List<float[]> textureCoordinates = new ArrayList<>();
        List<float[]> normals = new ArrayList<>();
        List<Vertex> vertices = new ArrayList<>();

        String currentMaterial = "default";

        try (BufferedReader reader = new BufferedReader(source)) {
            String line;
            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split("\\s+");

                switch (parts[0]) {

                    case "v":
                        positions.add(new float[]{
                                Float.parseFloat(parts[1]),
                                Float.parseFloat(parts[2]),
                                Float.parseFloat(parts[3])
                        });
                        break;
                    case "vt":
                        textureCoordinates.add(new float[]{
                                Float.parseFloat(parts[1]),
                                Float.parseFloat(parts[2])
                        });
                        break;

                    case "vn":
                        normals.add(new float[]{
                                Float.parseFloat(parts[1]),
                                Float.parseFloat(parts[2]),
                                Float.parseFloat(parts[3])
                        });
                        break;
                    case "usemtl":
                        if (parts.length > 1) {
                            currentMaterial = parts[1];
                        }
                        break;

                    case "f":

                        if (parts.length < 4) {
                            break;
                        }
                        Vertex first = parseVertex(
                                parts[1], positions, textureCoordinates, normals, currentMaterial
                        );

                        for (int i = 2; i < parts.length - 1; i++) {
                            Vertex second = parseVertex(
                                    parts[i], positions, textureCoordinates, normals, currentMaterial
                            );

                            Vertex third = parseVertex(

                                    parts[i + 1], positions, textureCoordinates, normals, currentMaterial
                            );

                            vertices.add(first);
                            vertices.add(second);
                            vertices.add(third);
                        }

                        break;


                }

            }
        }

        return new ObjModel(vertices);

    }

    private static Vertex parseVertex(
            String faceparts,
            List<float[]> positions,
            List<float[]> textureCoordinates,
            List<float[]> normals,
            String material
    ) {
        String[] indices = faceparts.split("/", -1);

        float[] position = positions.get(
                resolveIndex(indices[0], positions.size())
        );
        float[] uv = {0.0F, 0.0F};
        float[] normal = {0.0F, 1.0F, 0.0F};

        if (indices.length > 1 && !indices[1].isEmpty()) {
            uv = textureCoordinates.get(
                    resolveIndex(indices[1], textureCoordinates.size())
            );
        }

        if (indices.length > 2 && !indices[2].isEmpty()) {
            normal = normals.get(
                    resolveIndex(indices[2], normals.size())
            );
        }
        return new Vertex(
                position[0], position[1], position[2],
                uv[0], uv[1],
                normal[0], normal[1], normal[2],
                material
        );
    }

    private static int resolveIndex(String index, int size) {

        int value = Integer.parseInt(index);
        if (value > 0) {
            return value - 1;
        }

        if (value < 0) {
            return size + value;
        }

        throw new IllegalArgumentException("OBJ indices cannot be zero");
    }

    public record Vertex(
            float x, float y, float z,
            float u, float v,
            float nx, float ny, float nz,
            String material
    ) {
    }
}
