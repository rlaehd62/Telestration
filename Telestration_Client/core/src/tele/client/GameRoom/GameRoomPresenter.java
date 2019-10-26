package tele.client.GameRoom;

import tele.client.GameRoom.MVP.GameRoomMVP;

public class GameRoomPresenter implements GameRoomMVP.Presenter
{
    private GameRoomMVP.Model model;
    private GameRoomMVP.View view;

    public GameRoomPresenter()
    {
        model = new GameRoomModel();
        model.setPresenter(this);

        view = new GameRoomView();
        view.setPresenter(this);
    }

    public void setupView()
    {
        model.init();
        view.load();
    }

    public void updateActors()
    {
        view.updateActors();
        view.drawActors();
    }
}
