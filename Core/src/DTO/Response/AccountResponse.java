package DTO.Response;

import DTO.Request.Account.LoginRequest;

import java.io.Serializable;

public class AccountResponse implements GamePacketResponse
{
    private String ID;
    private String password;
    private boolean accepted;

    public AccountResponse(LoginRequest request, boolean isAccepted)
    {
        setID(request.getID());
        setPassword(request.getPassword());
        setAccepted(accepted);
    }

    public AccountResponse(String ID, String password, boolean accepted)
    {
        this.ID = ID;
        this.password = password;
        this.accepted = accepted;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getID()
    {
        return ID;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public void setAccepted(boolean accepted)
    {
        this.accepted = accepted;
    }

    public boolean isAccepted()
    {
        return accepted;
    }
}
