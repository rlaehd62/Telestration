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
    private static final long serialVersionUID = 3350604569882611288L;
    private byte[] imageBytes;
    private String secretWord;
    private String owner;
    private boolean isPainter;

    public SketchBook(String owner, String secretWord)
    {
        this.owner = owner;
        this.secretWord = secretWord;
    }

    public void setPainter(boolean painter)
    {
        isPainter = painter;
    }

    public boolean isPainter()
    {
        return isPainter;
    }

    public String getOwner()
    {
        return owner;
    }

    public void toByte(@NotNull BufferedImage image)
    {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream())
        {
            ImageIO.write(image, "png", baos);
            imageBytes = baos.toByteArray();
            baos.close();
            System.out.println("정보: " + image.toString());
            System.out.println("길이: " + this.imageBytes.length);
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
            bais.close();
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
