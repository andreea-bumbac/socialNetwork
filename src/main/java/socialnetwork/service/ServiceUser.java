package socialnetwork.service;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.User;
import socialnetwork.repository.Repo0;

import java.util.ArrayList;
import java.util.Optional;

public class ServiceUser {

    //private final InMemoryRepository<Long, Utilizator> users;

    //private final InMemoryRepository<Long, Friendship> friendships;
    private Repo0<Long, Friendship> friendshipsDB;

    private Repo0<Long, User> usersDB;
    /**
     * Constructor for the utilizator service.
     */
    public ServiceUser(Repo0<Long, User> repoUser, Repo0<Long, Friendship> repoFriendship) {
        this.usersDB = repoUser;
        this.friendshipsDB = repoFriendship;
    }

    /**
     * Adds a user to the repository.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     */
    public void addUser(String firstName, String lastName, String email, String parola) {
        User user = new User(firstName, lastName, email, parola);
        usersDB.save(user);
    }

    /**
     * Getter for all users.
     *
     * @return all users
     */
    public Iterable<User> getAllUsers() {
        return usersDB.findAll();
    }

    /**
     * Deletes a user
     *
     * @param id the id of the user
     */
    public void deleteUser(Long id) {
        usersDB.delete(id);
        Iterable<Friendship> friendship_itr = friendshipsDB.findAll();
        ArrayList<Friendship> friendshipsToDelete = new ArrayList<>();


        for (Friendship friendship : friendship_itr) {
            if (friendship.getId1().equals(id) || friendship.getId2().equals(id)) {
                friendshipsToDelete.add(friendship);
            }
        }

        for (Friendship friendship : friendshipsToDelete) {
            this.friendshipsDB.delete(friendship.getId());
        }
    }

    /**
     * Updates a user
     *
     * @param id        the id of the user
     * @param firstName the new first name
     * @param lastName  the new last name
     * @param email     the new email
     * @param parola    the new parola
     */
    public void updateUser(Long id, String firstName, String lastName, String email, String parola) {
        User user = new User(firstName, lastName, email, parola);
        user.setId(id);
        usersDB.update(user);
    }

    /**
     * Returns the user with a certain id
     *
     * @param id the id of the user
     * @return the user with the given id
     */
    public User getUser(Long id) {
        User nulluser = new User("null", "null", "null", "null");
        Iterable<User> users = getAllUsers();
        for(User user: users){
            if (user.getId() == id)
                return user;
        }
        return nulluser;
    }
}
