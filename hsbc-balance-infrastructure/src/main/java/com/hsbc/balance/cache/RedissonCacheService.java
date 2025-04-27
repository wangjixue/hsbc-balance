package com.hsbc.balance.cache;

import com.hsbc.balance.common.cache.CacheService;
import com.hsbc.balance.common.exception.InfrastructureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * RedissonCacheService
 *
 * @author jixueWang
 * @version 2025/4/26
 * 
 **/
@Service
@Slf4j
public class RedissonCacheService implements CacheService {
    /**
     * Redisson客户端实例，用于操作Redisson缓存。
     */
    @Autowired
    RedissonClient redissonClient;

    /**
     *
     * @param bizType 指定的范围。
     * @param key 用于生成标识符的键。
     * @return
     */
    @Override
    public String generateKey(String bizType, String key) {
        if (StringUtils.isBlank(bizType) || StringUtils.isBlank(key)) {
            log.error("generateKey error, bizType or key is null");
            throw new InfrastructureException("bizType or key is null");
        }
        return "balance:" + bizType + ":" + key;
    }

    /**
     *
     * @param key 键，用于标识缓存中的值。
     * @param value 值，需要被缓存的对象。
     * @param expirationTime 过期时间，表示从现在开始到过期的时间长度。
     * @param timeUnit 时间单位，用于指定过期时间的单位。
     * @param <V>
     */
    @Override
    public <V> void add(String key, V value, long expirationTime, TimeUnit timeUnit) {
        try {
            RBucket<V> bucket = redissonClient.getBucket(key);
            bucket.set(value, expirationTime, timeUnit);
        } catch (Exception ex) {
            log.error("add error, key:{}", key, ex);
            throw new InfrastructureException("add error");
        }


    }
    /**
     *
     * @param key 用于查找的键。
     * @return
     * @param <V>
     */
    @Override
    public <V> V get(String key) {
        try {
            RBucket<V> bucket = redissonClient.getBucket(key);
            return bucket.get();
        } catch (Exception ex) {
            log.error("get error, key:{}", key, ex);
            throw new InfrastructureException("get error");
        }

    }


}
