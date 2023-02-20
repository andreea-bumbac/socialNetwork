package socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Friendship extends Entity<Long>{

    private final Long id1;
    private final Long id2;

    private LocalDateTime data;

    private static Long ids = 1L;




    private Status status;

    public Friendship(Long id1, Long id2, LocalDateTime data, Status status){
        this.id1 = Math.min(id1,id2);
        this.id2 = Math.max(id1,id2);
        this.data = data;
        this.status = status;
        setId(ids);
        ids+=1;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = LocalDateTime.now();
    }

    public Long getId1() {
        return id1;
    }

    public Long getId2() {
        return id2;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id_user1=" + id1 +
                ", id_user2=" + id2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Friendship that = (Friendship) o;
        return Objects.equals(id1, that.id1) && Objects.equals(id2, that.id2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id1, id2);
    }
}
