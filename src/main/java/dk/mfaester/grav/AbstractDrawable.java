package dk.mfaester.grav;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Morten.Faester on 20-01-14.
 */
public abstract class AbstractDrawable implements Drawable {
    private boolean hasBeenSent;
    private int openGlVaoId;

    private final float[] vertices = createVertices();
    private final float[] colors = createColors();
    private final int[] indices = createIndices();

    protected abstract float[] createColors();

    private int vertexBufferObjectId;
    private int vertexBufferObjectIndexId;

    protected abstract float[] createVertices();

    protected abstract int[] createIndices();

    @Override
    public boolean hasBeenSent() {
        return hasBeenSent;
    }


    final private static Vector3f position = new Vector3f(0, 0, 0);
    final private static Vector3f angle = new Vector3f(2f, 23f, 0);
    final private static Vector3f scale = new Vector3f(1, 1, 1);

    @Override
    public Vector3f getPosition() {
        return AbstractDrawable.position;
    }

    @Override
    public Vector3f getModelAngle() {
        return AbstractDrawable.angle;
    }

    @Override
    public Vector3f getModelScale() {
        return AbstractDrawable.scale;
    }


    @Override
    public void setHasBeenSent(boolean value) {
        hasBeenSent = value;
    }

    @Override
    public float[] getColors() {
        return this.colors;
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
