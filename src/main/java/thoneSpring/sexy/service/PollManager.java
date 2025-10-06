package thoneSpring.sexy.service;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import thoneSpring.sexy.model.Poll;
import thoneSpring.sexy.model.User;
import thoneSpring.sexy.model.Vote;
import thoneSpring.sexy.model.VoteOption;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class PollManager {
    private final Map<UUID, User> users = new HashMap<>();
    private final Map<UUID, Poll> polls = new HashMap<>();
    private final Map<UUID, Vote> votes = new HashMap<>();
    private final Map<UUID, VoteOption> options = new HashMap<>();
    private final CacheService cacheService;

    @Autowired
    public PollManager(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public User addUser(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(UUID id) {
        return users.get(id);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public Poll addPoll(Poll poll) {
        if (poll.getId() == null) {
            poll.setId(UUID.randomUUID());
        }
        for (VoteOption option : poll.getOptions()) {
            if (option.getId() == null) {
                option.setId(UUID.randomUUID());
            }
            option.setVotes(new ArrayList<>());
            options.put(option.getId(), option);
        }
        polls.put(poll.getId(), poll);
        return poll;
    }

    public Poll getPoll(UUID id) {
        return polls.get(id);
    }

    public Collection<Poll> getAllPolls() {
        return polls.values();
    }

    public Poll updatePoll(UUID id, Poll updatedPoll) {
        if (!polls.containsKey(id)) {
            return null;
        }
        updatedPoll.setId(id);
        polls.put(id, updatedPoll);
        return updatedPoll;
    }

    public boolean deletePoll(UUID id) {
        return polls.remove(id) != null;
    }

// VOTE
    public Vote addVote(Vote vote) {
        if (vote.getId() == null) {
            vote.setId(UUID.randomUUID());
        }
        votes.put(vote.getId(), vote);
        // Invalidate cache for poll
        if (vote.getPollId() != null) {
            cacheService.invalidatePollResults(vote.getPollId().toString());
        }
        return vote;
    }

    public Vote getVote(UUID id) {
        return votes.get(id);
    }

    public Collection<Vote> getAllVotes() {
        return votes.values();
    }

    public Vote updateVote(UUID id, Vote updated) {
        if (votes.containsKey(id)) {
            updated.setId(id);
            votes.put(id, updated);
            return updated;
        }
        return null;
    }

    public boolean deleteVote(UUID id) {
        return votes.remove(id) != null;
    }

// VOTE OPTION
    public VoteOption addVoteOption(VoteOption option) {
        if (option.getId() == null) {
            option.setId(UUID.randomUUID());
        }
        options.put(option.getId(), option);
        return option;
    }

    public VoteOption getVoteOption(UUID id) {
        return options.get(id);
    }

    public Collection<VoteOption> getAllVoteOptions() {
        return options.values();
    }

    public VoteOption updateVoteOption(UUID id, VoteOption updated) {
        if (options.containsKey(id)) {
            updated.setId(id);
            options.put(id, updated);
            return updated;
        }
        return null;
    }

    public boolean deleteVoteOption(UUID id) {
        return options.remove(id) != null;
    }

// CACHING
    private Map<String, Integer> queryPollResultsFromDB(UUID pollId) {
        Poll poll = polls.get(pollId);
        if (poll == null) {
            return new HashMap<>();
        }
        return poll.getOptions().stream()
                .collect(Collectors.toMap(
                    VoteOption::getCaption,
                    option -> option.getVotes() != null ? option.getVotes().size() : 0
                ));
    }

     public Map<String, Integer> getPollResults(UUID pollId) {
        // Try cache first
        Map<String, String> cached = cacheService.getPollResults(pollId.toString());
        if (cached != null && !cached.isEmpty()) {
            // Convert String values to Integer
            return cached.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> Integer.parseInt(e.getValue())
                ));
        }
        // Not cached, compute and cache
        Map<String, Integer> results = queryPollResultsFromDB(pollId);
        cacheService.setPollResults(pollId.toString(), results, 60); // TTL 60s
        return results;
    }
}