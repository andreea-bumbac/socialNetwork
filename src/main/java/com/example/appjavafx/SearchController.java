package com.example.appjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socialnetwork.domain.Friendship;
import socialnetwork.domain.Status;
import socialnetwork.domain.User;
import socialnetwork.service.ServiceFriendship;
import socialnetwork.service.ServiceMessage;
import socialnetwork.service.ServiceUser;

import java.io.IOError;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

public class SearchController {
    private ServiceUser serviceUser;

    private ServiceMessage serviceMessage;
    private ServiceFriendship serviceFriendship;

    private User currentUser;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label numeLabel;


    @FXML
    private TextField searchBox;

    @FXML
    private Button searchButton;

    @FXML
    private ScrollPane scrollPane;

    public GridPane getUserGrid() {
        return userGrid;
    }

    @FXML
    private GridPane userGrid;


    @FXML
    public Button friendRequestsButton;


    public Stage getPrimarystage() {
        return Primarystage;
    }

    Stage Primarystage = new Stage();

    public UserController usercontroller = new UserController();

    public void init(Stage stage, ServiceUser serviceUser, User currentUser, ServiceFriendship serviceFriendship) {
        this.serviceUser = serviceUser;
        this.currentUser = currentUser;
        this.serviceFriendship = serviceFriendship;
        Primarystage = stage;
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
                                     @Override
                                     public void handle(ActionEvent e) {
                                         Iterable<User> users = serviceUser.getAllUsers();
                                         int column = 0;
                                         int row = 0;
                                         for (User usr : users) {
                                             if ((usr.getFirstName() + " " + usr.getLastName()).equals(searchBox.getText()) ||
                                                     (usr.getLastName() + " " + usr.getFirstName()).equals(searchBox.getText()) ||
                                                     usr.getLastName().equals(searchBox.getText()) ||
                                                     usr.getFirstName().equals(searchBox.getText())) {
                                                 FXMLLoader fxmlLoader = new FXMLLoader();
                                                 fxmlLoader.setLocation(getClass().getResource("/views/user.fxml"));
                                                 try {
                                                     AnchorPane anchorPane1 = fxmlLoader.load();
                                                     UserController userController = fxmlLoader.getController();
                                                     userController.init(stage, usr, currentUser, serviceFriendship, serviceUser);

                                                     usercontroller = userController;

                                                     if (column == 1) {
                                                         column = 0;
                                                         row++;
                                                     }

                                                     if (usr.getFirstName() == currentUser.getFirstName() && usr.getLastName() == currentUser.getLastName())
                                                         userController.addButton.setText("You");

                                                     Iterable<Friendship> friendships = serviceFriendship.getAllFriendships();
                                                     for (Friendship friend : friendships)
                                                         if ((friend.getId1().equals(usr.getId()) && friend.getId2().equals(currentUser.getId())) ||
                                                                 (friend.getId1().equals(currentUser.getId()) && friend.getId2().equals(usr.getId())))
                                                             userController.addButton.setText("Delete");

                                                     userGrid.add(anchorPane1, column++, row);
                                                 } catch (IOException ex) {
                                                     throw new RuntimeException(ex);
                                                 }
                                             }
                                         }
                                     }
                                 }
        );
    }


    @FXML
    private void hello() throws IOException {

        Iterable<User> users = serviceUser.getAllUsers();
        int column = 0;
        int row = 0;



        if(usercontroller == null){
            System.out.println("null");
        }

        System.out.println(usercontroller.getFriendRequestList().size());
        Friendship fr1 = new Friendship(18L, 29L, LocalDateTime.now(), Status.pending);
        Friendship fr2 = new Friendship(18L, 33L, LocalDateTime.now(), Status.pending);

        usercontroller.getFriendRequestList().add(fr1);
        usercontroller.getFriendRequestList().add(fr2);
        System.out.println(usercontroller.getFriendRequestList().size());


        int okk = 0;
        for (Friendship frs : usercontroller.getFriendRequestList()) {

            ReqController req = new ReqController();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/request.fxml"));
            try {
                AnchorPane anchorPane1 = fxmlLoader.load();
                //UserController userController = fxmlLoader.getController();
                //userController.init(stage, usr, currentUser, serviceFriendship, serviceUser);
                if(anchorPane1 != null && okk<1)
                {
                    Long first = frs.getId1();
                    Long second = frs.getId2();
                    if (first.equals(currentUser.getId())) {
                        User reqUser = serviceUser.getUser(second);
                        req.setData(reqUser);
                        okk = okk + 1;

                        if (column == 1) {
                            column = 0;
                            row++;
                        }
                    }

                    if (second.equals(currentUser.getId())) {
                        User reqUser = serviceUser.getUser(first);
                        req.setData(reqUser);
                        okk = okk+1;
                        //AnchorPane anchorPane1 = fxmlLoader.load();
                        //UserController userController = fxmlLoader.getController();
                        for (User usr : serviceUser.getAllUsers()) {
                            if (usr.getId().equals(first)) {
                                usercontroller.init(Primarystage, usr, currentUser, serviceFriendship, serviceUser);
                                break;
                            }
                        }

                        if (column == 1) {
                            column = 0;
                            row++;
                        }
                    }
                    userGrid.add(anchorPane1, column++, row);
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            RequestController requestController = new RequestController();
            GridPane reqgrid = requestController.getGridPaneReq();
        }

        FXMLLoader fxmlLoader2 = new FXMLLoader();
        fxmlLoader2.setLocation(getClass().getResource("/views/requestList.fxml"));

    }

    public UserController getUsercontroller(){
        return usercontroller;
    }

    public Stage getPrimaryStage(){
        return Primarystage;
    }

    public User getCurrentUser() { return currentUser;}
}

