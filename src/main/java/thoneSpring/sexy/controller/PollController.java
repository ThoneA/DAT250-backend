package thoneSpring.sexy.controller;

import org.springframework.web.bind.annotation.*;
import thoneSpring.sexy.model.Poll;
import thoneSpring.sexy.service.PollManager;
import thoneSpring.sexy.model.VoteOption;
import thoneSpring.sexy.model.Vote;

import java.util.Collection;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/polls")
public class PollController {
    
    private final PollManager pollManager;
    
    public PollController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping
    public Collection<Poll> getAllPolls() {
        return pollManager.getAllPolls();
    }

    @GetMapping("/{id}")
    public Poll getPoll(@PathVariable UUID id) {
        return pollManager.getPoll(id);
    }       

    @PostMapping
    public Poll createPoll(@RequestBody Poll poll) {
        return pollManager.addPoll(poll);
    }

    @PutMapping("/{id}")
    public Poll updatePoll(@PathVariable UUID id, @RequestBody Poll poll) {
        return pollManager.updatePoll(id, poll);
    }   

    @DeleteMapping("/{id}")
    public boolean deletePoll(@PathVariable UUID id) {
        return pollManager.deletePoll(id);
    }

    @PostMapping("/{id}/vote")
    public Poll voteOnPoll(@PathVariable UUID id, @RequestBody VoteRequest voteRequest) {
        UUID optionId = voteRequest.getOptionId();
        int delta = voteRequest.getDelta();

        Poll poll = pollManager.getPoll(id);
        if (poll == null) {
            throw new RuntimeException("Poll not found");
        }

        VoteOption option = pollManager.getVoteOption(optionId);
        if (option == null) {
            throw new RuntimeException("Option not found");
        }

        int currentVotes = option.getVotes().size();

        if (delta > 0) {
            for (int i = 0; i < delta; i++) {
                Vote vote = new Vote();
                vote.setVoteOption(option);
                option.getVotes().add(vote);
            }
        } else if (delta < 0) {
            for (int i = 0; i < -delta && !option.getVotes().isEmpty(); i++) {
                option.getVotes().remove(option.getVotes().size() - 1);
            }
        }
        return poll;
    }

        public static class VoteRequest {
            private UUID optionId;
            private int delta;

            public UUID getOptionId() {
                return optionId;
            }

            public void setOptionId(UUID optionId) {
                this.optionId = optionId;
            }

            public int getDelta() {
                return delta;
            }

            public void setDelta(int delta) {
                this.delta = delta;
            }
        }
}