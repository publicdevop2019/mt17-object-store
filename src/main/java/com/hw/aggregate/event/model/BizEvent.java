package com.hw.aggregate.event.model;

import com.hw.aggregate.event.BizEventRepository;
import com.hw.shared.Auditable;
import com.hw.shared.UserThreadLocal;
import com.hw.shared.rest.exception.EntityNotExistException;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Data
public class BizEvent extends Auditable implements Serializable {
    @Id
    private Long id;
    private String blob;


    public BizEvent(Long id, String blob) {
        this.id = id;
        this.blob = blob;
    }

    public static void create(Long id, String blob, BizEventRepository repository) {
        BizEvent bizEvent = new BizEvent(id, blob);
        bizEvent.setCreatedAt(new Date());
        bizEvent.setCreatedBy(UserThreadLocal.get());
        repository.save(bizEvent);
    }

    public static BizEvent readById(Long id, BizEventRepository repository) {
        Optional<BizEvent> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotExistException();
        } else {
            if (Boolean.TRUE.equals(byId.get().getDeleted())) {
                throw new EntityNotExistException();
            } else {
                return byId.get();
            }
        }
    }

    public static void update(Long id, String blob, BizEventRepository repository) {
        BizEvent bizEvent = readById(id, repository);
        bizEvent.setBlob(blob);
        bizEvent.setModifiedAt(new Date());
        bizEvent.setModifiedBy(UserThreadLocal.get());
        repository.save(bizEvent);
    }

    public static void delete(Long id, BizEventRepository repository) {
        Optional<BizEvent> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotExistException();
        } else {
            if (Boolean.TRUE.equals(byId.get().getDeleted())) {
            } else {
                BizEvent bizEvent = byId.get();
                bizEvent.setDeleted(true);
                bizEvent.setDeletedAt(new Date());
                bizEvent.setModifiedBy(UserThreadLocal.get());
                repository.save(bizEvent);
            }
        }
    }
}
