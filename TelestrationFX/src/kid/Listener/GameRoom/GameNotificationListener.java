package kid.Listener.GameRoom;

import DTO.Notification.GameRoom.GameInfoNotification;
import com.google.common.eventbus.Subscribe;
import kid.Controller.GameRoomController;
import kid.Controller.TestController;

import java.util.Objects;

public class GameNotificationListener
{

   @Subscribe
   public void handle(GameInfoNotification notification)
   {
       TestController controller = TestController.getController();
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
           controller.setWord("", false);
       }

       controller.setPainter(notification.isPainter());
   }
}
