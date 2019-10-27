package Controller;

import DTO.Request.Room.GameRoom;
import DTO.Response.Room.RoomListResponse;
import DTO.Response.Room.RoomResponse;
import GameData.Account;
import GameData.User;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.Observer;

public class WaitRoomController
{

    @FXML
    private JFXTreeTableView<GameRoomBean> table;

    @FXML
    private Label username;

    @FXML
    private Label level;

    @FXML
    private Label exp;

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField limit;

    @FXML
    private JFXButton creation;

    public static WaitRoomController controller;

    public WaitRoomController()
    {
        System.out.println("테스트");
        controller = this;
    }

    @FXML
    void updateInfo(MouseEvent event)
    {
        username.setText(Account.getInstance().getID());
        level.setText("Lv." + User.getInstance().level());
        exp.setText(String.format("EXP (%d / %d)", User.getInstance().exp(), User.getInstance().maxExp()));
    }


    public void updateUserInfo()
    {
        username.setText(Account.getInstance().getID());
        level.setText("Lv." + User.getInstance().level());
        exp.setText(String.format("EXP (%d / %d)", User.getInstance().exp(), User.getInstance().maxExp()));
    }

    public void updateRoomList(RoomListResponse response)
    {
        JFXTreeTableColumn<GameRoomBean, String> title = new JFXTreeTableColumn<>("Title");
        title.setPrefWidth(100);
        title.setCellValueFactory(param ->
                param.getValue().getValue().title);

        JFXTreeTableColumn<GameRoomBean, String> owner = new JFXTreeTableColumn<>("Owner");
        owner.setPrefWidth(100);
        owner.setCellValueFactory(param ->
                param.getValue().getValue().owner);

        JFXTreeTableColumn<GameRoomBean, String> level = new JFXTreeTableColumn<>("Level 제한");
        owner.setPrefWidth(100);
        owner.setCellValueFactory(param ->
                param.getValue().getValue().limit);

        ObservableList<GameRoomBean> list = FXCollections.observableArrayList();
        for(RoomResponse room : response.getRooms())
        {
            GameRoom gr = room.getRoom();
            GameRoomBean bean = new GameRoomBean(gr);
            list.add(bean);
        }

        final TreeItem<GameRoomBean> root = new RecursiveTreeItem<>(list, RecursiveTreeObject::getChildren);
        table.getColumns().setAll(title, owner, level);
        table.setRoot(root);
        table.setShowRoot(false);
        System.out.println("테스트");
    }

    private class GameRoomBean extends RecursiveTreeObject<GameRoomBean>
    {
        StringProperty owner;
        StringProperty title;
        StringProperty limit;

        public GameRoomBean(GameRoom room)
        {
            owner = new SimpleStringProperty(room.getOwner());
            title = new SimpleStringProperty(room.getTitle());
            limit = new SimpleStringProperty(room.getLevelLimit() + "");
        }
    }
}
