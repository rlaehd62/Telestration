package kid.Listener.GameRoom;

import kid.Controller.GameRoomController;
import DTO.Response.GameRoom.ChatResponse;
import com.google.common.eventbus.Subscribe;
import kid.Controller.TestController;

public class ChatResponseListener
{
    @Subscribe
    public void handle(ChatResponse response)
    {
        System.out.println("메세지를 받긴 받음 .");
        if(response != null)
        {
            System.out.println(response.toString());
            TestController con = TestController.getController();
            con.receiveChat(response);
        }
    }
}
