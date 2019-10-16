package tele.client.Room;

import DTO.Request.GamePacket;
import tele.client.Network.Client;
import tele.client.Network.GameClient;
import tele.client.Room.Interface.RoomMVP;

public class RoomModel implements RoomMVP.Model
{
    private RoomMVP.Presenter presenter;
    private GameClient client;

    public void init()
    {
        client = Client.getInstance();
    }

    public void send(GamePacket packet)
    {
        if(packet != null)
            client.send(packet);
    }

    public void setPresenter(RoomMVP.Presenter presenter)
    {
        this.presenter = presenter;
    }
}
