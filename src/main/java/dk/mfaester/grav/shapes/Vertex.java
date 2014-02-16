package dk.mfaester.grav.shapes;

public class Vertex {
    private float[] xyzw = new float[]{0f, 0f, 0f, 1f};
    private float[] rgba = new float[]{1f, 1f, 1f, 0.2f};
    private float u, v;

    public static final int positionElementCount = 4;
    public static final int colorElementCount = 4;
    public static final int textureElementCount = 2;

    public static final int elementCount = positionElementCount + colorElementCount + textureElementCount;

    public static final int elementBytes = 4;

    public static final int positionByteCount = positionElementCount * elementBytes;
    public static final int colorByteCount = colorElementCount * elementBytes;
    public static final int textureByteCount = textureElementCount * elementBytes;


    public static final int positionByteOffset = 0;
    public static final int colorByteOffset = positionByteOffset + positionByteCount;
    public static final int textureByteOffset = colorByteOffset + colorByteCount;

    public static final int stride = positionByteCount + colorByteCount + textureByteCount;


    public Vertex setXYZ(float x, float y, float z) {
        this.xyzw = new float[]{x, y, z, 1f};
        return this;
    }

    public float[] getElements(){
        float[] result = new float[Vertex.elementCount];
        int ix = 0;

        result[ix++] = this.xyzw[0];
        result[ix++] = this.xyzw[1];
        result[ix++] = this.xyzw[2];
        result[ix++] = this.xyzw[3];

        result[ix++] = this.rgba[0];
        result[ix++] = this.rgba[1];
        result[ix++] = this.rgba[2];
        result[ix++] = this.rgba[3];

        result[ix++] = this.u;
        result[ix++] = this.v;

        return result;
    }

    public Vertex setRGB(float r, float g, float b) {
        this.rgba = new float[]{r, g, b, 0.3f};
        return this;
    }

    public float[] getXYZW() {
        return new float[]{this.xyzw[0], this.xyzw[1], this.xyzw[2], this.xyzw[3]};
    }

    public float[] getRGBA() {
        return new float[]{this.rgba[0], this.rgba[1], this.rgba[2], this.rgba[3]};
    }

    public Vertex setUV(float u, float v) {
        this.u = u;
        this.v = v;
        return this;
    }

    public float[] getUV() {
        return new float[]{u, v};
    }
}
