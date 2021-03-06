package com.hw.shared.idempotent;

import com.hw.shared.idempotent.model.ChangeRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChangeRepository extends MongoRepository<ChangeRecord, Long> {
    Optional<ChangeRecord> findByChangeIdAndEntityType(String changeId, String entityType);
}
