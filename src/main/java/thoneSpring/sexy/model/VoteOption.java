package thoneSpring.sexy.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "vote_options")
public class VoteOption {    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String caption;
    private int presentationOrder;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    @JsonIgnore // lagt til fordi det skaper sirkulær referanse ved serialisering
    private Poll poll;

    @OneToMany(mappedBy = "voteOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();

    public VoteOption() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getPresentationOrder() {
        return presentationOrder;
    }

    public void setPresentationOrder(int presentationOrder) {
        this.presentationOrder = presentationOrder;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}

