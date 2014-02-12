package dk.mfaester.grav.shapes;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Morten.Faester on 20-01-14.
 */
public abstract class AbstractDrawable implements Drawable {
    private boolean hasBeenSent;
    private int openGlVaoId;

    private Vertex[] vertices;
    private int[] indices;

    protected abstract float[] createColors();

    @Override
    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }

    private int vertexBufferObjectId;
    private int vertexBufferObjectIndexId;

    protected abstract float[] createVertices();

    protected abstract int[] createIndices();

    @Override
    public boolean hasBeenSent() {
        return hasBeenSent;
    }

    protected Vector3f position = new Vector3f(0, 0, 0);
    protected Vector3f angle = new Vector3f(0, 0, 0);
    protected Vector3f scale = new Vector3f(1, 1, 1);

    @Override
    public Vector3f getPosition() {
        return this.position;
    }

    @Override
    public Vector3f getModelAngle() {
        return this.angle;
    }

    @Override
    public Vector3f getModelScale() {
        return this.scale;
    }

    @Override
    public void setHasBeenSent(boolean value) {
        hasBeenSent = value;
    }

    @Override
    public void setScale(float x, float y, float z) {
        if (this.scale == null) {
            this.scale = new Vector3f(x, y, z);
        } else {
            this.scale.set(x, y, z);
        }
    }

    @Override
    public Vertex[] getVertices() {
        float[] xyz = createVertices();
        float[] rgb = createColors();

        this.vertices = new Vertex[xyz.length / 3];

        for(int i = 0; i < this.vertices.length; i++){
            this.vertices[i] = new Vertex()
                    .setXYZ(xyz[i * 3], xyz[i * 3 + 1], xyz[i * 3 + 2])
                    .setRGB(rgb[i * 3], rgb[i * 3 + 1], rgb[i * 3 + 2]);
        }

        return this.vertices;
    }

    @Override
    public int[] getIndices() {
        if (this.indices == null){
            this.indices = createIndices();
        }
        return indices;
    }

    @Override
    public int getGlVerticeFormat() {
        return GL11.GL_TRIANGLES;
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
