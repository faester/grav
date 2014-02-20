package dk.mfaester.grav.shapes;

import dk.mfaester.grav.rendering.Texture;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 * Represents something that can be drawn.
 * Created by Morten.Faester on 20-01-14.
 */
public interface Drawable {
    /**
     * Used for internal bookkeeping to test if the object has been
     * sent to OpenGL (and the id has been set)
     * @return bool indicating whether we've sent the object to OpenGL
     */
    boolean hasBeenSent();

    /**
     * Sets a boolean indicating whether this Drawable has been sent to
     * OpenGL.
     * @param value
     */
    void setHasBeenSent(boolean value);

    /**
     * Gets the vertices used to compose this object.
     * @return
     */
    Vertex[] getVertices();

    Vector3f getPosition();

    Vector3f getModelAngle();

    Vector3f getModelScale();

    void setPosition(float x, float y, float z);

    /**
     * Gets the indices for the vertices to set.
     * @return
     */
    int[] getIndices();

    int getGlVerticeFormat();

    /**
     * Gets the id for the VAO.
     * @return
     */
    int getOpenGLVaoId();

    /**
     * Sets the VAO id
     * @param value
     */
    void setOpenGlId(int value);

    /**
     * Returns number of indices used to define the shape.
     * @return
     */
    int getIndexCount();

    void setVertexBufferObjectId(int vertexBufferObjectId);

    void setVertexIndexBufferObjectId(int vertexBufferObjectIndexId);

    int getVertexBufferObjectId();

    int getVertexIndexBufferObjectId();

    void setScale(float x, float y, float z);

    void setTexture(Texture texture);

    Texture getTexture();
}


