package com.example.appjavafx;

import javafx.application.Application;
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
import socialnetwork.domain.User;
import socialnetwork.domain.validators.FriendshipValidator;
import socialnetwork.domain.validators.UserValidator;
import socialnetwork.repository.Repo0;
import socialnetwork.repository.database.FriendshipDBRepo;
import socialnetwork.repository.database.MessageDBRepo;
import socialnetwork.repository.database.UserDBRepo;
import socialnetwork.service.ServiceFriendship;
import socialnetwork.service.ServiceMessage;
import socialnetwork.service.ServiceUser;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class HelloApplication extends Application {
    UserValidator utilizatorValidator = new UserValidator();
    FriendshipValidator friendshipValidator = new FriendshipValidator();


    String username="postgres";
    String pasword="postgres";
    String url="jdbc:postgresql://localhost:5432/postgres";

    UserDBRepo repoUtilizatorDB =
            new UserDBRepo(url,username, pasword,  utilizatorValidator);
    FriendshipDBRepo repoFriendshipDB =
            new FriendshipDBRepo(url,username, pasword,  friendshipValidator);

    MessageDBRepo messageDBRepo = new MessageDBRepo(url,username, pasword);

    ServiceUser serviceUser = new ServiceUser(repoUtilizatorDB, repoFriendshipDB);
    ServiceFriendship serviceFriendship = new ServiceFriendship(repoUtilizatorDB, repoFriendshipDB);

    ServiceMessage serviceMessage = new ServiceMessage(messageDBRepo);


    User currentUser;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Image img = new Image("D:\\lenovo1\\D\\Facultate\\AN2_SEMESTRU1\\MAP\\appJavafx\\src\\main\\resources\\background.jpg");
        BackgroundImage image = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background image2 = new Background(image);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setBackground(image2);

        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 400, 350);

        Text scenetitle = new Text("Welcome");
        scenetitle.setFill(Color.DARKGOLDENROD);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 4, 1);

        Label firstName = new Label("First Name:");
        firstName.setTextFill(Color.DARKGOLDENROD);
        grid.add(firstName, 0, 1);

        TextField firstnameTextField = new TextField();
        grid.add(firstnameTextField, 1, 1);

        Label lastName = new Label("Last Name:");
        lastName.setTextFill(Color.DARKGOLDENROD);
        grid.add(lastName, 0, 2);

        TextField lastnameTextField = new TextField();
        grid.add(lastnameTextField, 1, 2);

        Label email = new Label("Email:");
        email.setTextFill(Color.DARKGOLDENROD);
        grid.add(email, 0, 3);

        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 3);

        Label password = new Label("Password:");
        password.setTextFill(Color.DARKGOLDENROD);
        grid.add(password, 0, 4);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 4);



        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 5);


        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

                btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                Iterable<User> users = serviceUser.getAllUsers();
                if (pwBox.getText() == "") {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Invalid password!");
                }
                else if (emailTextField.getText() == ""){
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Invalid email!");
                }
                else if (lastnameTextField.getText() == ""){
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Invalid last name!");
                }
                else if (firstnameTextField.getText() == ""){
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Invalid first name!");
                }

                else{
                    int ok = 0;
                    for(User usr: users){
                        if(usr.getFirstName().equals(firstnameTextField.getText()) &&
                        usr.getLastName().equals(lastnameTextField.getText()) &&
                        usr.getEmail().equals(emailTextField.getText()))
                        {
                            actiontarget.setFill(Color.FIREBRICK);
                            actiontarget.setText("Utilizator valid!");
                            ok=1;
                            currentUser=usr;
                        }

                    }
                    if(ok==0)
                    {
                        serviceUser.addUser(firstnameTextField.getText(), lastnameTextField.getText(), emailTextField.getText(),pwBox.getText());
                        Iterable<User> usrs = serviceUser.getAllUsers();
                        for(User usr: usrs){
                            if(usr.getFirstName().equals(firstnameTextField.getText()) &&
                                    usr.getLastName().equals(lastnameTextField.getText()) &&
                                    usr.getEmail().equals(emailTextField.getText()))
                            {
                                currentUser = usr;
                            }

                        }
                    }

                    FXMLLoader fxmlLoader1 = new FXMLLoader();
                    fxmlLoader1.setLocation(getClass().getResource("/views/search.fxml"));
                    try {
                        Parent root = fxmlLoader1.load();
                        SearchController searchController = fxmlLoader1.getController();
                        searchController.init(stage, serviceUser, currentUser, serviceFriendship);
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }





                }
            }
        });



        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
