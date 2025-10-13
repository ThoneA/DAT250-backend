package thoneSpring.sexy.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import thoneSpring.sexy.model.Vote;
import thoneSpring.sexy.model.VoteOption;
import java.util.UUID;

@Service
public class PollEventListener {

    private final PollManager pollManager;

    public PollEventListener(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @RabbitListener(queues = "polls.queue")
    public void handleVoteEvent(VoteEventMessage message) {
        System.out.println("📩 Received vote event for poll: " + message.pollId);

        VoteOption option = pollManager.getVoteOption(message.optionId);
        if (option == null) {
            System.err.println("⚠️ Option not found for ID: " + message.optionId);
            return;
        }

        // Apply votes
        for (int i = 0; i < message.delta; i++) {
            Vote vote = new Vote();
            vote.setPollId(message.pollId);
            vote.setVoteOption(option);
            pollManager.addVote(vote);
            option.getVotes().add(vote);
        }

        System.out.println("✅ Applied " + message.delta + " vote(s) to option " + message.optionId);
    }

    public static class VoteEventMessage {
        public UUID pollId;
        public UUID optionId;
        public int delta;
    }
}
