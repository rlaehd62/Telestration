package tele.client.Login.Interface;

import DTO.Request.GamePacket;

public interface LoginMVP
{
    interface Presenter
    {
        void login(boolean isRegister);
        void showLogin();

        void updateActors();
        void setupView();
        void setupNetwork();
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

        default void load()
        {
            initLayout();
            loadLayout();
            loadListener();
        }

        void initLayout();
        void loadLayout();
        void loadListener();

        void showLoading(boolean editable);
        void showLogin();

        String getID();
        String getPW();

        void setDescription(String text);
        void setPresenter(Presenter presenter);
    }
}
