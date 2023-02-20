package com.example.appjavafx;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import socialnetwork.domain.User;
import socialnetwork.service.ServiceFriendship;
import socialnetwork.service.ServiceUser;
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

import java.io.IOException;
import java.util.Optional;


public class ReqController {

    private User user;
    private User currentUser;
    private ServiceFriendship srvFriendship;

    private ServiceUser srvUser;

    @FXML
    public AnchorPane requestAnchorPane;

    @FXML
    public Label numeLabel;

    @FXML
    private Button rejectButton;

    @FXML
    private Button acceptButton;

    private User user_request;

    public void setData(User user_request){

        if(numeLabel == null){
            System.out.println("null");
        }
        else{
            System.out.println("not");
        }
        //numeLabel.setText(user_request.getLastName() + " " + user_request.getFirstName());
    }

    @FXML
    private void yes(){
        FXMLLoader fxmlLoader2 = new FXMLLoader();
        fxmlLoader2.setLocation(getClass().getResource("/views/accpt.fxml"));
        SearchController searchController = new SearchController();
        Stage  stage = searchController.getPrimaryStage();

        try {
            stage.setScene(fxmlLoader2.load());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        stage.show();
    }

    @FXML
    private void no() throws IOException {


        final Text actiontarget = new Text();
        SearchController src = new SearchController();
        GridPane f = src.getUserGrid();
        /*f.add(actiontarget, 1, 6);
        actiontarget.setFill(Color.FIREBRICK);
        actiontarget.setText("Invalid password!");*/

        FXMLLoader fxmlLoader2 = new FXMLLoader();
        fxmlLoader2.setLocation(getClass().getResource("/views/rej.fxml"));
        SearchController searchController = new SearchController();
        Stage  stage = searchController.getPrimaryStage();

        try {
            stage.setScene(fxmlLoader2.load());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        stage.show();

        String name = numeLabel.getText();
        Long idDelete;
        //SearchController src = new SearchController();
        UserController userController = src.getUsercontroller();
        for(Friendship frs: userController.getFriendRequestList()){
            if(frs.getId2().equals(currentUser.getId()))
            {
                User todeleteuser = srvUser.getUser(frs.getId1());
                if ((todeleteuser.getLastName() + " " + todeleteuser.getFirstName()).equals(name)){
                    idDelete = todeleteuser.getId();
                    srvFriendship.deleteFriendship(idDelete);
                }
            }

            if(frs.getId1().equals(currentUser.getId()))
            {
                User todeleteuser = srvUser.getUser(frs.getId2());
                if ((todeleteuser.getLastName() + " " + todeleteuser.getFirstName()).equals(name)){
                    idDelete = todeleteuser.getId();
                    srvFriendship.deleteFriendship(idDelete);
                }
            }
        }



    }
}
