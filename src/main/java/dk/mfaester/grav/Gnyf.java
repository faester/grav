package dk.mfaester.grav;

import org.lwjgl.util.vector.Vector4f;

public class Gnyf extends AbstractDrawable {
    final static Vector4f color = new Vector4f(0f, 0.8f, 0f, 0.5f);

    @Override
    public Vector4f getColor() {
        return color;
    }

    @Override
    protected float[] createVertices() {
        final float[] vertices = {
                // Left bottom triangle
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
        };
        return vertices;
    }

    @Override
    protected int[] createIndices() {
        final int[] indices = {
                0, 1, 2,
        };
        return indices;
    }
}
