package socialnetwork.service;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.Status;
import socialnetwork.domain.User;
import socialnetwork.repository.Repo0;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class ServiceFriendship {
    //private Repo<Long, Friendship> friendships;
    private Repo0<Long, Friendship> friendshipsBD;
    //private Repo<Long, Utilizator> users;
    private Repo0<Long, User> usersBD;

    public ServiceFriendship( Repo0<Long, User> repoUser, Repo0<Long, Friendship> repoFriendship) {
        this.friendshipsBD = repoFriendship;
        this.usersBD = repoUser;
    }

    /**
     * Creates a friendship between two users
     *
     * @param id1 the id of the first user
     * @param id2 the id of the second user
     */
    public void addFriendship(Long id1, Long id2, Status status) {
        Friendship friendship = new Friendship(id1, id2, LocalDateTime.now(), status);
        friendshipsBD.save(friendship);
    }

    /**
     * Getter for all friendships.
     *
     * @return all friendships
     */
    public Iterable<Friendship> getAllFriendships() {
        return friendshipsBD.findAll();
    }

    /**
     * Deletes a friendship
     *
     * @param id the id of the friendship
     */
    public void deleteFriendship(Long id) {
        friendshipsBD.delete(id);
    }

    /**
     * Updates a friendship
     *
     * @param id  the id of the friendship
     * @param id1 the new id of the first user
     * @param id2 the new id of the second user
     */

    public void updateFriendship(Long id, Long id1, Long id2, Status status) {
        Friendship friendship = new Friendship(id1, id2, LocalDateTime.now(), status);
        friendship.setId(id);
        friendshipsBD.update(friendship);
    }

    /**
     * Returns the friendship with a certain id
     *
     * @param id the id of the friendship
     * @return the friendship with the given id
     */
    public Optional<Friendship> getFriendship(Long id) {
        return friendshipsBD.findOne(id);
    }

    /**
     * Returns the number of communities
     */
    public int getCommunityCount() {
        return getCommunities().size();
    }

    /**
     * Returns the communities
     *
     * @return the communities
     */
    public ArrayList<ArrayList<Long>> getCommunities() {
        ArrayList<ArrayList<Long>> communities = new ArrayList<>();
        ArrayList<Long> visited = new ArrayList<>();
        for (User user : usersBD.findAll()) {
            if (!visited.contains(user.getId())) {
                ArrayList<Long> community = new ArrayList<>();
                community.add(user.getId());
                visited.add(user.getId());
                for (int i = 0; i < community.size(); i++) {
                    for (Friendship friendship : friendshipsBD.findAll()) {
                        if (friendship.getId1().equals(community.get(i)) && !visited.contains(friendship.getId2())) {
                            community.add(friendship.getId2());
                            visited.add(friendship.getId2());
                        }
                        if (friendship.getId2().equals(community.get(i)) && !visited.contains(friendship.getId1())) {
                            community.add(friendship.getId1());
                            visited.add(friendship.getId1());
                        }
                    }
                }
                communities.add(community);
            }
        }
        return communities;
    }

    /**
     * Splits the graph of all users into graphs of communities
     *
     * @return a list of graphs of communities
     */
    private ArrayList<ArrayList<Friendship>> getFriendshipsByCommunities() {
        ArrayList<ArrayList<Long>> users_in_communities = getCommunities();

        ArrayList<ArrayList<Friendship>> communities = new ArrayList<>();

        for (ArrayList<Long> users_in_community : users_in_communities) {
            ArrayList<Friendship> community = new ArrayList<>();
            for (Friendship friendship : friendshipsBD.findAll()) {
                if (users_in_community.contains(friendship.getId1())
                        && users_in_community.contains(friendship.getId2())) {
                    community.add(friendship);
                }
            }
            communities.add(community);
        }

        return communities;
    }

    /**
     * Returns the longegst path of friendships
     *
     * @return the longest path of friendships
     */
    public ArrayList<Long> getLongestFriendshipPath() {
        ArrayList<ArrayList<Friendship>> communities = getFriendshipsByCommunities();

        ArrayList<Long> longest_path = new ArrayList<>();

        for (ArrayList<Friendship> community : communities) {
            ArrayList<Long> path = new ArrayList<>();
            for (Friendship friendship : community) {
                if (!path.contains(friendship.getId1())) {
                    path.add(friendship.getId1());
                }
                if (!path.contains(friendship.getId2())) {
                    path.add(friendship.getId2());
                }
            }
            if (path.size() > longest_path.size()) {
                longest_path = path;
            }
        }

        return longest_path;
    }

    /**
     * Returns the community containing the longest friendship path
     *
     * @return the community containing the longest friendship path
     */
    public ArrayList<Long> getMostSocialCommunity() {
        ArrayList<Long> longest_path = getLongestFriendshipPath();
        ArrayList<ArrayList<Long>> users_in_communities = getCommunities();

        for (ArrayList<Long> users_in_community : users_in_communities) {
            if (users_in_community.containsAll(longest_path)) {
                return users_in_community;
            }
        }

        return null;

    }
}

