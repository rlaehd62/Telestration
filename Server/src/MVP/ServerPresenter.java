package MVP;

public interface ServerPresenter
{
    void log(String tag, String text);
    void startServer();
    void stopServer();
    boolean isRunning();

    interface ServerView
    {
        void init();
        void updateText();
        void log(String text);
        void setPresenter(ServerPresenter presenter);
    }

    interface ServerModel
    {
        void startServer();
        void stopServer();
        boolean isRunning();
        void setPresenter(ServerPresenter presenter);
    }
}
