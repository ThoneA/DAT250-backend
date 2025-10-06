package thoneSpring.sexy.service;

import org.junit.jupiter.api.*;
import thoneSpring.sexy.model.*;
import java.util.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PollManagerTests {

    private PollManager pollManager;
    private CacheService cacheService;

    @BeforeEach
    void
    setup() {
        cacheService = new CacheService();
        pollManager = new PollManager(cacheService);
    }   

    @AfterEach
    void tearDown() {
        cacheService.close();
    }

 // USER TEST
    @Test
    void testAddAndGetUser() {
        User user = new User();
        user.setUsername("thone");
        user.setEmail("thone@example.com");

        User saved = pollManager.addUser(user);

        assertNotNull(saved.getId(), "User ID should be generated");
        assertEquals("thone", saved.getUsername());
        assertEquals("thone@example.com", saved.getEmail());

        User found = pollManager.getUser(saved.getId());
        assertNotNull(found, "User should be found by ID");
        assertEquals(saved.getId(), found.getId());
    }

// POLL TEST
    @Test
    void testAddAndGetPoll() {
        Poll poll = new Poll();
        poll.setQuestion("Is this a test?");
        poll.setPublishedAt(Instant.now());
        poll.setValidUntil(Instant.now().plusSeconds(3600));

        Poll saved = pollManager.addPoll(poll);

        assertNotNull(saved.getId(), "Poll ID should be generated");
        assertEquals("Is this a test?", saved.getQuestion());

        Poll found = pollManager.getPoll(saved.getId());
        assertNotNull(found, "Poll should be found by ID");
        assertEquals(saved.getId(), found.getId());
    }

    @Test
    void testUpdatePoll() {
        Poll poll = new Poll();
        poll.setQuestion("Initial Question?");
        poll.setPublishedAt(Instant.now());
        poll.setValidUntil(Instant.now().plusSeconds(3600));
        Poll saved = pollManager.addPoll(poll);

        Poll updated = new Poll();
        updated.setQuestion("Updated Question?");
        updated.setPublishedAt(saved.getPublishedAt());
        updated.setValidUntil(saved.getValidUntil());

        Poll result = pollManager.updatePoll(saved.getId(), updated);
        assertNotNull(result);
        assertEquals("Updated Question?", result.getQuestion());
        assertEquals(saved.getId(), result.getId(), "ID should remain the same");
    }

    @Test
    void testDeletePoll() {
        Poll poll = new Poll();
        poll.setQuestion("To be deleted?");
        poll.setPublishedAt(Instant.now());
        poll.setValidUntil(Instant.now().plusSeconds(3600));
        Poll saved = pollManager.addPoll(poll);

        boolean deleted = pollManager.deletePoll(saved.getId());
        assertTrue(deleted, "Poll should be deleted");
        assertNull(pollManager.getPoll(saved.getId()), "Poll should no longer exist");
    }

    // CACHING TEST
    
    // @Test
    // void testPollResultsCachingAndInvalidation() {
    //     // Create poll and options
    //     Poll poll = new Poll();
    //     poll.setTitle("Test Poll");
    //     VoteOption option1 = new VoteOption();
    //     option1.setName("Option 1");
    //     VoteOption option2 = new VoteOption();
    //     option2.setName("Option 2");
    //     poll.setOptions(Arrays.asList(option1, option2));
    //     pollManager.addPoll(poll);

    //     // No votes yet
    //     Map<String, Integer> results = pollManager.getPollResults(poll.getId());
    //     assertEquals(0, results.get("Option 1"));
    //     assertEquals(0, results.get("Option 2"));

    //     // Add a vote
    //     Vote vote = new Vote();
    //     vote.setPollId(poll.getId());
    //     vote.setVotedOn(option1.getId());
    //     pollManager.addVote(vote);

    //     // Results should be recalculated and cache invalidated
    //     Map<String, Integer> resultsAfterVote = pollManager.getPollResults(poll.getId());
    //     assertEquals(1, resultsAfterVote.get("Option 1"));
    //     assertEquals(0, resultsAfterVote.get("Option 2"));
    // }
}




