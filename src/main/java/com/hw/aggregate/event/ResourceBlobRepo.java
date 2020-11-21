package com.hw.aggregate.event;

import com.hw.aggregate.event.model.ResourceBlob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
//@Transactional
public interface ResourceBlobRepo extends CrudRepository<ResourceBlob, String> {
}
