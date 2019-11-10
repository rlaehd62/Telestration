package Util;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.Serializable;

public class Command implements Serializable
{
    private static final long serialVersionUID = -316460586910691284L;
    private String colorCode;
    private double X;
    private double Y;

    public Command(Color color, double X, double Y)
    {
        this.colorCode = String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
        this.X = X;
        this.Y = Y;
    }

    public Color getColor()
    {
        return Color.valueOf(colorCode);
    }

    public double getX()
    {
        return X;
    }

    public double getY()
    {
        return Y;
    }

    public void draw(GraphicsContext gc)
    {
        Platform.runLater(() ->
        {
            gc.setStroke(Color.valueOf(colorCode));
            gc.moveTo(X, Y);
            gc.stroke();
        });
    }
}
