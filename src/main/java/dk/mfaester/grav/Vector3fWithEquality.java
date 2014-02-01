package dk.mfaester.grav;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Morten.Faester on 30-01-14.
 */
public class Vector3fWithEquality {

    final float x, y, z;

    public Vector3fWithEquality(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3fWithEquality(Vector3f vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
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
}