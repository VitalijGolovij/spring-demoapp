package ru.example.demoapp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "friendship")
public class Friendship {
    @EmbeddedId
    private FriendshipId friendshipId;

    @ManyToOne
    @JoinColumn(name = "user_id_sender", insertable = false,updatable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "user_id_receiver", insertable = false, updatable = false)
    private User receiver;

    @Embeddable
    public static class FriendshipId implements Serializable{
        @Column(name = "user_id_sender")
        private Long senderId;

        @Column(name = "user_id_receiver")
        private Long receiverId;
    }
}
