package socialnetwork.service;

import socialnetwork.domain.Friendship;
import socialnetwork.domain.Message;
import socialnetwork.domain.User;
import socialnetwork.repository.Repo0;

import java.util.ArrayList;
import java.util.Optional;

public class ServiceMessage {

    private Repo0<Long, Message> messageDB;

    public ServiceMessage(Repo0<Long, Message> repoMessage) {
        this.messageDB = repoMessage;

    }


    public void addMessage(Long id_sender, Long id_receiver, String content) {

        Message mesaj = new Message(id_sender, id_receiver, content);
        messageDB.save(mesaj);
    }


    public Iterable<Message> getAllMessages() {
        return messageDB.findAll();
    }


    public void deleteMessage(Long id) {
        messageDB.delete(id);
    }


    public void updateUser(Long id, Long id_sender, Long id_receiver, String content) {
        Message mesaj = new Message(id_sender, id_receiver, content);
        mesaj.setId_message(id);
        messageDB.update(mesaj);
    }


    public Optional<Message> getMessage(Long id) {
        return messageDB.findOne(id);
    }
}
