package dk.mfaester.grav.shapes;

public class Vertex {
    private float[] xyzw = new float[]{0f, 0f, 0f, 0f};
    private float[] rgba = new float[]{1f, 1f, 1f, 1f};

    public static final int elementCount = 8;
    public static final int elementBytes = 4;
    public static final int sizeInBytes = elementBytes * elementCount;

    public Vertex setXYZ(float x, float y, float z) {
        this.xyzw = new float[]{x, y, z, 1f};
        return this;
    }

    public Vertex setRGB(float r, float g, float b) {
        this.rgba = new float[]{r, g, b, 1f};
        return this;
    }

    public float[] getXYZW() {
        return new float[]{this.xyzw[0], this.xyzw[1], this.xyzw[2], this.xyzw[3]};
    }

    public float[] getRGBA() {
        return new float[]{this.rgba[0], this.rgba[1], this.rgba[2], this.rgba[3]};
    }
}
