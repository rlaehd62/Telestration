package Util;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import javafx.scene.canvas.GraphicsContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class SketchBook implements Serializable
{
    private static final long serialVersionUID = 5015030143108005673L;

    private byte[] imageBytes;
    private String secretWord;

    public SketchBook(String secretWord)
    {
        this.secretWord = secretWord;
    }

    public void toByte(@NotNull BufferedImage image)
    {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream())
        {
            ImageIO.write(image, "jpg", baos);
            byte[] arr = baos.toByteArray();
            imageBytes = arr;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public @Nullable BufferedImage toImage()
    {
        try(ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes))
        {
            BufferedImage image = ImageIO.read(bais);
            return image;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public void setSecretWord(String secretWord)
    {
        this.secretWord = new String(secretWord);
    }

    public String getSecretWord()
    {
        return secretWord;
    }
}
