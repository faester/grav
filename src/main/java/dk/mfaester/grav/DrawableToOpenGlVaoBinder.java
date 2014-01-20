package dk.mfaester.grav;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;

public class DrawableToOpenGlVaoBinder
{
    public void Bind(Drawable drawable){
        if (drawable.hasBeenSent()) { return; }

        prepareVerticeArrayObject(drawable);

        bindVerticeArrayObject(drawable);

        bindVectors(drawable);

        releaseVerticeArrayObject();
        drawable.setHasBeenSent(true);
    }

    private void bindVectors(Drawable drawable) {
        float[] vertices = drawable.getVertices();
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();
        int vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        // Put the VBO in the attributes list at index 0
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        // Deselect (bind to 0) the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void prepareVerticeArrayObject(Drawable drawable) {
        drawable.setOpenGlId(GL30.glGenVertexArrays());
    }

    private void releaseVerticeArrayObject() {
        GL30.glBindVertexArray(0);
    }

    private void bindVerticeArrayObject(Drawable drawable) {
        GL30.glBindVertexArray(drawable.getOpenGLVaoId());
    }
}
