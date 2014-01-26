package dk.mfaester.grav;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class KeyboardHandler {
    private Camera camera;
    private boolean isWireframeRendering = false;
    private boolean isMoveUp;
    private boolean isMoveBackward;
    private boolean isMoveDown;
    private boolean isMoveForward;
    private boolean isMoveRight;
    private boolean isMoveLeft;
    private boolean isRotateCcwY;
    private boolean isRotateCwX;
    private boolean isRotateCcwX;
    private boolean isRotateCwY;
    private boolean destroying;

    public KeyboardHandler(Camera camera) {
        this.camera = camera;
    }

    public void receiveInput() {
        if (destroying) { return; }
        while (Keyboard.next()) {
            switch (Keyboard.getEventKey()) {
                case Keyboard.KEY_F:
                    switchWireframeMode();
                    break;
                case Keyboard.KEY_UP:
                    isMoveForward = Keyboard.getEventKeyState();
                    break;
                case Keyboard.KEY_DOWN:
                    isMoveBackward = Keyboard.getEventKeyState();
                    break;
                case Keyboard.KEY_RIGHT:
                    isMoveRight = Keyboard.getEventKeyState();
                    break;
                case Keyboard.KEY_LEFT:
                    isMoveLeft = Keyboard.getEventKeyState();
                    break;
                case Keyboard.KEY_PRIOR: //PageUp
                    isMoveUp = Keyboard.getEventKeyState();
                    break;
                case Keyboard.KEY_NEXT: // PgDown
                    isMoveDown = Keyboard.getEventKeyState();
                    break;
                case Keyboard.KEY_W:
                    this.isRotateCcwX = Keyboard.getEventKeyState();
                    break;
                case Keyboard.KEY_S:
                    this.isRotateCwX = Keyboard.getEventKeyState();
                    break;
                case Keyboard.KEY_A:
                    this.isRotateCcwY = Keyboard.getEventKeyState();
                    break;
                case Keyboard.KEY_D:
                    this.isRotateCwY = Keyboard.getEventKeyState();
                    break;
                case Keyboard.KEY_ESCAPE:
                    this.destroying= true;
                    Display.destroy();
            }
        }
        handleMovement();
    }

    private void handleMovement(){
        if (isMoveForward) {
            this.camera.moveForward();
        }
        if (isMoveBackward){
            this.camera.moveBackward();
        }
        if (isMoveUp){
            this.camera.moveUp();
        }
        if (isMoveDown){
            this.camera.moveDown();
        }
        if (isMoveLeft){
            this.camera.moveLeft();
        }
        if (isMoveRight){
            this.camera.moveRight();
        }
        if (isRotateCcwX){
            this.camera.rotateCounterClockWiseX();
        }
        if (isRotateCwX){
            this.camera.rotateClockWiseX();
        }
        if (isRotateCcwY){
            this.camera.rotateCounterClockWiseY();
        }
        if (isRotateCwY){
            this.camera.rotateClockWiseY();
        }
    }

    private void switchWireframeMode() {
        this.isWireframeRendering = !this.isWireframeRendering;
        System.out.println("Wireframe rendering: " + this.isWireframeRendering);
        this.setOpenGLWirefromeMode();
    }

    private void setOpenGLWirefromeMode() {
        if (this.isWireframeRendering) {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        } else {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        }
    }
}
