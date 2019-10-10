package tele.client.Login;

import DTO.Request.Account.LoginRequest;
import tele.client.Login.Interface.LoginMVP;

public class LoginPresenter implements LoginMVP.Presenter
{
    private LoginMVP.Model model;
    private LoginMVP.View view;

    public LoginPresenter()
    {
        model = new LoginModel();
        model.setPresenter(this);

        view = new LoginView();
        view.setPresenter(this);
    }

    public void login(boolean isRegister)
    {
        view.showLoading(false);
        LoginRequest request = new LoginRequest(view.getID(), view.getPW());
        request.setSubscribable(isRegister);
        model.send(request);
    }

    public void showLogin()
    {
        view.showLogin();
    }

    public void updateActors()
    {
        view.updateActors();
        view.drawActors();
    }

    public void setupView()
    {
        view.load();
    }

    public void setupNetwork()
    {
        model.init();
    }
}
