package dk.mfaester.grav;

import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    /*
     http://www.opengl-tutorial.org/beginners-tutorials/tutorial-3-matrices/#The_Projection_matrix
      */
    private float width;
    private float height;
    private final float moveDelta = 0.01f;


    private final Matrix4f movement ;

    private final static Vector3f xVector = new Vector3f(1, 0, 0);
    private final static Vector3f yVector = new Vector3f(0, 1, 0);
    private final static Vector3f zVector = new Vector3f(0, 0, 1);
    private final float rotateStep = 0.01f;

    public Camera(int width, int height){
        this.width = width;
        this.height = height;
        createProjectionMatrix();
        movement = new Matrix4f();
        movement.setIdentity();
    }

    private Vector3f getXVector(){
        return new Vector3f(movement.m00 * this.moveDelta, movement.m10 * this.moveDelta, movement.m20 * this.moveDelta);
    }

    private Vector3f getYVector(){
        return new Vector3f(movement.m01 * this.moveDelta, movement.m11 * this.moveDelta, movement.m21 * this.moveDelta);
    }

    private Vector3f getZVector(){
        return new Vector3f(movement.m02 * this.moveDelta, movement.m12 * this.moveDelta, movement.m22 * this.moveDelta);
    }

    private Vector3f position = new Vector3f(0, 0, -1);
    private Matrix4f projectionMatrix;

    public Vector3f getPosition() {
        return this.position;
    }

    public void moveLeft(){
        Vector3f.add(this.position, getXVector(), this.position);
    }

    public void moveRight(){
        Vector3f.sub(this.position, getXVector(), this.position);
    }

    public void moveUp(){
        Vector3f.add(this.position, getYVector(), this.position);
    }

    public void moveDown(){
        Vector3f.sub(this.position, getYVector(), this.position);
    }

    public void moveBackward(){
        Vector3f.sub(this.position, getZVector(), this.position);
    }

    public void moveForward(){
        Vector3f.add(this.position, getZVector(), this.position);
    }

    public void rotateClockWiseX() {
        this.movement.rotate(rotateStep, xVector);
        projectionMatrix.rotate(rotateStep, xVector);
    }

    public void rotateCounterClockWiseX() {
        this.movement.rotate(-rotateStep, xVector);
        projectionMatrix.rotate(-rotateStep, xVector);
    }

    public void rotateClockWiseY() {
        this.movement.rotate(rotateStep, yVector);
        projectionMatrix.rotate(rotateStep, yVector);
    }

    public void rotateCounterClockWiseY() {
        this.movement.rotate(-rotateStep, yVector);
        projectionMatrix.rotate(-rotateStep, yVector);
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
