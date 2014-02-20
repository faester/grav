package dk.mfaester.grav.shapes;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Morten.Faester on 30-01-14.
 */
class Vector3fWithEquality {

    final float x, y, z, u, v;

    public Vector3fWithEquality(float x, float y, float z, float u, float v) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.u = u;
        this.v = v;
    }

    public Vector3fWithEquality(float x, float y, float z) {
        this(x, y, z, 0, 0);
    }

    public Vector3fWithEquality(Vector3f vector, float u, float v) {
        this(vector.getX(), vector.getY(), vector.getZ(), u, v);
    }

    public Vector3fWithEquality(Vector3fWithEquality icoPoint) {
        this.x = icoPoint.getX();
        this.y = icoPoint.getY();
        this.z = icoPoint.getZ();
        this.u = icoPoint.getU();
        this.v = icoPoint.getV();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector3fWithEquality)) return false;

        Vector3fWithEquality that = (Vector3fWithEquality) o;

        if (Float.compare(that.x, x) != 0) return false;
        if (Float.compare(that.y, y) != 0) return false;
        if (Float.compare(that.z, z) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }

    public Vector3f createVector() {
        return new Vector3f(this.x, this.y, this.z);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getU() {
        return u;
    }

    public float getV() {
        return v;
    }
}