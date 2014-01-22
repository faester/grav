package dk.mfaester.grav;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class DrawableToOpenGlVaoBinder
{
    public void Bind(Drawable drawable){
        if (drawable.hasBeenSent()) { return; }

        prepareVerticeArrayObject(drawable);
        bindVerticeArrayObject(drawable);

        bindVertices(drawable);
        bindColors(drawable);
        bindIndices(drawable);

        releaseVerticeArrayObject();
        drawable.setHasBeenSent(true);
    }

    private void bindColors(Drawable drawable) {
        float[] colors = drawable.getColors();
        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colors.length);
        colorBuffer.put(colors);
        colorBuffer.flip();
        int vboColorId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboColorId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void bindIndices(Drawable drawable) {
        int[] indices = drawable.getIndices();
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        // Create a new VBO for the indices and select it (bind)
        int vboiId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);

        // Deselect the VBO.
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        drawable.setVertexIndexBufferObjectId(vboiId);

    }

    private void bindVertices(Drawable drawable) {
        float[] vertices = drawable.getVertices();
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();

        int vertextBufferObjectId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertextBufferObjectId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        // Put the VBO in the attributes list at index 0
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
        // Deselect (bind to 0) the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        drawable.setVertexBufferObjectId(vertextBufferObjectId);
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
