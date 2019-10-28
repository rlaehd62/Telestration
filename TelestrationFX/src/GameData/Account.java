package GameData;


import DTO.Response.Account.AccountResponse;

public class Account
{
    private static Account ins = null;
    private AccountResponse response;

    private Account() {}
    public static Account getInstance()
    {
        return (ins != null) ? ins : (ins = new Account());
    }

    public void setResponse(AccountResponse response)
    {
        this.response = response;
    }

    public String getID()
    {
        return response.getID();
    }
}
