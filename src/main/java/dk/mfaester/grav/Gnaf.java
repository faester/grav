package dk.mfaester.grav;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

public class Gnaf extends AbstractDrawable {
    final Vector4f color = new Vector4f(0.8f, 0f, 0f, 0.5f);

    @Override
    protected float[] createVertices() {
        final float[] vertices = {
                0.5f, -0.5f, 0f, 1f,
                0.5f, 0.5f, 0f, 1f,
                -0.5f, 0.5f, 0f, 1f,
        };
        return vertices;
    }

    @Override
    protected float[] createColors() {
        final float[] colors = {
                1f, 0f, 0f, 1f,
                0f, 1f, 0f, 1f,
                0f, 0f, 1f, 1f,
        };
        return colors;
    }

    @Override
    protected int[] createIndices() {
        final int[] indices = {
                0, 1, 2,
        };
        return indices;
    }
}

