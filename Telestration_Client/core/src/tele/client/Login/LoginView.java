package tele.client.Login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import tele.client.Login.Interface.LoginMVP;
import tele.client.Main;

public class LoginView implements LoginMVP.View
{
    private LoginMVP.Presenter presenter;

    private Stage stage;
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

    private Label description;

    public LoginView()
    {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal(Main.SKIN));
        Gdx.input.setInputProcessor(stage);
    }

    public void updateActors()
    {
        stage.act(Gdx.graphics.getDeltaTime());
    }

    public void drawActors()
    {
        stage.draw();
    }

    public void disposeAll()
    {
        stage.dispose();
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

        description = new Label("Enter Your Account", skin);
        description.setAlignment(Align.center);

        root = new Window("Login / Register", skin);
        root.setMovable(false);
        root.setWidth(W);
        root.setHeight(H);
        root.getTitleLabel().setAlignment(Align.center);
    }

    public void loadLayout()
    {
        root.add(nameLabel).pad(1f);
        root.add(nameText);
        root.row().pad(7, 0, 7, 0);
        root.add(passwordLabel).pad(1f);
        root.add(passwordText);
        root.row().pad(7, 0, 7, 0);
        root.add(loginButton).width(120).height(60).padRight(10);
        root.add(registerButton).width(120).height(60);
        root.row();
        root.add(description).expandX();

        Vector2 vector2 = new Vector2(Gdx.graphics.getWidth()/2f-(W/2f), Gdx.graphics.getHeight()/2f-(H/2f));
        root.setPosition(vector2.x, vector2.y);
        stage.addActor(root);
    }

    public void loadListener()
    {
        loginButton.addListener(new LoginEvent());
        registerButton.addListener(new RegisterEvent());
    }

    public void showLoading(boolean editable)
    {
        nameText.setDisabled(!editable);
        passwordText.setDisabled(!editable);
        setDescription("Trying to Login ..");
    }

    public void showLogin()
    {
        nameText.setDisabled(false);
        nameText.setText("");

        passwordText.setDisabled(false);
        passwordText.setText("");

        setDescription("Failed to Login");

    }

    public String getID()
    {
        return nameText.getText();
    }

    public String getPW()
    {
        return passwordText.getText();
    }

    public void setDescription(String text)
    {
        this.description.setText(text);
    }

    public void setPresenter(LoginMVP.Presenter presenter)
    {
        this.presenter = presenter;
    }

    private class LoginEvent extends ClickListener
    {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
        {
            presenter.login(false);
            return true;
        }
    }

    private class RegisterEvent extends ClickListener
    {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
        {
            presenter.login(true);
            return true;
        }
    }
}
