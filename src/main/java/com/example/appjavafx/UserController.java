package com.example.appjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import socialnetwork.domain.Friendship;
import socialnetwork.domain.Status;
import socialnetwork.domain.User;
import socialnetwork.repository.database.FriendshipDBRepo;
import socialnetwork.service.ServiceFriendship;
import socialnetwork.service.ServiceMessage;
import socialnetwork.service.ServiceUser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserController {
    private User user;
    private User currentUser;
    private ServiceFriendship srvFriendship;

    private ServiceMessage srvMessage;

    private ServiceUser srvUser;
    @FXML
    private Label firstnameLabel;

    @FXML
    private Label lastnameLabel;

    public Set<Friendship> friendRequestList = new HashSet<>();
    @FXML
    public Button addButton;

    @FXML
    public Button sendMsgButton;

    public Button friendRequestsButton;

    Stage Primstage = new Stage();


    public void init(Stage stage, User user, User currentUser, ServiceFriendship srvFriendship, ServiceUser srvUser) {
        this.user = user;
        this.currentUser = currentUser;
        this.srvFriendship = srvFriendship;
        this.srvUser = srvUser;
        firstnameLabel.setText(user.getFirstName());
        lastnameLabel.setText(user.getLastName());

        Primstage = stage;
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (addButton.getText() == "Add") {

                    srvFriendship.addFriendship(currentUser.getId(), user.getId(), Status.pending);
                    addButton.setText("Pending");

                    for (Friendship frd : srvFriendship.getAllFriendships())
                        if (frd.getId1().equals(currentUser.getId()) && frd.getId2().equals(user.getId())) {

                            friendRequestList.add(frd);

                        }
                } else if (addButton.getText() == "Delete") {
                    Long idDelete = 0l;
                    Iterable<Friendship> friendships = srvFriendship.getAllFriendships();

                    for (Friendship friend : friendships)
                        if ((friend.getId1().equals(user.getId()) && friend.getId2().equals(currentUser.getId())) ||
                                (friend.getId1().equals(currentUser.getId()) && friend.getId2().equals(user.getId())))
                            idDelete = friend.getId();

                    srvFriendship.deleteFriendship(idDelete);
                    addButton.setText("Add");
                } else {
                    addButton.setText("You");
                }
            }
        });
    }

    String prenume_receiver, nume_receiver;
    User receiver = new User("null", "null", "null", "null");

    @FXML
    private void conv() throws IOException {
        FXMLLoader fxmlLoader2 = new FXMLLoader();
        fxmlLoader2.setLocation(getClass().getResource("/views/mesaje.fxml"));
        prenume_receiver = firstnameLabel.getText();
        nume_receiver = lastnameLabel.getText();

        for(User user: srvUser.getAllUsers()) {
            if (user.getFirstName().equals(prenume_receiver) && user.getLastName().equals(nume_receiver)) {
                receiver = user;
            }
        }




        FXMLLoader fxmlLoader4 = new FXMLLoader();
        fxmlLoader4.setLocation(getClass().getResource("/views/mesaje.fxml"));
        fxmlLoader4.load();
        ConvoController convoController = fxmlLoader4.getController();
        convoController.init(Primstage, srvUser, currentUser, srvMessage);

        try {
            Primstage.setScene(fxmlLoader2.load());
            convoController.showMessages();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Primstage.show();


    }

    public User getReceiver(){
        return receiver;
    }

    public Set<Friendship> getFriendRequestList(){
        return friendRequestList;
    }
}
