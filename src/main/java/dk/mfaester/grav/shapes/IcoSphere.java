package dk.mfaester.grav.shapes;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;

public class IcoSphere extends AbstractDrawable {
//http://blog.andreaskahler.com/2009/06/creating-icosphere-mesh-in-code.html
    private final static float t = (float)(1 + Math.sqrt(5f)) / 2.0f;
    private final static Vector3f[] icoPoints = {
            new Vector3f(-1f, t, 0),
            new Vector3f(1f, t, 0),
            new Vector3f(-1f, -t, 0),
            new Vector3f(1f, -t, 0),
            new Vector3f(0, -1f, t),
            new Vector3f(0, 1f, t),
            new Vector3f(0, -1f, -t),
            new Vector3f(0, 1f, -t),
            new Vector3f(t, 0, -1f),
            new Vector3f(t, 0, 1f),
            new Vector3f(-t, 0, -1f),
            new Vector3f(-t, 0, 1f),
    };

    private final static int[] icoIndices = new int[]{
// 5 faces around point 0
        0, 11, 5,
        0, 5, 1,
        0, 1, 7,
        0, 7, 10,
        0, 10, 11,

// 5 adjacent faces
        1, 5, 9,
        5, 11, 4,
        11, 10, 2,
        10, 7, 6,
        7, 1, 8,

// 5 faces around point 3
        3, 9, 4,
        3, 4, 2,
        3, 2, 6,
        3, 6, 8,
        3, 8, 9,

// 5 adjacent faces
        4, 9, 5,
        2, 4, 11,
        6, 2, 10,
        8, 6, 7,
        9, 8, 1,
    };

    private int[] indices;
    private float[] verticePoints;

    private final int depth;
    private float[] uvArray;

    public IcoSphere(float radius, int depth){
        super.setScale(radius, radius, radius);
        this.depth = depth;
        init();
    }

    private void init(){
        ArrayList<Vector3fWithEquality> workingVertices = initVertices();
        ArrayList<Integer> workingIndices = new ArrayList<Integer>();
        HashMap<Vector3fWithEquality, Integer> vertexIndexMap
                = new HashMap<Vector3fWithEquality, Integer>(workingVertices.size());
        for (int i = 0; i < icoIndices.length; i++){
            workingIndices.add(icoIndices[i]);
        }

        for (int i = 0; i < workingVertices.size(); i++){
            vertexIndexMap.put(workingVertices.get(i), i);
        }

        for (int recursions = 0; recursions < this.depth; recursions++){
            workingIndices = subdivide(workingVertices, workingIndices, vertexIndexMap);
        }

        this.indices = toIntArray(workingIndices);
        this.verticePoints = fitOnRadius(workingVertices);
        this.uvArray = getUVs(workingVertices);
    }

    private ArrayList<Integer> subdivide(
            final ArrayList<Vector3fWithEquality> workingVertices,
            final ArrayList<Integer> workingIndices,
            final HashMap<Vector3fWithEquality, Integer> vertexIndexMap) {
        ArrayList<Integer> newWorkingIndices
                = new ArrayList<Integer>();
        newWorkingIndices.ensureCapacity(workingIndices.size() * 3);
        for (int i = 0; i < workingIndices.size(); i += 3) {
            int newVertexIndex = workingVertices.size();
            int indexA = workingIndices.get(i);
            int indexB = workingIndices.get(i + 1);
            int indexC = workingIndices.get(i + 2);

            Vector3fWithEquality a = workingVertices.get(indexA);
            Vector3fWithEquality b = workingVertices.get(indexB);
            Vector3fWithEquality c = workingVertices.get(indexC);

            int indexAB = createCenterVertex(a, b, vertexIndexMap, workingVertices);
            int indexAC = createCenterVertex(a, c, vertexIndexMap, workingVertices);
            int indexBC = createCenterVertex(b, c, vertexIndexMap, workingVertices);

            newWorkingIndices.add(indexA);
            newWorkingIndices.add(indexAB);
            newWorkingIndices.add(indexAC);

            newWorkingIndices.add(indexB);
            newWorkingIndices.add(indexAB);
            newWorkingIndices.add(indexBC);

            newWorkingIndices.add(indexC);
            newWorkingIndices.add(indexAC);
            newWorkingIndices.add(indexBC);

            newWorkingIndices.add(indexAB);
            newWorkingIndices.add(indexBC);
            newWorkingIndices.add(indexAC);
        }

        return newWorkingIndices;
    }

    private int createCenterVertex(Vector3fWithEquality a, Vector3fWithEquality b,
                                   HashMap<Vector3fWithEquality, Integer> vertexIndexMap,
                                   ArrayList<Vector3fWithEquality> workingVertices) {
        float x = (a.getX() + b.getX()) / 2f;
        float y = (a.getY() + b.getY()) / 2f;
        float z = (a.getZ() + b.getZ()) / 2f;
        float u = (a.getU() + b.getU()) / 2f;
        float v = (a.getV() + b.getV()) / 2f;
        Vector3fWithEquality vertex = new Vector3fWithEquality(x, y, z, u, v);

        if (vertexIndexMap.containsKey(vertex)) {
            return vertexIndexMap.get(vertex);
        } else {
            int index = workingVertices.size();
            workingVertices.add(vertex);
            return index;
        }
    }

    private int[] toIntArray(final ArrayList<Integer> workingIndices) {
        int[] result = new int[workingIndices.size()];
        for(int i = 0; i < result.length; i++){
            result[i] = workingIndices.get(i);
        }
        return result;
    }

    @Override
    public int getGlVerticeFormat() {
        return GL11.GL_TRIANGLES;
    }

    @Override
    protected float[] createColors() {
        float[] colorBase = {
                0.3f, 0.7f, 0.8f,
                0.8f, 0.3f, 0.3f,
                0.3f, 0.3f, 0.8f,
                0.3f, 0.3f, 0.3f,
        };
        float[] colors = new float[verticePoints.length];
        for(int i = 0; i < verticePoints.length; i++){
            colors[i] = colorBase[i % colorBase.length];
        }
        return colors;
    }

    @Override
    protected float[] createVertices() {
        return this.verticePoints;
    }

    protected ArrayList<Vector3fWithEquality> initVertices() {
        ArrayList<Vector3fWithEquality> points = new ArrayList<Vector3fWithEquality>();
        points.ensureCapacity(icoPoints.length);
        for(int i = 0; i < icoPoints.length; i++){
            Vector3f vec = icoPoints[i];
            Vector3f p = new Vector3f();
            vec.normalise(p);

            double u = .5 + (Math.atan2(p.getZ(), p.getX()) / (2 * Math.PI));
            double v = .5 - Math.asin(p.getY()) / Math.PI;

            System.out.println(String.format("(%.5f, %.5f)", u, v));
            points.add(new Vector3fWithEquality(icoPoints[i], (float)u, (float)v));
        }
        return points;
    }

    private float[] fitOnRadius(final ArrayList<Vector3fWithEquality> points){
        float[] array = new float[points.size() * 3];
        for(int i = 0; i < points.size(); i++){
            Vector3f currentPoint = points.get(i).createVector();
            currentPoint.scale(1f / (float)currentPoint.length());

            int arrayOffset = i * 3;
            array[arrayOffset] = currentPoint.getX();
            array[arrayOffset + 1] = currentPoint.getY();
            array[arrayOffset + 2] = currentPoint.getZ();
        }
        return array;
    }


    private float[] getUVs(final ArrayList<Vector3fWithEquality> points){
        float[] array = new float[points.size() * 2];
        for(int i = 0; i < points.size(); i++){
            Vector3fWithEquality currentPoint = points.get(i);
            int arrayOffset = i * 2;
            array[arrayOffset] = currentPoint.getU();
            array[arrayOffset + 1] = currentPoint.getV();
        }
        return array;
    }

    @Override
    protected int[] createIndices() {
        return indices;
    }

    @Override
    protected float[] createUvs() {
        return uvArray;
    }
}
