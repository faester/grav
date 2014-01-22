package dk.mfaester.grav;

import com.sun.javafx.geom.Vec4f;
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
    float[] getVertices();

    /**
     * Gets the indices for the vertices to set.
     * @return
     */
    byte[] getIndices();

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
     * Gets number of vertices in the Drawable.
     * @return
     */
    int getGlVertexCount();

    /**
     * Returns number of indices used to define the shape.
     * @return
     */
    int getIndexCount();

    /**
     * Gets the color of the Drawable. (Is subject to change soon.)
     * @return
     */
    Vector4f getColor();

    void setVertexBufferObjectId(int vertexBufferObjectId);

    void setVertexIndexBufferObjectId(int vertexBufferObjectIndexId);

    int getVertexBufferObjectId();

    int getVertexIndexBufferObjectId();
}


