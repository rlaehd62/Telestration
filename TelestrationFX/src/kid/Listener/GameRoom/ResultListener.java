package kid.Listener.GameRoom;

import DTO.Notification.GameRoom.RewardNotification;
import DTO.Request.GameRoom.ChatRequest;
import DTO.Response.GameRoom.ChatResponse;
import com.google.common.eventbus.Subscribe;
import kid.Controller.ResultController;
import kid.Controller.TestController;
import kid.TelestrationFX.ScreenManager;

public class ResultListener
{
    @Subscribe
    public void handle(RewardNotification notification)
    {
        TestController con = TestController.getController();

        ScreenManager.getInstance().activate("ResultScreen");
        ResultController.getController().setNotification(notification);
        ResultController.getController().UpdateResult();

        TestController.getController().init();
        TestController.getController().UpdateUserList();
        TestController.getController().UpdateRoomInfo();
    }
}
