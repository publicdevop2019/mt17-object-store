package com.hw.aggregate.event;

import com.hw.aggregate.event.ResourceBlobRepo;
import com.hw.aggregate.event.model.ResourceStatusEnum;
import com.hw.aggregate.event.model.Message;
import com.hw.aggregate.event.model.ResourceBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "events/admin",produces = "application/json")
public class DataController {
    @Autowired
    ResourceBlobRepo blobRepo;

    // @note only allow system gen ID in order to avoid duplicate check every time a resource created
    @PostMapping("{id}")
    public ResponseEntity<Message> create(@RequestBody String blob,@PathVariable(name = "id") Long id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            blobRepo.save(new ResourceBlob(id.toString(), blob).setOwner("authentication.getName()"));
            return ResponseEntity.ok(new Message(id.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((new Message("unable to save")));
        }

    }

    @GetMapping("{id}")

    public ResponseEntity<Object> read(@PathVariable(value = "id") String id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(blobRepo.findById(id).isEmpty())
            return null;
//        ResourceBlob var2 = blobRepo.findById(id).get();
        if (blobRepo.existsById(id) && blobRepo.findById(id).get().getMetaInfo().owner.equals("authentication.getName()")
                && blobRepo.findById(id).get().getMetaInfo().status != ResourceStatusEnum.Deleted) {
            Optional<ResourceBlob> ret = blobRepo.findById(id);
            ret.get().getMetaInfo().status = ResourceStatusEnum.Editing;
            blobRepo.save(ret.get());
            return ResponseEntity.ok(ret.get().getBlob());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/resource/exist/{id}/{owner}")
//    @PreAuthorize("hasRole('ROLE_BACKEND') and #oauth2.hasScope('trust') and #oauth2.isClient()")
    public ResponseEntity<String> checkResource(@PathVariable(value = "id") String id, @PathVariable(value = "owner") String owner) {
        if (blobRepo.existsById(id) && blobRepo.findById(id).get().getMetaInfo().owner.equals(owner)
                && blobRepo.findById(id).get().getMetaInfo().status != ResourceStatusEnum.Deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") String id, @RequestBody String blob) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (blobRepo.existsById(id) && blobRepo.findById(id).get().getMetaInfo().owner.equals("authentication.getName()")) {
            ResourceBlob resourceBlob = blobRepo.findById(id).get();
            resourceBlob.getMetaInfo().update();
            resourceBlob.setBlob(blob);
            blobRepo.save(resourceBlob);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") String id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (blobRepo.existsById(id) && blobRepo.findById(id).get().getMetaInfo().owner.equals("authentication.getName()")) {
            Optional<ResourceBlob> ret = blobRepo.findById(id);
            ret.get().getMetaInfo().status = ResourceStatusEnum.Deleted;
            blobRepo.save(ret.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
