package tele.client.Login;

import DTO.Request.Account.LoginRequest;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import tele.client.Login.Interface.LoginPresenter;

public class LoginLayout implements LoginPresenter.LoginView
{
    private LoginPresenter presenter;
    private Skin skin;

    private final int W = 300;
    private final int H = 250;

    private Window root;
    private Label nameLabel;
    private TextField nameText;

    private Label passwordLabel;
    private TextField passwordText;

    private TextButton loginButton;
    private TextButton registerButton;

    public LoginLayout()
    {
        setSkin("Skin/Particle Park UI.json");
    }

    public void initLayout()
    {
        nameLabel = new Label("Name:", skin);
        nameText = new TextField("", skin);

        passwordLabel = new Label("Password:", skin);
        passwordText = new TextField("", skin);
        passwordText.setPasswordMode(true);
        passwordText.setPasswordCharacter('*');

        loginButton = new TextButton("Login", skin);
        registerButton = new TextButton("Register", skin);

        root = new Window("Login / Register", skin);
        root.setMovable(false);
        root.setWidth(W);
        root.setHeight(H);
        root.getTitleLabel().setAlignment(Align.center);

        loadListener();
        loadLayout();
    }

    public void loadLayout()
    {
        int W = 300;
        int H = 250;

        root.add(nameLabel).pad(10);
        root.add(nameText);
        root.row().pad(7, 0, 7, 0);
        root.add(passwordLabel).pad(10);
        root.add(passwordText);
        root.row().pad(7, 0, 7, 0);
        root.add(loginButton).width(120).height(60).padRight(10);
        root.add(registerButton).width(120).height(60);

        Vector2 vector2 = new Vector2(Gdx.graphics.getWidth()/2f-(W/2f), Gdx.graphics.getHeight()/2f-(H/2f));
        root.setPosition(vector2.x, vector2.y);

        presenter.addActor(root);
    }

    public void loadListener()
    {
        loginButton.addListener(new LoginEvent());
        registerButton.addListener(new RegisterEvent());
    }

    public void setSkin(String url)
    {
        skin = new Skin(Gdx.files.internal(url));
    }

    public void setPresenter(LoginPresenter presenter)
    {
        this.presenter = presenter;
    }

    private class LoginEvent extends ClickListener
    {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
        {
            LoginRequest request = new LoginRequest(nameText.getText(), passwordText.getText());
            request.setSubscribable(false);

            System.out.println("ID : " + nameText.getText());
            System.out.println("PW : " + passwordText.getText() + "\n");
            nameText.setText("");
            passwordText.setText("");

            presenter.send(request);
            return true;
        }
    }

    private class RegisterEvent extends ClickListener
    {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
        {
            LoginRequest request = new LoginRequest(nameText.getText(), passwordText.getText());
            request.setSubscribable(true);

            System.out.println("ID : " + nameText.getText());
            System.out.println("PW : " + passwordText.getText() + "\n");
            nameText.setText("");
            passwordText.setText("");

            presenter.send(request);
            return true;
        }
    }
}
