package dk.mfaester.grav.rendering;

public interface ShaderDefinitions {
    ShaderProgram getShaderProgram();
    int getProjectionMatrixLocation();
    int getViewMatrixLocation();
    int getModelMatrixLocation();
}
