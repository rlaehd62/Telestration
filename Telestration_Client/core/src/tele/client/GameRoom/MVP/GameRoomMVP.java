package tele.client.GameRoom.MVP;

import DTO.Request.GamePacket;

public interface GameRoomMVP
{
    interface Presenter
    {
        void setupView();
        void updateActors();
    }

    interface Model
    {
        void send(GamePacket packet);
        void init();
        void setPresenter(Presenter presenter);
    }

    interface View
    {
        void updateActors();
        void drawActors();
        void initLayout();
        void loadLayout();
        void loadListener();

        default void load()
        {
            initLayout();
            loadLayout();
            loadListener();
        }

        void setPresenter(Presenter presenter);
    }
}
