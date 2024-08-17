package org.example.systemserver.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    void setValue(String key, Object value);

    Object getValue(String key);

    Boolean removeValue(String key);

    Boolean expire(String key, Long timeOut, TimeUnit timeUnit);
}
