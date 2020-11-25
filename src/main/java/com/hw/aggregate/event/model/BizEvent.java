package com.hw.aggregate.event.model;

import com.hw.aggregate.event.command.AdminUpdateBizEventCommand;
import com.hw.shared.Auditable;
import com.hw.shared.EntityNotExistException;
import com.hw.shared.UserThreadLocal;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Document
public class BizEvent extends Auditable implements Serializable {
    @Id
    private Long id;
    private String blob;
    @Version
    private Integer version;


    public BizEvent(Long id, String blob) {
        this.id = id;
        this.blob = blob;
    }

    public static void create(Long id, String blob, MongoTemplate mongoTemplate) {
        BizEvent bizEvent = new BizEvent(id, blob);
        bizEvent.setCreatedAt(new Date());
        bizEvent.setCreatedBy(UserThreadLocal.get());
        mongoTemplate.insert(bizEvent);
    }

    public static BizEvent readById(Long id, MongoTemplate mongoTemplate) {
        Query query = getQuery(id);
        List<BizEvent> bizEvents = mongoTemplate.find(query, BizEvent.class);
        if (bizEvents.isEmpty()) {
            throw new EntityNotExistException();
        }
        return bizEvents.get(0);
    }

    private static Query getQuery(Long id) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where(ENTITY_DELETED).exists(false), Criteria.where(ENTITY_DELETED).is(false));
        query.addCriteria(Criteria.where("id").is(id)
                        .andOperator(criteria)
        );
        return query;
    }

    public static void update(Long id, AdminUpdateBizEventCommand blob, MongoTemplate mongoTemplate) {
        BizEvent bizEvent = readById(id, mongoTemplate);
        bizEvent.setBlob(blob.getEvents());
        bizEvent.setModifiedAt(new Date());
        bizEvent.setModifiedBy(UserThreadLocal.get());
        mongoTemplate.save(bizEvent);
    }

    public static void delete(Long id, MongoTemplate mongoTemplate) {
        Query query = getQuery(id);
        Update update = new Update();
        update.set(ENTITY_DELETED, true);
        update.set(ENTITY_DELETED_BY, UserThreadLocal.get());
        update.set(ENTITY_DELETED_AT, new Date());
        mongoTemplate.findAndModify(query, update, BizEvent.class);
    }
}
