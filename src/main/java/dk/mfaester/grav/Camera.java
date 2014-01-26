package dk.mfaester.grav;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    /*
     http://www.opengl-tutorial.org/beginners-tutorials/tutorial-3-matrices/#The_Projection_matrix
      */
    private float width;
    private float height;
    private final float moveDelta = 0.01f;

    public Camera(int width, int height){
        this.width = width;
        this.height = height;
        createProjectionMatrix();
    }

    private Vector3f position = new Vector3f(0, 0, -1);
    private Matrix4f projectionMatrix;

    public Vector3f getPosition() {
        return this.position;
    }

    public void moveLeft(){
        this.position.setX(this.position.getX() - moveDelta);
    }

    public void moveRight(){
        this.position.setX(this.position.getX() + moveDelta);
    }

    public void moveUp(){
        this.position.setY(this.position.getY() + moveDelta);
    }

    public void moveDown(){
        this.position.setY(this.position.getY() - moveDelta);
    }

    public void moveBackward(){
        this.position.setZ(this.position.getZ() - moveDelta);
    }

    public void moveForward(){
        this.position.setZ(this.position.getZ() + moveDelta);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    private void createProjectionMatrix(){
        // Setup projection matrix
        projectionMatrix = new Matrix4f();
        float fieldOfView = 60f;
        float aspectRatio = this.width / this.height;
        float near_plane = 0.1f;
        float far_plane = 100f;

        float y_scale = MathUtil.coTangent(MathUtil.degreesToRadians(fieldOfView / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
        projectionMatrix.m33 = 0;
    }
}
