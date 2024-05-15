package com.example.project2.repository.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class BaseRedisRepositoryImpl implements BaseRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;

    public BaseRedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    // Lưu cặp key-value vào Redis
    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // Đặt thời gian hết hạn cho một key trong Redis (tính bằng giây)
    @Override
    public void setTimeToLive(String key, long timeoutInSeconds) {
        redisTemplate.expire(key, timeoutInSeconds, TimeUnit.SECONDS);
    }

    // Lưu một trường (field) và giá trị (value) vào Redis với key cho trước
    @Override
    public void hashSet(String key, String field, Object value) {
        hashOperations.put(key, field, value);
    }

    // Kiểm tra xem một trường (field) có tồn tại trong key cho trước trong Redis hay không
    @Override
    public boolean hashExists(String key, String field) {
        return hashOperations.hasKey(key, field);
    }

    // Lấy giá trị của một key trong Redis
    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Lấy giá trị của một trường (field) trong key cho trước trong Redis
    @Override
    public Object hashGet(String key, String field) {
        return hashOperations.get(key, field);
    }

    // Xóa một key khỏi Redis
    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // Xóa một trường (field) khỏi key cho trước trong Redis
    @Override
    public void delete(String key, String field) {
        hashOperations.delete(key, field);
    }
}