package com.hw.aggregate.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.aggregate.event.model.BizEvent;
import com.hw.aggregate.event.representation.AdminBizEventRep;
import com.hw.shared.cache.CacheCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.hw.shared.AppConstant.CACHE_ID_PREFIX;
import static com.hw.shared.AppConstant.CACHE_QUERY_PREFIX;

@Slf4j
@Service
public class AdminBizEventApplicationService {
    @Autowired
    BizEventRepository repository;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ObjectMapper om;

    public void create(String blob, Long id) {
        BizEvent.create(id, blob, repository);
    }

    public AdminBizEventRep readById(Long id) {
        CacheCriteria cacheCriteria = new CacheCriteria(CacheCriteria.RoleEnum.ADMIN, "id:" + id, null, null);
        String cache = redisTemplate.opsForValue().get(getQueryCacheKey(cacheCriteria));
        BizEvent bizEvent;
        if (cache == null) {
            bizEvent = BizEvent.readById(id, repository);
            try {
                String s = om.writeValueAsString(bizEvent);
                redisTemplate.opsForValue().set(getQueryCacheKey(cacheCriteria), s);
            } catch (JsonProcessingException e) {
                log.error("error during cache update", e);
            }
        } else {
            try {
                bizEvent = om.readValue(cache, BizEvent.class);
            } catch (IOException e) {
                log.error("error during read from redis cache", e);
                bizEvent = BizEvent.readById(id, repository);
            }
        }
        return new AdminBizEventRep(bizEvent);
    }

    public void update(Long id, String blob) {
        cleanUpCache(Collections.singleton(id));
        BizEvent.update(id, blob, repository);
    }

    public void delete(Long id) {
        cleanUpCache(Collections.singleton(id));
        BizEvent.delete(id, repository);
    }

    private String getQueryCacheKey(CacheCriteria cacheCriteria) {
        //sort query param in fixed order
        if (cacheCriteria.getQuery() != null) {
            String[] split1 = cacheCriteria.getQuery().split(",");
            String collect = Arrays.stream(split1).map(e -> {
                String[] split = e.split(":");
                String key = split[0];
                String value = split[1];
                if (value.contains(".")) {
                    String[] split2 = value.split("\\.");
                    TreeSet<String> strings = new TreeSet<>(Arrays.asList(split2));
                    String join = String.join(".", strings);
                    return key + ":" + join;
                } else if (value.contains("$")) {
                    String[] split2 = value.split("\\$");
                    TreeSet<String> strings = new TreeSet<>(Arrays.asList(split2));
                    String join = String.join("$", strings);
                    return key + ":" + join;
                } else {
                    return e;
                }
            }).collect(Collectors.joining(","));
            cacheCriteria.setQuery(collect);
            if (Arrays.stream(split1).anyMatch(e -> e.contains("id:"))) {
                String minId;
                String maxId;
                String s = Arrays.stream(split1).filter(e -> e.contains("id:")).findFirst().get().replace("id:", "");
                if (s.contains(".")) {
                    String[] split2 = s.split("\\.");
                    OptionalLong min = Arrays.stream(split2).mapToLong(Long::parseLong).min();
                    OptionalLong max = Arrays.stream(split2).mapToLong(Long::parseLong).max();
                    minId = String.valueOf(min.getAsLong());
                    maxId = String.valueOf(max.getAsLong());
                } else if (s.contains("$")) {
                    String[] split2 = s.split("\\$");
                    OptionalLong min = Arrays.stream(split2).mapToLong(Long::parseLong).min();
                    OptionalLong max = Arrays.stream(split2).mapToLong(Long::parseLong).max();
                    minId = String.valueOf(min.getAsLong());
                    maxId = String.valueOf(max.getAsLong());
                } else {
                    minId = s;
                    maxId = s;
                }
                return getEntityName() + CACHE_ID_PREFIX + ":" + cacheCriteria.hashCode() + "[" + minId + "-" + maxId + "]";
            }
        }
        return getEntityName() + CACHE_QUERY_PREFIX + ":" + cacheCriteria.hashCode();
    }

    protected String getEntityName() {
        String[] split = BizEvent.class.getName().split("\\.");
        return split[split.length - 1];
    }

    private void cleanUpCache(Set<Long> ids) {
        String entityName = getEntityName();
        Set<String> keys = redisTemplate.keys(entityName + CACHE_QUERY_PREFIX + ":*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
        ids.forEach(id -> {
            Set<String> keys1 = redisTemplate.keys(entityName + CACHE_ID_PREFIX + ":*");
            if (!CollectionUtils.isEmpty(keys1)) {
                Set<String> collect = keys1.stream().filter(e -> {
                    String[] split1 = e.split(":");
                    String[] split2 = split1[1].split("\\[");
                    String s = split2[split2.length - 1];
                    String replace = s.replace("]", "");
                    String[] split3 = replace.split("-");
                    long min = Long.parseLong(split3[0]);
                    long max = Long.parseLong(split3[1]);
                    return id <= max && id >= min;
                }).collect(Collectors.toSet());
                if (!CollectionUtils.isEmpty(collect)) {
                    redisTemplate.delete(collect);
                }
            }
        });
    }

}
