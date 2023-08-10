package ru.example.demoapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Friendship(User sender, User receiver){
        this.sender = sender;
        this.receiver = receiver;
        this.friendshipId = new FriendshipId(sender.getId(), receiver.getId());
    }

    public Friendship() {

    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FriendshipId implements Serializable{
        @Column(name = "user_id_sender")
        private Long senderId;

        @Column(name = "user_id_receiver")
        private Long receiverId;
    }
}
