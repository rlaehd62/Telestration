package kid.Listener.GameRoom;

import DTO.Request.GameRoom.ChatRequest;
import DTO.Response.GameRoom.ChatResponse;
import DTO.Response.GameRoom.SketchBookResponse;
import Util.SketchBook;
import com.google.common.eventbus.Subscribe;
import kid.Controller.GameRoomController;
import kid.Controller.TestController;

public class SketchBookResponseListener
{

    @Subscribe
    public void handle(SketchBookResponse response)
    {
        SketchBook book = response.getSketchBook();
        ChatResponse response1 = new ChatResponse(new ChatRequest("받은 단어", "", book.getSecretWord()));
        TestController.getController().receiveChat(response1);
        TestController.getController().reDraw(book);
    }
}
