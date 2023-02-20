package socialnetwork.domain;

public class Message extends Entity<Long>{

    private Long id_message;
    private Long id_sender;
    private Long id_receiver;
    private String content;

    private static long ids = 1L;

    public Message(Long id_sender, Long id_receiver, String content) {
        this.id_sender = id_sender;
        this.id_receiver = id_receiver;
        this.content = content;

        setId(ids);
        ids+=1;
    }

    public Long getId_message() {
        return id_message;
    }

    public void setId_message(Long id_message) {
        this.id_message = id_message;
    }

    public Long getId_sender() {
        return id_sender;
    }

    public void setId_sender(Long id_sender) {
        this.id_sender = id_sender;
    }

    public Long getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(Long id_receiver) {
        this.id_receiver = id_receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
