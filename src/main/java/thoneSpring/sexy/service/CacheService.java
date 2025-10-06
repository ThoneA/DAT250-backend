package thoneSpring.sexy.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.UnifiedJedis;

import java.util.Map;

@Service
public class CacheService {
    private final UnifiedJedis jedis;

    public CacheService() {
        this.jedis = new UnifiedJedis("redis://localhost:6379");
    }

    public Map<String, String> getPollResults(String pollId) {
        Map<String, String> result = jedis.hgetAll("poll:" + pollId);
        System.out.println("[CACHE] GET poll:" + pollId + " -> " + result);
        return result;
    }

    public void setPollResults(String pollId, Map<String, Integer> results, int ttlSeconds) {
        String key = "poll:" + pollId;
        results.forEach((option, count) -> jedis.hset(key, option, String.valueOf(count)));
        jedis.expire(key, ttlSeconds);
        System.out.println("[CACHE] SET poll:" + pollId + " -> " + results + " (TTL: " + ttlSeconds + "s)");
    }

    public void invalidatePollResults(String pollId) {
        jedis.del("poll:" + pollId);
        System.out.println("[CACHE] INVALIDATE poll:" + pollId);
    }

    public void close() {
        jedis.close();
    }
}
