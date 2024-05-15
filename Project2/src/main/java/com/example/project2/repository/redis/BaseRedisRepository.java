package com.example.project2.repository.redis;

public interface BaseRedisRepository {

    // Lưu cặp key-value vào Redis
    void set(String key, String value);

    // Đặt thời gian hết hạn cho một key trong Redis (tính bằng giây)
    void setTimeToLive(String key, long timeoutInSeconds);

    // Lưu một trường (field) và giá trị (value) vào Redis với key cho trước
    void hashSet(String key, String field, Object value);

    // Kiểm tra xem một trường (field) có tồn tại trong key cho trước trong Redis hay không
    boolean hashExists(String key, String field);

    // Lấy giá trị của một key trong Redis
    Object get(String key);

    // Lấy giá trị của một trường (field) trong key cho trước trong Redis
    Object hashGet(String key, String field);

    // Xóa một key khỏi Redis
    void delete(String key);

    // Xóa một trường (field) khỏi key cho trước trong Redis
    void delete(String key, String field);

}
