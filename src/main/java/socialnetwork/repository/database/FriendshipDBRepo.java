package socialnetwork.repository.database;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.Status;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repo0;

import java.io.ObjectInputFilter;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FriendshipDBRepo implements Repo0<Long, Friendship> {

    private String url;
    private String username;
    private String password;

    private Validator<Friendship> validator;

    public FriendshipDBRepo(String url, String username, String password, Validator<Friendship> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Friendship> findOne(Long newId) {
        String sql = "SELECT * FROM friendships WHERE id_friendship=" + newId.toString();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             //Optional<Friendship> resultSet = statement.executeQuery()) {
             ResultSet resultSet = statement.executeQuery()) {

            Long id = resultSet.getLong("id_friendship");
            Long id1 = resultSet.getLong("id1");
            Long id2 = resultSet.getLong("id2");
            LocalDateTime data = resultSet.getObject("date", LocalDateTime.class);
            String statusdb = resultSet.getString("status");
            Status status = Status.valueOf(statusdb);

            Friendship friendship = new Friendship(id1,id2,data, status);

            return Optional.of(friendship);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id_friendship");
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                LocalDateTime data = resultSet.getObject("date", LocalDateTime.class);
                String statusdb = resultSet.getString("status");
                Status status = Status.valueOf(statusdb);



                Friendship friendship = new Friendship(id1,id2,data, status);
                friendship.setId(id);
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public Optional<Friendship> save(Friendship entity) {
        String sql = "insert into friendships (id1, id2, date, status) values (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, entity.getId1());
            ps.setLong(2, entity.getId2());
            ps.setObject(3, entity.getData());
            ps.setObject(4, Status.pending.toString());


            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Friendship> delete(Long newId) {
        String sql = "DELETE FROM friendships WHERE id_friendship=" + newId.toString();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Friendship> update(Friendship friendship) {
        String sql = "update friendships Set status=? where id_friendship="+friendship.getId().toString();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, friendship.getStatus().toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
