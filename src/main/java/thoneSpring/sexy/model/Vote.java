package thoneSpring.sexy.model;

import java.util.UUID;
import java.time.Instant;

public class Vote{

    private UUID id;
    private Instant publishedAt;

    public Vote() {}

    public Vote(UUID id, Instant publishedAt) {
        this.id = id;
        this.publishedAt = publishedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }
}

