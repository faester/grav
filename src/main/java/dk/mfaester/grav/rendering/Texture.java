package dk.mfaester.grav.rendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by Morten.Faester on 16-02-14.
 */
public class Texture {
    private final int textureId;

    private Texture(int textureId){
        this.textureId = textureId;
    }

    public static Texture load(InputStream stream){
        final int mask = 0x000000FF;
        ByteBuffer byteBuffer;
        BufferedImage texture0;
        try {
            texture0 = ImageIO.read(stream);
            if (texture0.getType() != BufferedImage.TYPE_4BYTE_ABGR){
                throw new RuntimeException("Wrong image type " + texture0.getType());
            }
            byteBuffer = BufferUtils.createByteBuffer(texture0.getWidth() * texture0.getHeight() * 4);
            for (int x = 0; x < texture0.getWidth(); x++){
                for(int y = 0; y < texture0.getHeight(); y++){
                    int p = texture0.getRGB(x, y);
                    int alpha = (p >> 24) & mask;
                    int b = (p >> 0) & mask;
                    int g = (p >> 8) & mask;
                    int r = (p >> 16) & mask;
                    byteBuffer.put((byte)r);
                    byteBuffer.put((byte)g);
                    byteBuffer.put((byte)b);
                    byteBuffer.put((byte)alpha);
                }
            }


        } catch (IOException e) {
            throw new RuntimeException("Could not load textures.");
        }

        byteBuffer.flip();

        int textureId = GL11.glGenTextures();
        GL13.glActiveTexture(Texture.getTextureUnit());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, texture0.getWidth(), texture0.getHeight(),
                0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, byteBuffer);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        return new Texture(textureId);
    }

    public int getTextureId() {
        return textureId;
    }

    public static int getTextureUnit() {
        return GL13.GL_TEXTURE0;
    }
}
