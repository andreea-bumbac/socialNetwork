package socialnetwork.repository.database;

import socialnetwork.domain.Message;
import socialnetwork.repository.Repo0;

import java.sql.*;
import java.util.*;

public class MessageDBRepo implements Repo0<Long, Message> {

    private String url;
    private String username;
    private String password;

    public MessageDBRepo(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public Optional<Message> findOne(Long newId) {
        String sql = "SELECT * FROM messages WHERE id_message=" + newId.toString();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             //Optional<Utilizator> resultSet = statement.executeQuery()) {
             ResultSet resultSet = statement.executeQuery()) {

            //if(resultSet != null)
            //return resultSet;
            Long id_message = resultSet.getLong("id_message");
            Long id_sender = resultSet.getLong("id_sender");
            Long id_receiver = resultSet.getLong("id_receiver");
            String content = resultSet.getString("content");

            Message mesaj = new Message(id_sender, id_receiver, content);

            return Optional.of(mesaj);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Iterable<Message> findAll() {
        List<Message> messages = new ArrayList<>() {
        };
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from messages ORDER BY id_message ASC");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id_message = resultSet.getLong("id_message");
                Long id_sender = resultSet.getLong("id_sender");
                Long id_receiver = resultSet.getLong("id_receiver");
                String content = resultSet.getString("content");

                Message mesaj = new Message(id_sender, id_receiver, content);
                mesaj.setId_message(id_message);
                messages.add(mesaj);
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }


    @Override
    public Optional<Message> save(Message entity) {
        String sql = "insert into messages (id_sender, id_receiver, content) values (?, ?, ?)";

        Long currentId=7l;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, entity.getId_sender());
            ps.setLong(2, entity.getId_receiver());
            ps.setString(3, entity.getContent());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Optional<Message> delete(Long newId) {
        String sql = "DELETE FROM messages WHERE id_message=" + newId.toString();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Message> update(Message entity) {
        String sql = "update messages set id_sender=?, id_receiver=?, content=? where id_message="+entity.getId_message().toString();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, entity.getId_sender());
            ps.setLong(2, entity.getId_receiver());
            ps.setString(3, entity.getContent());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
