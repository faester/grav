package dk.mfaester.grav;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Program {
    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();
        } catch (LWJGLException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        while (!Display.isCloseRequested()) {

            Display.update();
        }

        Display.destroy();
    }

    public static void main(String[] args) {
        Program program = new Program();
        program.start();
    }
}
