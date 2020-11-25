package com.hw.aggregate.event.model;

import com.hw.aggregate.event.command.AdminUpdateBizEventCommand;
import com.hw.shared.Auditable;
import com.hw.shared.EntityNotExistException;
import com.hw.shared.EntityOutdatedException;
import com.hw.shared.UserThreadLocal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class BizEvent extends Auditable implements Serializable {
    @Id
    private Long id;
    private Object[] events;
    @Version
    private Integer version;


    public BizEvent(Long id, Object[] blob) {
        this.id = id;
        this.events = blob;
    }

    public static void create(Long id, Object[] blob, MongoTemplate mongoTemplate) {
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

    public static void update(Long id, AdminUpdateBizEventCommand command, MongoTemplate mongoTemplate) {
        BizEvent bizEvent = readById(id, mongoTemplate);
        if (!command.getVersion().equals(bizEvent.getVersion())) {
            throw new EntityOutdatedException();
        }
        bizEvent.setEvents(command.getEvents());
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
