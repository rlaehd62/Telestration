package DTO.Request.Account;

import DTO.Request.GamePacket;

public class LoginRequest extends GamePacket
{
    private String password;
    private boolean subscribable;

    public LoginRequest(String ID, String password)
    {
        setID(ID);
        setPassword(password);
        setSubscribable(false);
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public void setSubscribable(boolean subscribable)
    {
        this.subscribable = subscribable;
    }

    public boolean isSubscribable()
    {
        return subscribable;
    }
}
