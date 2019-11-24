package Game;

import java.io.Serializable;

public class History implements Serializable
{
    private static final long serialVersionUID = -5564174255037295881L;
    private int answers;

    public History answer(int n)
    {
        this.answers = n;
        return this;
    }

    public int getAnswers()
    {
        return answers;
    }
}
