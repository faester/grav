package dk.mfaester.grav.rendering;

import org.lwjgl.opengl.GL20;

public class DefaultShaderLoader extends OpenGlBaseObject implements ShaderLoader
{
    private class ShaderDefinition implements  ShaderDefinitions {

        private ShaderProgram shaderProgram;
        private int projectionMatrixLocation;
        private int viewMatrixLocation;
        private int modelMatrixLocation;

        private ShaderDefinition(ShaderProgram shaderProgram, int projectionMatrixLocation, int viewMatrixLocation, int modelMatrixLocation) {
            this.shaderProgram = shaderProgram;
            this.projectionMatrixLocation = projectionMatrixLocation;
            this.viewMatrixLocation = viewMatrixLocation;
            this.modelMatrixLocation = modelMatrixLocation;
        }

        @Override
        public ShaderProgram getShaderProgram() {
            return shaderProgram;
        }

        @Override
        public int getProjectionMatrixLocation() {
            return projectionMatrixLocation;
        }

        @Override
        public int getViewMatrixLocation() {
            return viewMatrixLocation;
        }

        @Override
        public int getModelMatrixLocation() {
            return modelMatrixLocation;
        }
    }

    public ShaderDefinitions loadShaders() {
        Shader fragmentShader = Shader.loadFragmentShader("C:/workdirs/grav/src/main/resources/dk/mfaester/grav/fragment.glsl");
        Shader vertexShader = Shader.loadVertexShader("C:/workdirs/grav/src/main/resources/dk/mfaester/grav/vertex.glsl");
        vertexShader.addInputAttribute("in_Position");
        vertexShader.addInputAttribute("in_Color");
        vertexShader.addInputAttribute("in_TextureCoord");
        ShaderProgram shaderProgram = new ShaderProgram();
        shaderProgram.AttachShader(vertexShader);
        shaderProgram.AttachShader(fragmentShader);
        shaderProgram.LinkAndValidateProgram();

        // Get matrices uniform locations
        int projectionMatrixLocation = GL20.glGetUniformLocation(shaderProgram.getGlProgramId(), "projectionMatrix");
        int viewMatrixLocation = GL20.glGetUniformLocation(shaderProgram.getGlProgramId(), "viewMatrix");
        int modelMatrixLocation = GL20.glGetUniformLocation(shaderProgram.getGlProgramId(), "modelMatrix");

        super.throwOnGlError("loadShaders");

        return new ShaderDefinition(shaderProgram, projectionMatrixLocation, viewMatrixLocation, modelMatrixLocation);
    }

}
