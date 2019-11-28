package kid.Listener.GameRoom;

import DTO.Notification.GameRoom.GameInfoNotification;
import com.google.common.eventbus.Subscribe;
import kid.Controller.TestController;

import java.awt.*;
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

           if(notification.isOdd()) controller.setAccess(false, false);
           else controller.setAccess(true, false);
       }
       else process(notification);
       controller.setReceived(notification.getSketchBook());
       controller.setPainter(notification.isPainter());
   }

   private void process(GameInfoNotification notification)
   {
       TestController controller = TestController.getController();
       if(notification.isPainter())
       {
           controller.clearCanvas();
           controller.setWord(notification.getWord(), notification.isPainter());
       }
       else
       {
           if(!Objects.isNull(notification.getSketchBook().toImage())) controller.reDraw(notification.getSketchBook());
           controller.setWord("", false);
       }

       controller.setAccess((notification.isPainter()), !notification.isPainter());
   }
}
