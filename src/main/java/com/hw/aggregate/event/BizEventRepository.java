package com.hw.aggregate.event;

import com.hw.aggregate.event.model.BizEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BizEventRepository extends MongoRepository<BizEvent, Long> {
}
