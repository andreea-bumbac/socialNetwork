package socialnetwork.ui;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.Status;
import socialnetwork.domain.User;
import socialnetwork.domain.validators.FriendshipValidator;
import socialnetwork.domain.validators.UserValidator;
import socialnetwork.repository.database.FriendshipDBRepo;
import socialnetwork.repository.database.UserDBRepo;
import socialnetwork.service.ServiceFriendship;
import socialnetwork.service.ServiceUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class UI {

    UserValidator utilizatorValidator = new UserValidator();
    FriendshipValidator friendshipValidator = new FriendshipValidator();


    //InMemoryRepository<Long, User> repoUtilizatori = new InMemoryRepository<>(utilizatorValidator);
    //InMemoryRepository<Long, Friendship> repoFriendship = new InMemoryRepository<>(friendshipValidator);

    //UtilizatorFile0 repoUtilizatoriFile = new UtilizatorFile0("completare_sem3/repository/file/userFile", utilizatorValidator);
    //FriendshipFile0 repoFriendshipFile = new FriendshipFile0("completare_sem3/repository/file/friendshipFile", friendshipValidator);

    String username="postgres";
    String pasword="postgres";
    String url="jdbc:postgresql://localhost:5432/postgres";

    UserDBRepo repoUtilizatorDB =
            new UserDBRepo(url,username, pasword,  utilizatorValidator);
    FriendshipDBRepo repoFriendshipDB =
            new FriendshipDBRepo(url,username, pasword,  friendshipValidator);


    ServiceUser serviceUser = new ServiceUser(repoUtilizatorDB, repoFriendshipDB);
    ServiceFriendship serviceFriendship = new ServiceFriendship(repoUtilizatorDB, repoFriendshipDB);

    public void run() throws IOException {
        String comanda;
        while(true) {
            System.out.println("introduceti comanda: \n");
            System.out.println("0) stop");
            System.out.println("1) add user");
            System.out.println("2) delete user");
            System.out.println("3) show all friendships");
            System.out.println("4) show all users");
            System.out.println("5) comunities ");
            System.out.println("6) add friendship ");
            System.out.println("7) delete friendship ");
            System.out.println("8) update friendship ");
            System.out.println("9) update user \n");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Scanner myInput = new Scanner( System.in );
            comanda = reader.readLine();
            switch (comanda) {
                case "0":
                    return;
                case "1":
                    System.out.println("Add user\n");
                    String nume, prenume, email, parola;
                    System.out.println("Last Name: ");
                    nume = reader.readLine();
                    System.out.println("First Name: ");
                    prenume = reader.readLine();
                    System.out.println("Email: ");
                    email = reader.readLine();
                    System.out.println("Password: ");
                    parola = reader.readLine();
                    serviceUser.addUser(prenume, nume, email, parola);
                    break;
                case "2":
                    System.out.println("Delete user\n ");
                    Long id;
                    System.out.println("id: ");
                    id = myInput.nextLong();
                    serviceUser.deleteUser(id);
                    break;
                case "3":
                    System.out.println("Show all\n ");

                    for (Friendship friend : serviceFriendship.getAllFriendships()) {
                        System.out.println(friend);
                    }
                    break;
                case "4":
                    System.out.println("Show all\n ");

                    for (User user : serviceUser.getAllUsers()) {
                        System.out.println(user);
                    }
                    break;
                case "5":


                    System.out.println("Communities:" + serviceFriendship.getCommunities());
                    System.out.println("Community number:" + serviceFriendship.getCommunityCount());
                    break;
                case "6":
                    Long id1, id2;
                    System.out.println("First id: ");
                    id1 = myInput.nextLong();
                    System.out.println("Second id: ");
                    id2 = myInput.nextLong();
                    serviceFriendship.addFriendship(id1,id2, Status.pending);
                    break;
                case "7":
                    Long id_delete;
                    System.out.println("Friendship id to delete: ");
                    id_delete = myInput.nextLong();
                    serviceFriendship.deleteFriendship(id_delete);
                    break;
                case "8":
                    Long id11, id22, main;
                    System.out.println("Friendship id to update: ");
                    main = myInput.nextLong();
                    System.out.println("first id: ");
                    id11 = myInput.nextLong();
                    System.out.println("second id: ");
                    id22 = myInput.nextLong();
                    serviceFriendship.updateFriendship(main, id11, id22, Status.pending);
                    break;
                case "9":
                    String numenew, prenumenew, emailnew, parolanew;
                    Long userid;
                    System.out.println("User id to update: ");
                    userid = myInput.nextLong();
                    System.out.println("New Last Name: ");
                    numenew = reader.readLine();
                    System.out.println("New First Name: ");
                    prenumenew = reader.readLine();
                    System.out.println("New Email: ");
                    emailnew = reader.readLine();
                    System.out.println("New Password: ");
                    parolanew = reader.readLine();
                    serviceUser.updateUser(userid, prenumenew, numenew, emailnew, parolanew);
                    break;
                default:
                    System.out.println("Wrong option!\n");
            }
        }


    }
}

