package com.hw.aggregate.event;

import com.hw.aggregate.event.model.ResourceBlob;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ResourceBlobRepo extends MongoRepository<ResourceBlob, String> {
}
