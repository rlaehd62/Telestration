package Listener.Room;

import DTO.Request.GameRoom.ChatRequest;
import DTO.Request.Room.CreateRoomRequest;
import DTO.Request.Room.GameRoom;
import DTO.Response.GameRoom.ChatResponse;
import Database.GameDB;
import Database.Manager.GameRoomManager;
import Listener.ServerListener;
import MVP.DataPresenter;
import Server.ChannelManager;
import com.google.common.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

public class CreateRoomListener extends ServerListener<CreateRoomRequest>
{
    @Subscribe
    public void handle(CreateRoomRequest message)
    {
        System.out.println(GameRoomManager.getInstance().getRoomList().size());
        presenter.createRoom(message);

        /*  타이머 테스트 코드 (정상적인 동작 확인)
        Timer timer = new Timer(message.getID() + "'s Room");
        timer.schedule(new TimerTask()
        {
            String owner = message.getID();
            int count = 0;
            public void run()
            {
                count++;
                GameRoom room = GameRoomManager.getInstance().searchRoom(owner);
                ChatResponse response = new ChatResponse(new ChatRequest("Timer", owner, "타이머 " + count + "초"));
                ChannelManager.sendBroadCast(room.getUsers().stream().toArray(java.lang.String[]::new), response);
                if(count >= 30) timer.cancel();
            }
        }, 1000, 1000);
         */
    }
}
