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
import socialnetwork.domain.User;
import socialnetwork.service.ServiceFriendship;
import socialnetwork.service.ServiceUser;

import java.io.IOException;
import java.util.Optional;

public class RequestController {

    private User user;
    private User currentUser;
    private ServiceFriendship srvFriendship;

    private ServiceUser srvUser;

    @FXML
    public Label numeLabel;

    @FXML
    private Button acceptButton;

    @FXML
    private Button rejectButton;

    @FXML
    private Scene sceneReq;

    @FXML
    private AnchorPane anchorPaneReq;

    @FXML
    private AnchorPane anchorPanneReq;

    @FXML
    private ScrollPane scrollPaneReq;

    @FXML
    private GridPane gridPaneReq;

    @FXML
    public GridPane reqList;
    @FXML
    public Button friendRequestsButton;

    public GridPane getGridPaneReq(){
        return reqList;
    }


    public void init(Stage stage, ServiceUser srvUser, User currentUser, ServiceFriendship srvFriendship) {
        this.srvUser = srvUser;
        this.currentUser = currentUser;
        this.srvFriendship = srvFriendship;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/views/user.fxml"));
        UserController userController = fxmlLoader.getController();

        int column = 0;
        int row = 0;

        int okk=0;
        /*for (Friendship frs : userController.getFriendRequestList()){


            try {
                AnchorPane anchorPane1 = fxmlLoader.load();
                Long first = frs.getId1();
                Long second = frs.getId2();
                if (first == currentUser.getId()) {
                    Optional<User> reqUser = srvUser.getUser(second);
                    numeLabel.setText(reqUser.get().getLastName() + ' ' + reqUser.get().getFirstName());
                    okk = 1;
                    //AnchorPane anchorPane1 = fxmlLoader.load();
                    //UserController userController = fxmlLoader.getController();


                    if (column == 1) {
                        column = 0;
                        row++;
                    }
                }

                if (second == currentUser.getId()) {
                    Optional<User> reqUser = srvUser.getUser(first);
                    numeLabel.setText(reqUser.get().getLastName() + ' ' + reqUser.get().getFirstName());
                    okk = 2;
                    //AnchorPane anchorPane1 = fxmlLoader.load();
                    //UserController userController = fxmlLoader.getController();
                    for(User usr: srvUser.getAllUsers()) {
                        if (usr.getId() == first) {
                            userController.init(usr, currentUser, srvFriendship);
                            break;
                        }
                    }

                    if (column == 1) {
                        column = 0;
                        row++;
                    }
                }
                reqList.add(anchorPane1, column++, row);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(okk==0){

            numeLabel.setText("no friends");

        }


        FXMLLoader fxmlLoader2 = new FXMLLoader();
        fxmlLoader2.setLocation(getClass().getResource("/views/requestList.fxml"));


        RequestController reqController = fxmlLoader2.getController();
        reqController.init(stage, srvUser, currentUser, srvFriendship);
    }*/}

    public void addOnGrid(AnchorPane anch, int col, int row)
    {
        if(reqList == null)
            System.out.println("null");
        reqList.add(anch, col++, row);
    }

}