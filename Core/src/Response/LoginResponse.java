package Response;

import Request.GamePacket;

public class LoginResponse extends GamePacket
{
    private boolean accepted;
    private String ID;
    private String PW;

    public LoginResponse(String ID, String PW, boolean accepted)
    {
        this.ID = ID;
        this.PW = PW;
        this.accepted = accepted;
    }

    public String getID()
    {
        return ID;
    }

    public String getPW()
    {
        return PW;
    }

    public boolean isAccepted()
    {
        return accepted;
    }
}
