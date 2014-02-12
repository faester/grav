package dk.mfaester.grav.rendering;

import dk.mfaester.grav.shapes.Vertex;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class DrawableToOpenGlVaoBinder
{
    public void Bind(dk.mfaester.grav.shapes.Drawable drawable){
        if (drawable.hasBeenSent()) { return; }

        prepareVerticeArrayObject(drawable);
        bindVerticeArrayObject(drawable);

        bindVertices(drawable);
        bindIndices(drawable);

        releaseVerticeArrayObject();
        drawable.setHasBeenSent(true);
    }

    private void bindIndices(dk.mfaester.grav.shapes.Drawable drawable) {
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

    private void bindVertices(dk.mfaester.grav.shapes.Drawable drawable) {
        Vertex[] vertices = drawable.getVertices();
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.elementCount);

        for (int i = 0; i < vertices.length; i++){
            verticesBuffer.put(vertices[i].getXYZW());
            verticesBuffer.put(vertices[i].getRGBA());
        }
        verticesBuffer.flip();

        int vertexBufferObjectId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferObjectId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        // Put the VBO in the attributes list at index 0
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, Vertex.sizeInBytes, 0);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, Vertex.sizeInBytes, Vertex.elementBytes * 4);
        // Deselect (bind to 0) the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        drawable.setVertexBufferObjectId(vertexBufferObjectId);
    }

    private void prepareVerticeArrayObject(dk.mfaester.grav.shapes.Drawable drawable) {
        drawable.setOpenGlId(GL30.glGenVertexArrays());
    }

    private void releaseVerticeArrayObject() {
        GL30.glBindVertexArray(0);
    }

    private void bindVerticeArrayObject(dk.mfaester.grav.shapes.Drawable drawable) {
        GL30.glBindVertexArray(drawable.getOpenGLVaoId());
    }
}
