package Server;

import MVP.ServerPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerUI extends JFrame implements ServerPresenter.ServerView
{
    private ServerPresenter presenter;
    private JButton button;
    private JTextArea area;

    private final String START = "서버 시작";
    private final String STOP = "서버 종료";

    public ServerUI()
    {
        setTitle("서버 프로그램");
        setLayout(new BorderLayout());
        init();

        JPanel panel = new JPanel(new GridLayout());
        panel.add(button);

        JScrollPane jScrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setAutoscrolls(true);

        add("North", panel);
        add("Center", jScrollPane);

        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    public void init()
    {
        button = new JButton(START);
        button.addActionListener(new ClickEvent());
        area = new JTextArea();
        area.setEditable(false);
        area.setAutoscrolls(true);
    }

    public void updateText()
    {
        if(presenter.isRunning()) button.setText(START);
        else button.setText(STOP);
    }

    public void log(String text)
    {
        area.append(text + "\n");
        area.setCaretPosition(area.getText().length() - 1);
    }

    public void setPresenter(ServerPresenter presenter)
    {
        this.presenter = presenter;
    }

    private class ClickEvent implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            updateText();
            if(!presenter.isRunning()) presenter.startServer();
            else
            {
                presenter.stopServer();
                button.setEnabled(false);
            }
        }
    }
}
