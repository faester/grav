package dk.mfaester.grav;

import org.lwjgl.util.vector.Vector4f;

public class Box extends AbstractDrawable {

    final Vector4f color = new Vector4f(0f, 0f, 0.8f, 0.5f);

    @Override
    public Vector4f getColor() {
        return color;
    }

    @Override
    protected float[] createVertices() {
        final float x = .25f, y = .25f, z = .25f;

        final float[] vertices = {
                -x, -y, -z,
                -x, +y, -z,
                +x, +y, -z,
                +x, -y, -z,
        };
        return vertices;
    }

    @Override
    protected int[] createIndices() {
        final int[] indices = {
                0, 1, 2,
                3, 2, 0
        };
        return indices;
    }
}
