package Game;

public class History
{
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
