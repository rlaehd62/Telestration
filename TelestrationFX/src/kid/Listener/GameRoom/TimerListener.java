package kid.Listener.GameRoom;

import DTO.Notification.GameRoom.CurrentTimeNotification;
import com.google.common.eventbus.Subscribe;
import kid.Controller.TestController;

public class TimerListener
{
    @Subscribe
    public void handle(CurrentTimeNotification notification)
    {
        TestController con = TestController.getController();
        con.setTimer((double) notification.getCurrentSeconds() / notification.getMaxSeconds());
    }
}
