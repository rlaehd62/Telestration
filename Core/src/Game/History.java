package Game;

import Util.SketchBook;

import java.io.Serializable;
import java.util.HashMap;

public class History implements Serializable
{
    private static final long serialVersionUID = -5564174255037295881L;
    private int round;
    private int answers;
    private HashMap<String, SketchBook> sketchbooks;
    private HashMap<String, Integer> answerCount;

    public History()
    {
        sketchbooks = new HashMap<>();
        answerCount = new HashMap<>();
    }

    public History answer(int n)
    {
        this.answers = n;
        return this;
    }

    public History round(int n)
    {
        this.round = n;
        return this;
    }

    public History saveSketchbook(String owner, SketchBook book)
    {
        sketchbooks.put(owner, book);
        return this;
    }

    public History setAnswerCound(String name, int n)
    {
        answerCount.put(name, n);
        return this;
    }

    public int getAnswers()
    {
        return answers;
    }

    public int getRound()
    {
        return round;
    }

    public HashMap<String, SketchBook> getSketchbooks()
    {
        return sketchbooks;
    }

    public HashMap<String, Integer> getAnswerCount()
    {
        return answerCount;
    }
}
