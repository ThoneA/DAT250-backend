package thoneSpring.sexy.model;

import java.util.UUID;
import java.time.Instant;

public class VoteOption {    
    
    private UUID id;
    private String caption;
    private int presentationOrder;

    public VoteOption() {}

    public VoteOption(UUID id, String caption, int presentationOrder) {
        this.id = id;
        this.caption = caption;
        this.presentationOrder = presentationOrder;
    }

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
}

