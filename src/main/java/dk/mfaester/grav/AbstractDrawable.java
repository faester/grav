package dk.mfaester.grav;

import org.lwjgl.opengl.GL11;

/**
 * Created by Morten.Faester on 20-01-14.
 */
public abstract class AbstractDrawable implements Drawable {
    private boolean hasBeenSent;
    private int openGlVaoId;

    final float[] vertices = createVertices();
    final int[] indices = createIndices();
    private int vertexBufferObjectId;
    private int vertexBufferObjectIndexId;

    protected abstract float[] createVertices();

    protected abstract int[] createIndices();

    @Override
    public boolean hasBeenSent() {
        return hasBeenSent;
    }

    @Override
    public void setHasBeenSent(boolean value) {
        hasBeenSent = value;
    }

    @Override
    public float[] getVertices() {
        return this.vertices;
    }

    @Override
    public int[] getIndices() {
        return this.indices;
    }

    @Override
    public int getGlVerticeFormat() {
        return GL11.GL_TRIANGLES;
    }

    @Override
    public int getGlVertexCount() {
        return this.vertices.length;
    }

    @Override
    public int getIndexCount() {
        return this.indices.length;
    }

    @Override
    public int getOpenGLVaoId() {
        return openGlVaoId;
    }

    @Override
    public void setOpenGlId(int value) {
        openGlVaoId = value;
    }

    @Override
    public void setVertexBufferObjectId(int vertexBufferObjectId) {
        this.vertexBufferObjectId = vertexBufferObjectId;
    }

    @Override
    public void setVertexIndexBufferObjectId(int vertexBufferObjectIndexId) {
        this.vertexBufferObjectIndexId = vertexBufferObjectIndexId;
    }

    @Override
    public int getVertexBufferObjectId() {
        return this.vertexBufferObjectId;
    }

    @Override
    public int getVertexIndexBufferObjectId() {
        return this.vertexBufferObjectIndexId;
    }
}
