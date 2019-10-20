package DTO.Request.Users;

import DTO.Request.GamePacket;
import com.google.common.eventbus.Subscribe;

public class UserInfoRequest extends GamePacket
{
    public UserInfoRequest(String ID)
    {
        setID(ID);
    }
}
