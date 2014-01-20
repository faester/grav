package dk.mfaester.grav;

import org.lwjgl.opengl.GL11;

public class Gnaf extends AbstractDrawable {
    @Override
    public float[] getVertices() {
        float[] vertices = {
                // Right top triangle
                0.5f, -0.5f, 0f, 1f,
                0.5f, 0.5f, 0f, 1f,
                -0.5f, 0.5f, 0f, 1f,
        };
        return vertices;
    }

    @Override
    public int getGlVerticeFormat() {
        return GL11.GL_TRIANGLES;
    }

    @Override
    public int getGlVertexCount() {
        return 3;
    }
}

