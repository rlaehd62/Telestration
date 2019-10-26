package tele.client.GameRoom;

import DTO.Request.GamePacket;
import tele.client.GameRoom.MVP.GameRoomMVP;
import tele.client.Network.Client;
import tele.client.Network.GameClient;

public class GameRoomModel implements GameRoomMVP.Model
{
    private GameRoomMVP.Presenter presenter;
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

    public void setPresenter(GameRoomMVP.Presenter presenter)
    {
        this.presenter = presenter;
    }
}
