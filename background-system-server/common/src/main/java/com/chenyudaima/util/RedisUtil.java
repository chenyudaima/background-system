package com.chenyudaima.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作工具类（包含分布式锁）（保证所有key都是String数据结构）
 * 记得对象要实现序列化接口
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 字符串型根据key获取值
     * @param key
     * @return 值
     * @param <T> 自动转换
     */
    public <T> T get(String key) {
        return (T)redisTemplate.opsForValue().get(key);
    }

    /**
     * 字符串型添加值
     * @param key
     * @param value
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 列表类型添加值 插入到最左边（表头）位置
     * @param key
     * @param value
     * @return 列表长度
     */
    public Long list_leftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 列表类型添加值 插入到最右边（表尾）位置
     * @param key
     * @param value
     * @return 列表长度
     */
    public Long list_rightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 列表类型获取值 返回并移除最左边（表头）的值
     * @param key
     * @param timeout 如果没有元素,愿意等待的时间
     * @param unit 时间单位
     * @return
     * @param <T>
     */
    public <T> T list_leftPop(String key, long timeout, TimeUnit unit) {
        return (T) redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 列表类型获取值 返回并移除最右边（表尾）的值
     * @param key
     * @param timeout 如果没有元素,愿意等待的时间
     * @param unit 时间单位
     * @return
     * @param <T>
     */
    public <T> T list_rightPop(String key, long timeout, TimeUnit unit) {
        return (T) redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 集合类型添加值 （重复的值会被消除）
     * @param key
     * @param values
     * @return 返回添加成功的个数
     */
    public Long set_add(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 集合类型获取值
     * @param key
     * @return 返回所有值
     */
    public <T> Set<T> set_members(String key) {
        return (Set<T>) redisTemplate.opsForSet().members(key);
    }


    /**
     * hash类型添加值
     * @param key
     * @param hashKey 如果存在则覆盖,等于修改原来的值
     * @param value
     */
    public void hash_put(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * hash类型获取值 根据某个key的值
     * @param key
     * @param hashKey
     * @return
     * @param <T>
     */
    public <T> T hash_get(String key, String hashKey) {
        try {
            return (T) redisTemplate.opsForHash().get(key, hashKey);
        }catch (Exception e) {
            return null;
        }
    }

    /**
     * hash类型删除值 删除内部的某一个key
     * @param key
     * @param hashKey
     * @return 返回删除之后的长度
     */
    public Long hash_delete(String key, String hashKey) {
        return redisTemplate.opsForHash().delete(key, hashKey);
    }


    /**
     * hash类型获取值 获取所有值
     * @param key
     * @return 指定key的所有值
     * @param <T>
     * @param <V>
     */
    public <T, V> Map<T, V> hash_entries(String key) {

        return (Map<T, V>) redisTemplate.opsForHash().entries(key);
    }

    /**
     * 有序集合类型添加值
     * @param key
     * @param value
     * @param score 根据score排序
     * @return 是否添加成功
     */
    public Boolean zSet_add(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 有序集合类型增加元素score值
     * @param key
     * @param value
     * @param score 根据score排序
     * @return 增加后的值
     */
    public Double zSet_incrementScore(String key, Object value, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    /**
     * 有序集合类型获取值 返回有序集合中所有值 (从大到小)
     * @param key
     * @return 所有值
     */
    public <T> Set<T> zSet_reverseRangeWithScores(String key) {
        return (Set<T>) redisTemplate.opsForZSet().reverseRange(key, 0, -1);
    }


    /**
     * 设置key的过期时间 (如果已有过期时间就会重置成当前设置的过期时间)
     * @param key
     * @param time
     * @param unit
     * @return
     */
    public Boolean expire(String key, long time, TimeUnit unit) {
        return redisTemplate.expire(key, time, unit);
    }

    /**
     * 判断key是否存在 (不支持正则匹配)
     * @param key 完整的key
     * @return 是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 判断key是否存在，存在则返回 （支持正则匹配）
     * Keys会引发Redis锁，并且增加Redis的CPU占用，官方建议用集合类型
     * @param pattern  支持 ？(表示代表任何一个字符)  *(表示任何)  [abc](通配括号内的某一个字符)
     * @return 匹配的所有key
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除指定key （包括用stringRedisTemplate添加的key）
     * @param key
     * @return 是否删除成功
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 指定key +1
     * @param key key
     * @return 返回+1之后的大小
     */
    public Long increment(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }


    /**
     * 创建锁操作对象
     */
    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

}
