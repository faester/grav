package dk.mfaester.grav;

import org.lwjgl.opengl.GL11;

import java.util.List;

public class IcoSphere extends AbstractDrawable {
//http://blog.andreaskahler.com/2009/06/creating-icosphere-mesh-in-code.html
    private final static float t = (float)(1 + Math.sqrt(5f)) / 2.0f;
    private final static float[] icoPoints = {
        -1f, t, 0, 1f,
        1f, t, 0,  1f,
        -1f, -t, 0, 1f,
        1f, -t, 0, 1f,
        0, -1f, t, 1f,
        0, 1f, t, 1f,
        0, -1f, -t, 1f,
        0, 1f, -t, 1f,
        t, 0, -1f, 1f,
        t, 0, 1f, 1f,
        -t, 0, -1f, 1f,
        -t, 0, 1f, 1f,
    };

    private final static int[] indices = new int[]{
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


    private final static float[] colors = {
        0, 0, 0, 1f,
        0, 1, 0, 1f,
        0, 0, 1, 1f,
        0, 1, 1, 1f,
        1, 0, 0, 1f,
        1, 1, 0, 1f,
        1, 0, 1, 1f,
        1, 1, 1, 1f,
        0.5f, 0f, 0.5f, 1f,
        0.5f, 0.5f, 0.5f, 1f,
        0.5f, 0f, 0f, 1f,
        0.5f, 0.5f, 0f, 1f,
    };

    private float radius;
    private float step;

    public IcoSphere(float radius, float step){
        this.radius = radius;
        this.step = step;
    }

    @Override
    public int getGlVerticeFormat() {
        return GL11.GL_TRIANGLES;
    }

    @Override
    protected float[] createColors() {
        return colors;
    }

    private float[] toArray(List<Float> floats){
        float[] array = new float[floats.size()];
        for(int i = 0; i < floats.size(); i++){
            array[i] = floats.get(i);
        }
        return array;
    }

    @Override
    protected float[] createVertices() {
        for(int i = 0; i < icoPoints.length; i++){
            System.out.print(icoPoints[i] + ", ");
        }
        System.out.println();
        return icoPoints;
    }

    @Override
    protected int[] createIndices() {
        return indices;
    }
}
