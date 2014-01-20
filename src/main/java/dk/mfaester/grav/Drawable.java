package dk.mfaester.grav;

/**
 * Created by Morten.Faester on 20-01-14.
 */
public interface Drawable {
    boolean hasBeenSent();
    void setHasBeenSent(boolean value);

    float[] getVertices();

    int getGlVerticeFormat();

    int getOpenGLVaoId();
    void setOpenGlId(int value);

    int getGlVertexCount();
}


