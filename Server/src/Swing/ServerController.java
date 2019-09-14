package Swing;

import Server.Server;

public class ServerController
{
    private ServerUI view;
    private Server server;

    public ServerController()
    {
        view = new ServerUI();
        server = Server.getInstance();

        view.setController(this);
        server.setController(this);
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
