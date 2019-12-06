package kid.Listener.GameRoom;

import DTO.Notification.GameRoom.SendSketchBookNotification;
import com.google.common.eventbus.Subscribe;
import kid.Controller.GameRoomController;
import kid.Controller.TestController;
import kid.Network.Client;

public class SendSketchBookListener
{
    @Subscribe
    public void handle(SendSketchBookNotification notification)
    {
        TestController grc = TestController.getController();
        System.out.println("스케치북을 보내달라는 서버의 요청이 도착했습니다.");
        grc.sendCurrentCanvas();
    }
}
