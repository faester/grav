package dk.mfaester.grav;

/**
 * Created by Morten.Faester on 20-01-14.
 */
public abstract class AbstractDrawable implements Drawable {
    private boolean _hasBeenSent;
    private int _openGlVaoId;

    @Override
    public boolean hasBeenSent() {
        return _hasBeenSent;
    }

    @Override
    public void setHasBeenSent(boolean value) {
        _hasBeenSent = value;
    }

    @Override
    public abstract float[] getVertices();

    @Override
    public abstract int getGlVerticeFormat();

    @Override
    public int getOpenGLVaoId() {
        return _openGlVaoId;
    }

    @Override
    public void setOpenGlId(int value) {
        _openGlVaoId = value;
    }
}
