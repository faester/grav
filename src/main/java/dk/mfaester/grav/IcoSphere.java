package dk.mfaester.grav;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.omg.PortableServer._ServantActivatorStub;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class IcoSphere extends AbstractDrawable {
//http://blog.andreaskahler.com/2009/06/creating-icosphere-mesh-in-code.html
    private final static float t = (float)(1 + Math.sqrt(5f)) / 2.0f;
    private final static float[] icoPoints = {
        -1f, t, 0,
        1f, t, 0,
        -1f, -t, 0,
        1f, -t, 0,
        0, -1f, t,
        0, 1f, t,
        0, -1f, -t,
        0, 1f, -t,
        t, 0, -1f,
        t, 0, 1f,
        -t, 0, -1f,
        -t, 0, 1f,
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

    private final float radius;
    private final float step;

    private IcoSphere(){
        this.radius = 0.4f;
        this.step = 0.4f;
    }

    public IcoSphere(float radius, float step){
        System.out.println("Radius " + radius);

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

    @Override
    protected float[] createVertices() {
        ArrayList<Vector3f> points = new ArrayList<Vector3f>();
        points.ensureCapacity(icoPoints.length / 3);
        for(int i = 0; i < icoPoints.length; i+=3){
            points.add(new Vector3f(icoPoints[i], icoPoints[i + 1], icoPoints[i + 2]));
        }
        return fitOnRadius(points);
    }

    private float[] fitOnRadius(ArrayList<Vector3f> points){
        float[] array = new float[points.size() * 4];
        for(int i = 0; i < points.size(); i++){
            Vector3f currentPoint = points.get(i);
            System.out.println("Length " + currentPoint.length());
            System.out.println("Radius " + this.radius);
            float scale = this.radius / currentPoint.length();
            System.out.println("Scale " + scale);
            currentPoint.scale(this.radius / (float)currentPoint.length());

            int arrayOffset = i * 4;
            array[arrayOffset] = currentPoint.getX();
            array[arrayOffset + 1] = currentPoint.getY();
            array[arrayOffset + 2] = currentPoint.getZ();
            array[arrayOffset + 3] = 1f;
            System.out.println("Length " + currentPoint.length());
        }
        System.out.println("Containing " + array.length);
        return array;
    }

    @Override
    protected int[] createIndices() {
        return indices;
    }
}
