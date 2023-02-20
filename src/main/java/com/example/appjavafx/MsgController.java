package com.example.appjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import socialnetwork.domain.Friendship;
import socialnetwork.domain.Message;
import socialnetwork.domain.User;
import socialnetwork.domain.validators.FriendshipValidator;
import socialnetwork.domain.validators.UserValidator;
import socialnetwork.repository.database.FriendshipDBRepo;
import socialnetwork.repository.database.UserDBRepo;
import socialnetwork.service.ServiceFriendship;
import socialnetwork.service.ServiceMessage;
import socialnetwork.service.ServiceUser;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class MsgController {

    private User user;
    private User currentUser;
    private ServiceFriendship srvFriendship;

    private ServiceUser srvUser;

    private ServiceMessage srvMessage;

    @FXML
    private AnchorPane MsgAnchorPane;

    @FXML
    private Label NMsgLabel;

    @FXML
    private Label CMsgLabel;



    public void setDataMsg(Message mesaj){
        //this.currentUser = currtUser;
        Long id_sender = mesaj.getId_sender();
        String content = mesaj.getContent();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/search.fxml"));

        try {
            AnchorPane anchorPane1 = fxmlLoader.load();
            SearchController srcController = fxmlLoader.getController();

            //User sender = srcController.getCurrentUser();

            srcController.init(new Stage(), srvUser, currentUser, srvFriendship);


            String username="postgres";
            String pasword="postgres";
            String url="jdbc:postgresql://localhost:5432/postgres";

            UserValidator utilizatorValidator = new UserValidator();
            FriendshipValidator friendshipValidator = new FriendshipValidator();
            UserDBRepo repoUtilizatorDB =
                    new UserDBRepo(url,username, pasword,  utilizatorValidator);
            FriendshipDBRepo repoFriendshipDB =
                    new FriendshipDBRepo(url,username, pasword,  friendshipValidator);
            ServiceUser serviceUser = new ServiceUser(repoUtilizatorDB, repoFriendshipDB);


            User us=new User("d", "d", "d", "d");

            NMsgLabel.setText(" ");

            CMsgLabel.setText(content);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
