package Server;

import MVP.ServerPresenter;

public class ServerManager implements ServerPresenter
{
    private ServerView view;
    private Server server;

    public ServerManager()
    {
        view = new ServerUI();
        server = Server.getInstance();

        view.setPresenter(this);
        server.setPresenter(this);
        log("알림", "프로그램이 시작되었습니다.");
    }

    public void log(String tag, String text)
    {
        String line = "[" + tag + "] " + text;
        view.log(line);
    }

    public void startServer()
    {
        server.startServer();
    }

    public void stopServer()
    {
        server.stopServer();
    }

    public boolean isRunning()
    {
        return server.isRunning();
    }
}
