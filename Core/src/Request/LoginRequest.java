package Request;

public class LoginRequest extends GamePacket
{
    private String PW;
    public LoginRequest(String ID, String PW)
    {
        setID(ID);
        setPW(PW);
    }

    public void setPW(String PW)
    {
        this.PW = PW;
    }

    public String getPassword()
    {
        return PW;
    }
}
