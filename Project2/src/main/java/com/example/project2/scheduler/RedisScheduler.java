package com.example.project2.scheduler;

import com.example.project2.repository.redis.BaseRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;



@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class RedisScheduler {
    private final BaseRedisRepository redisService;

    // Phương thức này được gọi mỗi 10 phút
    @Scheduled(fixedRate = 600000)
    private void clearCache() {
        redisService.setTimeToLive("A", 10);
        redisService.setTimeToLive("B", 10);
    }
}
