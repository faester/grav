package dk.mfaester.grav;

import org.lwjgl.opengl.GL11;

public class Box extends AbstractDrawable{

    @Override
    public float[] getVertices() {
        float x = .25f, y = .25f, z = .25f;
        float[] vertices = {
                -x, -y, -z,
                -x, +y, -z,
                +x, +y, -z,
                +x, -y, -z,

                -x, -y, -z,
                +x, -y, -z,
                +x, +y, -z,
                -x, +y, -z,

                -x, -y, +z,
                -x, +y, +z,
                +x, +y, +z,
                +x, -y, +z,

                -x, -y, +z,
                +x, -y, +z,
                +x, +y, +z,
                -x, +y, +z,

                -x, +y, +z,
                +x, +y, +z,
                +x, -y, +z,
                -x, -y, +z,

                +x, +y, +z,
                +x, -y, +z,
                -x, -y, +z,
                -x, +y, +z,
        };
        return vertices;
    }

    @Override
    public int getGlVerticeFormat() {
        return GL11.GL_TRIANGLES;
    }

    @Override
    public int getGlVertexCount() {
        return 0;
    }
}
