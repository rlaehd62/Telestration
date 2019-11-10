package Util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Command
{
    private Color color;
    private int X;
    private int Y;

    public Command(Color color, int X, int Y)
    {
        this.color = color;
        this.X = X;
        this.Y = Y;
    }

    public void draw(GraphicsContext gc)
    {
        gc.setStroke(color);
        gc.beginPath();
        gc.moveTo(X, Y);
        gc.stroke();
    }
}
