package dk.mfaester.grav;

import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Shader {
    int shaderID = 0;

    private Shader(){
    }

    public int getShaderID(){
        return this.shaderID;
    }

    public static Shader loadVertexShader(String filename){
        Shader shaderProgram = new Shader();
        shaderProgram .loadShader(filename, GL20.GL_VERTEX_SHADER);
        return shaderProgram;
    }

    public static Shader loadFragmentShader(String filename){
        Shader shaderProgram = new Shader();
        shaderProgram .loadShader(filename, GL20.GL_FRAGMENT_SHADER);
        return shaderProgram;
    }

    private void loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
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
    }
}
