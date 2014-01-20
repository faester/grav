package dk.mfaester.grav;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;


public class Program {
    // Entry point for the application
    public static void main(String[] args) {
        new Program();
    }

    // Setup variables
    private final String WINDOW_TITLE = "The Quad: glDrawArrays";
    private final int WIDTH = 800;
    private final int HEIGHT = 800;
    // Quad variables
    private int vaoId = 0;
    private int vboId = 0;

    private Drawable[] _drawables;

    public Program() {
        // Initialize OpenGL (Display)
        this.setupOpenGL();

        this._drawables = CreateDrawables();

        this.setupQuad(_drawables);

        while (!Display.isCloseRequested()) {
            // Do a single loop (logic/render)
            this.loopCycle(this._drawables);

            // Force a maximum FPS of about 60
            Display.sync(60);
            // Let the CPU synchronize with the GPU if GPU is tagging behind
            Display.update();
        }

        // Destroy OpenGL (Display)
        this.destroyOpenGL();
    }

    private Drawable[] CreateDrawables() {
        Drawable[] drawables = {
            new Gnyf(),
            new Gnaf(),
        };

        return drawables;
    }

    public void setupOpenGL() {
        // Setup an OpenGL context with API version 3.2
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                    .withForwardCompatible(true)
                    .withProfileCore(true);

            DisplayMode displayMode = new DisplayMode(WIDTH, HEIGHT);
            Display.setDisplayMode(displayMode);
            Display.setTitle(WINDOW_TITLE);
            Display.create(pixelFormat, contextAtrributes);

            GL11.glViewport(0, 0, WIDTH, HEIGHT);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Setup an XNA like background color
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

        // Map the internal OpenGL coordinate system to the entire screen
        GL11.glViewport(0, 0, WIDTH, HEIGHT);

        this.exitOnGLError("Error in setupOpenGL");
    }

    public void setupQuad(Drawable[] drawables) {
        // Sending data to OpenGL requires the usage of (flipped) byte buffers

        DrawableToOpenGlVaoBinder binder = new DrawableToOpenGlVaoBinder();

        for(Drawable drawable : drawables ){
            binder.Bind(drawable);
        }

        this.exitOnGLError("Error in setupQuad");
    }

    public void loopCycle(Drawable[] drawables) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        for(Drawable drawable : drawables){
            // Bind to the VAO that has all the information about the quad vertices
            GL30.glBindVertexArray(drawable.getOpenGLVaoId());
            GL20.glEnableVertexAttribArray(0);

            // Draw the vertices
            GL11.glDrawArrays(drawable.getGlVerticeFormat(), 0, drawable.getGlVertexCount());

            // Put everything back to default (deselect)
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        }
        this.exitOnGLError("Error in loopCycle");
    }

    public void destroyOpenGL() {
        // Disable the VBO index from the VAO attributes list
        GL20.glDisableVertexAttribArray(0);

        // Delete the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vboId);

        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);

        Display.destroy();
    }

    public void exitOnGLError(String errorMessage) {
        int errorValue = GL11.glGetError();

        if (errorValue != GL11.GL_NO_ERROR) {
            String errorString = GLU.gluErrorString(errorValue);
            System.err.println("ERROR - " + errorMessage + ": " + errorString);

            if (Display.isCreated()) Display.destroy();
            System.exit(-1);
        }
    }
}