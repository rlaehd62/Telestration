package Util;

import javafx.scene.canvas.GraphicsContext;
import java.io.Serializable;
import java.util.ArrayList;

public class SketchBook implements Serializable
{
    private static final long serialVersionUID = 5015030143108005673L;
    private ArrayList<Command> commands;
    private String secretWord;

    public SketchBook(String secretWord)
    {
        this.secretWord = secretWord;
        commands = new ArrayList<>();
    }

    public void addCommand(Command cmd)
    {
        commands.add(cmd);
    }

    public void clearCommands()
    {
        commands.clear();
    }

    public void draw(GraphicsContext gc)
    {
        for(Command cmd : commands)
            cmd.draw(gc);
    }

    public ArrayList<Command> getCommands()
    {
        return commands;
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
