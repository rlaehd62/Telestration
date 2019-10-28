package Listener.WaitRoom;

import DTO.Request.Room.CreateRoomRequest;
import DTO.Request.Room.RoomListRequest;
import DTO.Response.Room.CreateRoomResponse;
import GameData.Account;
import GameData.RoomInfo;
import Network.Client;
import com.google.common.eventbus.Subscribe;

public class CreateRoomResponseListener
{
    @Subscribe
    public void handle(CreateRoomResponse response)
    {
        if(!response.isAccepted()) return;

        RoomInfo info = RoomInfo.getInstance();
        if(response.getResponse() != null) info.setResponse(response.getResponse());
        RoomListRequest request = new RoomListRequest(Account.getInstance().getID(), 10);
        Client.getInstance().send(request);

        // RoomList 요청 대신, GameRoom 화면으로 스크린을 바꿀 것.
    }
}
