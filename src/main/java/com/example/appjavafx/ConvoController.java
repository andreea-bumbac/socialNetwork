package com.example.appjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import socialnetwork.domain.Friendship;
import socialnetwork.domain.Message;
import socialnetwork.domain.User;
import socialnetwork.repository.database.MessageDBRepo;
import socialnetwork.service.ServiceFriendship;
import socialnetwork.service.ServiceMessage;
import socialnetwork.service.ServiceUser;

import java.io.IOException;
import java.util.Optional;


public class ConvoController {

    private User user;
    private User currentUser;
    private ServiceFriendship srvFriendship;

    private ServiceUser srvUser;

    private ServiceMessage srvMessage;

    @FXML
    public static Scene MesajeScene;

    @FXML
    private AnchorPane MsgAnchorPane;

    @FXML
    private TextField MsgTextField;

    @FXML
    private TextField WriteTextField;

    @FXML
    private Button TrimisButton;

    @FXML
    private GridPane MesajeGridPane;

    int coll1 =0, row1=1;
    Long id;
    public void init(Stage stage, ServiceUser srvUser, User currentUser, ServiceMessage srvMessage){

        this.srvUser = srvUser;
        this.currentUser = currentUser;
        this.srvMessage = srvMessage;

        id=currentUser.getId();
    }

    public void showMessages() {
        String username="postgres";
        String pasword="postgres";
        String url="jdbc:postgresql://localhost:5432/postgres";
        MessageDBRepo repo = new MessageDBRepo(url,username, pasword);
        srvMessage = new ServiceMessage(repo);

        for(Message msg: srvMessage.getAllMessages()) {


            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/message.fxml"));



                try {
                    AnchorPane anchorPane1 = fxmlLoader.load();
                    MsgController msgController = fxmlLoader.getController();
                    msgController.setDataMsg(msg);

                    MesajeGridPane.add(anchorPane1, coll1, row1);
                    row1++;
                    coll1++;

                    if(coll1==2)
                    {
                        coll1=0;
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }



        }
    }

    int coll =0, row=0;
    @FXML
    private void message_send() throws IOException {

        SearchController src = new SearchController();
        String message = WriteTextField.getText();
        UserController userController = new UserController();
        User receiver = userController.getReceiver();

        User us=new User("null", "null", "null", "null");
        String username="postgres";
        String pasword="postgres";
        String url="jdbc:postgresql://localhost:5432/postgres";

        MessageDBRepo repo = new MessageDBRepo(url,username, pasword);
        ServiceMessage serviceMessage = new ServiceMessage(repo);
        Message mesaj_add = new Message(us.getId(), receiver.getId(), message);

        showMessages();

        serviceMessage.addMessage(us.getId(), receiver.getId(), message);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/message.fxml"));

        try {
            AnchorPane anchorPane1 = fxmlLoader.load();
            MsgController msgController = fxmlLoader.getController();
            msgController.setDataMsg(mesaj_add);
            GridPane newgrid = new GridPane();
            //MsgGridPane=newgrid;



            MesajeGridPane.add(anchorPane1, coll, row1);
            row1++;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
