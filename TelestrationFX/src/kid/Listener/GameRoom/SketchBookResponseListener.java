package kid.Listener.GameRoom;

import DTO.Request.GameRoom.ChatRequest;
import DTO.Request.Room.GameRoom;
import DTO.Response.GameRoom.ChatResponse;
import DTO.Response.GameRoom.SketchBookResponse;
import Util.SketchBook;
import com.google.common.eventbus.Subscribe;
import kid.Controller.GameRoomController;

public class SketchBookResponseListener
{

    @Subscribe
    public void handle(SketchBookResponse response)
    {
        SketchBook book = response.getSketchBook();
        ChatResponse response1 = new ChatResponse(new ChatRequest("받은 단어", "", book.getSecretWord()));
        GameRoomController.getInstance().receiveChat(response1);
        GameRoomController.getInstance().reDraw(book);
    }
}
