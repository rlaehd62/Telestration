package tele.client.Room.Interface;

import DTO.Request.GamePacket;
import DTO.Response.Room.RoomListResponse;
import DTO.Response.User.UserResponse;
import tele.client.Login.Interface.LoginMVP;

public interface RoomMVP
{
    interface Presenter
    {
        void updateRoomList();
        void updateRoomList(RoomListResponse response);

        void updateUserInfo();
        void updateUserInfo(UserResponse response);
        void updateActors();

        void createRoom(String title, int limit);
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
        void initLayout();
        void loadLayout();
        void loadListener();

        default void load()
        {
            initLayout();
            loadLayout();
            loadListener();
        }
        void hideInfo();
        void showInfo(UserResponse response);

        void setSubmit(boolean enabled);
        void setRoomList(RoomListResponse response);
        void setPresenter(Presenter presenter);
    }
}
