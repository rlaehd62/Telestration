package Request;

public class LoginRequest extends GamePacket
{
    private String PW;
    private boolean isSignUp;

    public LoginRequest(String ID, String PW)
    {
        setID(ID);
        setPW(PW);
        setSignUp(false);
    }

    public void setPW(String PW)
    {
        this.PW = PW;
    }

    public String getPW()
    {
        return PW;
    }

    public void setSignUp(boolean signUp)
    {
        isSignUp = signUp;
    }

    public boolean isSignUp()
    {
        return isSignUp;
    }
}
