package kid.Listener.GameRoom;

import DTO.Notification.GameRoom.GameInfoNotification;
import com.google.common.eventbus.Subscribe;
import kid.Controller.GameRoomController;

import java.util.Objects;

public class GameNotificationListener
{

   @Subscribe
   public void handle(GameInfoNotification notification)
   {
       GameRoomController controller = GameRoomController.getInstance();
       if(notification.getSketchBook() == null)
       {
           System.out.println(notification.getWord());
           System.out.println(notification.isPainter());
           controller.setWord(notification.getWord(), notification.isPainter());
       }
       else if(notification.isPainter())
       {
           controller.clearCanvas();
           controller.setWord(notification.getWord(), notification.isPainter());
       }
       else
       {
           controller.reDraw(notification.getSketchBook());
           controller.setWord("단어 추측 타임!", false);
       }

       controller.setPainter(notification.isPainter());
   }
}
