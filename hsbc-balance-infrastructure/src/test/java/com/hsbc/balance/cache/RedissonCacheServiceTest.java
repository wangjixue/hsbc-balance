package com.hsbc.balance.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class RedissonCacheServiceTest {
    @Mock
    RedissonClient redissonClient;
    @Mock
    RBucket rBucket;
    @InjectMocks
    RedissonCacheService redissonCacheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateKey() {
        String result = redissonCacheService.generateKey("bizType", "key");
        Assertions.assertEquals("balance:bizType:key", result);
    }

    @Test
    void testAdd() {
        when(redissonClient.getBucket(anyString())).thenReturn(rBucket);

        redissonCacheService.add("key", null, 0L, TimeUnit.NANOSECONDS);
    }

    @Test
    void testGet() {
        when(redissonClient.getBucket(anyString())).thenReturn(rBucket);

        String result = redissonCacheService.get("key");
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme