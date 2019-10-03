package DTO.Response;

public class AccountResponse
{
    private String ID;
    private String password;
    private boolean accepted;

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

    public boolean isAccepted()
    {
        return accepted;
    }
}
