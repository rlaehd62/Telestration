package Util;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class SketchBook
{
    private List<Command> commands;
    private String secretWord;

    public SketchBook(String secretWord)
    {
        commands = new ArrayList<>();
        this.secretWord = secretWord;
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

    public void setSecretWord(String secretWord)
    {
        this.secretWord = new String(secretWord);
    }

    public String getSecretWord()
    {
        return secretWord;
    }
}
