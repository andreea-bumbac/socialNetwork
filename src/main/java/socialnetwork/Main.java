package socialnetwork;


import socialnetwork.domain.Friendship;
import socialnetwork.domain.User;
import socialnetwork.domain.validators.FriendshipValidator;
import socialnetwork.domain.validators.UserValidator;

import socialnetwork.repository.Repo0;
import socialnetwork.repository.database.FriendshipDBRepo;
import socialnetwork.repository.database.UserDBRepo;
import socialnetwork.service.ServiceFriendship;
import socialnetwork.service.ServiceUser;
import socialnetwork.ui.UI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        UserValidator utilizatorValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();

        String username="postgres";
        String pasword="postgres";
        String url="jdbc:postgresql://localhost:5432/postgres";

        UserDBRepo repoUtilizatorDB =
                new UserDBRepo(url,username, pasword,  utilizatorValidator);
        FriendshipDBRepo repoFriendshipDB =
                new FriendshipDBRepo(url,username, pasword,  friendshipValidator);

        ServiceUser serviceUser = new ServiceUser(repoUtilizatorDB, repoFriendshipDB);
        ServiceFriendship serviceFriendship = new ServiceFriendship(repoUtilizatorDB, repoFriendshipDB);

        UI ui = new UI();
        ui.run();


    }
}