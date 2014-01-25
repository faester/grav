package dk.mfaester.grav;

/**
 * Created by Morten.Faester on 25-01-14.
 */
public class MathUtil {
    public static float degreesToRadians(float angle) {
        return angle * (float)Math.PI / 180f;
    }

    public static float coTangent(float value){
        return (float)(1 / Math.tan(value));
    }
}
