package dk.mfaester.grav.rendering;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

/**
 * Created by Morten.Faester on 02-02-14.
 */
public abstract class OpenGlBaseObject {
    public void throwOnGlError(String errorMessage) {
        int errorValue = GL11.glGetError();

        if (errorValue != GL11.GL_NO_ERROR) {
            String errorString = GLU.gluErrorString(errorValue);
            System.err.println();

            if (Display.isCreated()) Display.destroy();
            throw new RuntimeException("OpenGL error: " + errorMessage + ": " + errorString);
        }
    }
}
