package dk.mfaester.grav;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

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

    private final float radius;
    private final int depth;

    public IcoSphere(float radius, int depth){
        this.radius = radius;
        this.depth = depth;
        init();
    }

    private void init(){
        ArrayList<Vector3f> workingVertices = initVertices();
        ArrayList<Integer> workingIndices = new ArrayList<Integer>();
        for (int i = 0; i < icoIndices.length; i++){
            workingIndices.add(icoIndices[i]);
        }

        for (int recursions = 0; recursions < this.depth; recursions++){
            workingIndices = subdivide(workingVertices, workingIndices);
        }

        this.indices = toIntArray(workingIndices);
        this.verticePoints = fitOnRadius(workingVertices);
    }

    private ArrayList<Integer> subdivide(
            final ArrayList<Vector3f> workingVertices,
            final ArrayList<Integer> workingIndices) {
        ArrayList<Integer> newWorkingIndices
                = new ArrayList<Integer>();
        newWorkingIndices.ensureCapacity(workingIndices.size() * 3);
        for (int i = 0; i < workingIndices.size(); i += 3) {
            int newVertexIndex = workingVertices.size();
            int indexA = workingIndices.get(i);
            int indexB = workingIndices.get(i + 1);
            int indexC = workingIndices.get(i + 2);

            Vector3f a = workingVertices.get(indexA);
            Vector3f b = workingVertices.get(indexB);
            Vector3f c = workingVertices.get(indexC);

            Vector3f center =
                    new Vector3f(
                            (a.getX() + b.getX() + c.getX()) / 3f,
                            (a.getY() + b.getY() + c.getY()) / 3f,
                            (a.getZ() + b.getZ() + c.getZ()) / 3f);

            newWorkingIndices.add(indexA);
            newWorkingIndices.add(newVertexIndex);
            newWorkingIndices.add(indexB);

            newWorkingIndices.add(newVertexIndex);
            newWorkingIndices.add(indexC);
            newWorkingIndices.add(indexB);

            newWorkingIndices.add(indexA);
            newWorkingIndices.add(newVertexIndex);
            newWorkingIndices.add(indexC);

            // Also needs the point between A - B, C - B and C - A

            workingVertices.add(center);
        }

        return newWorkingIndices;
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
        float[] colors = new float[verticePoints.length];
        for(int i = 0; i < verticePoints.length; i++){
            colors[i] = (i % 7) % 2;
        }
        return colors;
    }

    @Override
    protected float[] createVertices() {
        return this.verticePoints;
    }

    protected ArrayList<Vector3f> initVertices() {
        ArrayList<Vector3f> points = new ArrayList<Vector3f>();
        points.ensureCapacity(icoPoints.length / 3);
        for(int i = 0; i < icoPoints.length; i++){
            Vector3f clone = cloneVector3f(icoPoints[i]);
            points.add(clone);
        }
        return points;
    }

    private Vector3f cloneVector3f(final Vector3f icoPoint) {
        return new Vector3f(icoPoint.getX(), icoPoint.getY(), icoPoint.getZ());
    }

    private float[] fitOnRadius(final ArrayList<Vector3f> points){
        float[] array = new float[points.size() * 4];
        for(int i = 0; i < points.size(); i++){
            Vector3f currentPoint = points.get(i);
            float scale = this.radius / currentPoint.length();
            currentPoint.scale(this.radius / (float)currentPoint.length());

            int arrayOffset = i * 4;
            array[arrayOffset] = currentPoint.getX();
            array[arrayOffset + 1] = currentPoint.getY();
            array[arrayOffset + 2] = currentPoint.getZ();
            array[arrayOffset + 3] = 1f;
        }
        return array;
    }

    @Override
    protected int[] createIndices() {
        return indices;
    }
}
