package thoneSpring.sexy.model;

/**
 * A simple DTO for transmitting vote events over RabbitMQ.
 * This is not a JPA entity — it’s just a plain message object.
 */
public class VoteEventMessage {

    private String pollId;
    private String voteOptionId;
    private String userId; // optional, can be null for anonymous votes

    public VoteEventMessage() {}

    public VoteEventMessage(String pollId, String voteOptionId, String userId) {
        this.pollId = pollId;
        this.voteOptionId = voteOptionId;
        this.userId = userId;
    }

    public String getPollId() { return pollId; }
    public void setPollId(String pollId) { this.pollId = pollId; }

    public String getVoteOptionId() { return voteOptionId; }
    public void setVoteOptionId(String voteOptionId) { this.voteOptionId = voteOptionId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "VoteEventMessage{" +
                "pollId='" + pollId + '\'' +
                ", voteOptionId='" + voteOptionId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
