package kid.Listener.GameRoom;

import DTO.Notification.GameRoom.GameInfoNotification;
import com.google.common.eventbus.Subscribe;
import kid.Controller.GameRoomController;

public class GameNotificationListener
{
   @Subscribe
   public void handle(GameInfoNotification notification)
   {
       GameRoomController.getInstance().setWord(notification.getWord(), notification.isPainter());
   }
}
