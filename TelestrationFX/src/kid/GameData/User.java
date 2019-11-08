package kid.GameData;

import DTO.Response.User.UserResponse;

public class User
{
    private static User ins = null;
    private UserResponse response;

    private User() {}
    public static User getInstance()
    {
        return (ins != null) ? ins : (ins = new User());
    }

    public void setResponse(UserResponse response)
    {
        this.response = response;
    }

    public int level()
    {
        return response.getLevel();
    }

    public int exp()
    {
        return response.getExp();
    }

    public int maxExp()
    {

        return response.getMaxExp();
    }
}
