package dk.mfaester.grav;

import org.lwjgl.opengl.GL20;

public class ShaderProgram {
    private final int glProgramId;
    private int attributeIndex = 0;

    public ShaderProgram(){
        this.glProgramId = GL20.glCreateProgram();
    }

    public int getGlProgramId() {
        return glProgramId;
    }

    public void AttachShader(Shader shader) {
        GL20.glAttachShader(glProgramId, shader.getShaderID());
        for (String attributeName : shader.getInputAttributes()) {
            GL20.glBindAttribLocation(glProgramId, attributeIndex, attributeName);
            attributeIndex++;
        }
    }

    public void LinkAndValidateProgram(){
        GL20.glLinkProgram(glProgramId);
        GL20.glValidateProgram(glProgramId);
    }
}
