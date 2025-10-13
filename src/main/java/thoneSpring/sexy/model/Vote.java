package thoneSpring.sexy.model;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.Instant;

@Entity
@Table(name = "votes")
public class Vote{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Instant publishedAt;
    private UUID pollId;
    private UUID votedOn;   

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vote_option_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private VoteOption voteOption;

    public Vote() {}
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public VoteOption getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(VoteOption voteOption) {
        this.voteOption = voteOption;
    }

    public UUID getPollId() {
        return pollId;
    }

    public void setPollId(UUID pollId) {
        this.pollId = pollId;
    }

    public UUID getVotedOn() {
        return votedOn;
    }

    public void setVotedOn(UUID votedOn) {
        this.votedOn = votedOn;
    }
}

