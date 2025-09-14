package thoneSpring.sexy.model;

import java.util.List;
import java.util.ArrayList;
import java.time.Instant;
import java.util.UUID;

public class Poll {

    private UUID id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil;

    public Poll() {}

    public Poll(UUID id, String question, Instant publishedAt, Instant validUntil) {
        this.id = id;
        this.question = question;
        this.publishedAt = publishedAt;
        this.validUntil = validUntil;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    private List<VoteOption> options = new ArrayList<>();

    public List<VoteOption> getOptions() {
        return options;
    }

    public void setOptions(List<VoteOption> options) {
        this.options = options;
    }   

}