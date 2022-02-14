package de.msjones.landscapeviewer;

import lombok.Getter;

import javax.media.j3d.TriangleArray;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Point3f;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CoordinatesLoader {
    @Getter
    private List<List<Integer>> coordinates;
    private String fileName;
    @Getter
    private TriangleStripArray triangleStripArray;

    public CoordinatesLoader(String fileName) throws IOException {
        this.coordinates = new ArrayList<>();
        this.fileName = fileName;

        loadIntegerValues();
        createTriangles();
    }

    private void loadIntegerValues() throws IOException {
        Path path = Paths.get(fileName);
        byte[] data = Files.readAllBytes(path);

        int length = (int) Math.sqrt(data.length / 2);
        for (int i = 0; i * length * 2 < data.length; ++i) {
            List<Integer> row = IntStream.range(i, i + length).mapToObj(j -> ((data[j * 2] & 0xff) << 8 | data[(j * 2) + 1] & 0xff)).collect(Collectors.toList());
            coordinates.add(row);
        }
    }

    private void createTriangles() {
        int distance = 2;
        List<Point3f> coordsList = new ArrayList<>();
        int[] stripVertexCount = new int[coordinates.size() - 1];
        for (int row = 0; row < coordinates.size() - 1; ++row) {
            for (int pos = 0; pos < coordinates.get(row).size(); ++pos) {
                // Triangle 1
                coordsList.add(new Point3f(row * distance, pos * distance, coordinates.get(row).get(pos)));

                // Triangle 2
                coordsList.add(new Point3f((row + 1) * distance, pos * distance, coordinates.get(row + 1).get(pos)));
            }
            stripVertexCount[row] = coordinates.get(row).size() * 2;
        }
        triangleStripArray = new TriangleStripArray(coordsList.size(), TriangleArray.COORDINATES, stripVertexCount);
        triangleStripArray.setCoordinates(0, coordsList.toArray(new Point3f[0]));

    }

}
