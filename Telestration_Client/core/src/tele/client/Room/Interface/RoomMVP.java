package tele.client.Room.Interface;

import DTO.Request.GamePacket;
import DTO.Response.RoomListResponse;
import DTO.Response.UserResponse;
import tele.client.Login.Interface.LoginMVP;

public interface RoomMVP
{
    interface Presenter
    {
        void updateRoomList();
        void updateRoomList(RoomListResponse response);
        void updateUserInfo(UserResponse response);

        void updateActors();
        void setupView();
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


        void hideInfo();
        void showInfo(UserResponse response);

        default void load()
        {
            initLayout();
            loadLayout();
            loadListener();
        }

        void setRoomList(RoomListResponse response);
        void initLayout();
        void loadLayout();
        void loadListener();

        void setPresenter(Presenter presenter);
    }
}
