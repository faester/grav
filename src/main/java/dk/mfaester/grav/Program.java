package dk.mfaester.grav;

import dk.mfaester.grav.rendering.*;
import dk.mfaester.grav.shapes.IcoSphere;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

public class Program  extends OpenGlBaseObject {
    private final ShaderDefinitions shaderDefinitions;
    private FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
    private Camera camera;
    private KeyboardHandler keyboardHandler;

    // Entry point for the application
    public static void main(String[] args) {
        new Program();
    }

    // Setup variables
    private final String WINDOW_TITLE = "The Quad: glDrawArrays";
    private final int WIDTH = 800;
    private final int HEIGHT = 800;

    private ShaderLoader shaderLoader = new DefaultShaderLoader();

    private dk.mfaester.grav.shapes.Drawable[] drawables;

    public Program() {
        // Initialize OpenGL (Display)
        this.setupOpenGL();

        this.shaderDefinitions = shaderLoader.loadShaders();

        this.drawables = createDrawables();

        this.camera = new Camera(WIDTH, HEIGHT);

        this.keyboardHandler = new KeyboardHandler(this.camera);

        this.bindDrawables(drawables);

        while (!Display.isCloseRequested()) {
            this.keyboardHandler.receiveInput();

            // Do a single loop (logic/render)
            this.loopCycle(this.drawables);

            // Force a maximum FPS of about 60
            Display.sync(60);
            // Let the CPU synchronize with the GPU if GPU is tagging behind
            Display.update();
        }

        // Destroy OpenGL (Display)
        this.destroyOpenGL(drawables);
    }

    private dk.mfaester.grav.shapes.Drawable[] createDrawables() {
        dk.mfaester.grav.shapes.Drawable[] drawables = {
//                new Gnyf(),
//                new Gnaf(),
//                new Box(),
                new IcoSphere(1f, 6),
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

            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthFunc(GL11.GL_LEQUAL);

            GL11.glViewport(0, 0, WIDTH, HEIGHT);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Setup an XNA like background color
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

        // Map the internal OpenGL coordinate system to the entire screen
        GL11.glViewport(0, 0, WIDTH, HEIGHT);

        this.throwOnGlError("Error in setupOpenGL");
    }

    public void bindDrawables(dk.mfaester.grav.shapes.Drawable[] drawables) {
        // Sending data to OpenGL requires the usage of (flipped) byte buffers

        DrawableToOpenGlVaoBinder binder = new DrawableToOpenGlVaoBinder();

        for (dk.mfaester.grav.shapes.Drawable drawable : drawables) {
            binder.Bind(drawable);
        }

        this.throwOnGlError("Error in bindDrawables");
    }

    public void loopCycle(dk.mfaester.grav.shapes.Drawable[] drawables) {
        render(drawables);
    }

    private void render(dk.mfaester.grav.shapes.Drawable[] drawables) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        for (dk.mfaester.grav.shapes.Drawable drawable : drawables) {
            prepareProjection(drawable);
            // Bind to the VAO that has all the information about the quad vertices

            GL20.glUseProgram(this.shaderDefinitions.getShaderProgram().getGlProgramId());
            GL30.glBindVertexArray(drawable.getOpenGLVaoId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);

            // Bind to element index array
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, drawable.getVertexIndexBufferObjectId());
            // Draw the vertices
            GL11.glDrawElements(drawable.getGlVerticeFormat(), drawable.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);

            // Put everything back to default (deselect)
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL30.glBindVertexArray(0);
            GL20.glUseProgram(0);
        }


        this.throwOnGlError("Error in loopCycle");
    }

    private void prepareProjection(dk.mfaester.grav.shapes.Drawable drawable) {
// Reset view and model matrices
        Matrix4f viewMatrix = new Matrix4f();

        Matrix4f modelMatrix = new Matrix4f();

// Translate camera
        org.lwjgl.util.vector.Matrix4f.translate(this.camera.getPosition(), viewMatrix, viewMatrix);

// Scale, translate and rotate model
        Vector3f modelAngle = drawable.getModelAngle();
        Matrix4f.scale(drawable.getModelScale(), modelMatrix, modelMatrix);
        Matrix4f.translate(drawable.getPosition(), modelMatrix, modelMatrix);
        Matrix4f.rotate(MathUtil.degreesToRadians(modelAngle.z), new Vector3f(0, 0, 1),
                modelMatrix, modelMatrix);
        Matrix4f.rotate(MathUtil.degreesToRadians(modelAngle.y), new Vector3f(0, 1, 0),
                modelMatrix, modelMatrix);
        Matrix4f.rotate(MathUtil.degreesToRadians(modelAngle.x), new Vector3f(1, 0, 0),
                modelMatrix, modelMatrix);
// Upload matrices to the uniform variables
        GL20.glUseProgram(this.shaderDefinitions.getShaderProgram().getGlProgramId());
        this.camera.getProjectionMatrix().store(matrix44Buffer);
        matrix44Buffer.flip();

        /// http://lwjgl.org/wiki/index.php?title=The_Quad_with_Projection,_View_and_Model_matrices
        GL20.glUniformMatrix4(this.shaderDefinitions.getProjectionMatrixLocation(), false, matrix44Buffer);
        viewMatrix.store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(this.shaderDefinitions.getViewMatrixLocation(), false, matrix44Buffer);
        modelMatrix.store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(this.shaderDefinitions.getModelMatrixLocation(), false, matrix44Buffer);
        GL20.glUseProgram(0);
        this.throwOnGlError("logicCycle");
    }

    public void destroyOpenGL(dk.mfaester.grav.shapes.Drawable[] drawables) {
        // Disable the VBO index from the VAO attributes list
        GL20.glDisableVertexAttribArray(0);

        for (dk.mfaester.grav.shapes.Drawable drawable : drawables) {
            // Delete the VBO
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
            GL15.glDeleteBuffers(drawable.getVertexBufferObjectId());

            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
            GL15.glDeleteBuffers(drawable.getVertexIndexBufferObjectId());

            // Delete the VAO
            GL30.glBindVertexArray(0);
            GL30.glDeleteVertexArrays(drawable.getOpenGLVaoId());
        }

        Display.destroy();
    }

}