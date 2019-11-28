package kid.Listener.GameRoom;

import DTO.Notification.GameRoom.RewardNotification;
import DTO.Request.GameRoom.ChatRequest;
import DTO.Response.GameRoom.ChatResponse;
import com.google.common.eventbus.Subscribe;
import kid.Controller.TestController;

public class ResultListener
{
    @Subscribe
    public void handle(RewardNotification notification)
    {
        TestController con = TestController.getController();
        notification.getResult()
                .forEach((s, integer) ->
                        con.receiveChat(new ChatResponse(new ChatRequest(s, "", "경험치 " + integer + "를 획득했습니다."))));
    }
}
