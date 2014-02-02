package dk.mfaester.grav.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.*;
import java.util.*;

public class Shader {
    int shaderID = 0;
    final private List<String> inputAttributes = new Vector<String>();

    private Shader() {
    }

    public int getShaderID() {
        return this.shaderID;
    }

    public static Shader loadVertexShader(InputStream filename) {
        Shader shaderProgram = new Shader();
        shaderProgram.loadShader(filename, GL20.GL_VERTEX_SHADER);
        return shaderProgram;
    }

    public static Shader loadFragmentShader(InputStream filename) {
        Shader shaderProgram = new Shader();
        shaderProgram.loadShader(filename, GL20.GL_FRAGMENT_SHADER);
        return shaderProgram;
    }

    private void loadShader(InputStream inputStream, int type) {
        if(inputStream == null){
            throw new NullPointerException("inputStream");
        }

        StringBuilder shaderSource = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }

        shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile shader.");
            throw new RuntimeException("Could not compile shader in file " + inputStream);
        }
    }

    public Iterable<String> getInputAttributes() {
        return inputAttributes;
    }

    public void addInputAttribute(String attributeName) {
        inputAttributes.add(attributeName);
    }
}
