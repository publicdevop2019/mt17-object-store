package com.hw.aggregate.event;

import com.hw.aggregate.event.model.BizEvent;
import com.hw.aggregate.event.representation.AdminBizEventRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminBizEventApplicationService {
    @Autowired
    BizEventRepository repository;

    public void create(String blob, Long id) {
        BizEvent.create(id, blob, repository);
    }

    public AdminBizEventRep readById(Long id) {
        return new AdminBizEventRep(BizEvent.readById(id, repository));
    }

    public void update(Long id, String blob) {
        BizEvent.update(id, blob, repository);
    }

    public void delete(Long id) {
        BizEvent.delete(id, repository);

    }
}
