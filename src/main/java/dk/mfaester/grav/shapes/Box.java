package dk.mfaester.grav.shapes;

import dk.mfaester.grav.shapes.AbstractDrawable;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Box extends AbstractDrawable {
    public Box(){
        this.angle = new Vector3f(45, 20, 0);
    }

    final Vector4f color = new Vector4f(0f, 0f, 0.8f, 0.5f);

    @Override
    protected float[] createVertices() {
        final float x = 1f, y = 1f, z = 1f;

        final float[] vertices = {
                -x, -y, -z, // 0
                -x, +y, -z, // 1
                +x, +y, -z, // 2
                +x, -y, -z, // 3
                -x, -y, +z, // 4
                -x, +y, +z, // 5
                +x, +y, +z, // 6
                +x, -y, +z, // 7
        };
        return vertices;
    }

    @Override
    protected float[] createColors() {
        final float[] vertices = {
                1f, 0f, 0f,
                1f, 0f, 1f,
                1f, 1f, 0f,
                1f, 1f, 1f,
                0f, 0f, 0f,
                0f, 0f, 1f,
                0f, 1f, 0f,
                0f, 1f, 1f,
        };
        return vertices;
    }

    @Override
    protected int[] createIndices() {
        final int[] indices = {
                0, 3, 7,
                4, 7, 0,

                1, 2, 6,
                5, 6, 1,

                0, 1, 2,
                3, 2, 0,

                4, 5, 6,
                7, 6, 4,

                3, 2, 6,
                7, 6, 3,

                0, 1, 5,
                4, 5, 0,
        };
        return indices;
    }


    @Override
    protected float[] createUvs(){
        final float[] uvs = {
                0f, 0f,
                1f, 0f,
                0f, 1f,
                1f, 0f,

                0f, 1f,
                1f, 0f,
                0f, 1f,
                1f, 1f,

        };
        return uvs;
    }


}
