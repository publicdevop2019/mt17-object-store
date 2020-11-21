package com.hw.aggregate.event;

import com.hw.shared.ServiceUtility;
import com.hw.shared.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "events/admin", produces = "application/json")
public class BizEventController {
    @Autowired
    AdminBizEventApplicationService applicationService;

    @PostMapping("{id}")
    public ResponseEntity<Void> create(@RequestHeader("authorization") String authorization, @RequestBody String blob, @PathVariable(name = "id") Long id) {
        UserThreadLocal.unset();
        UserThreadLocal.set(ServiceUtility.getUserId(authorization));
        applicationService.create(blob, id);
        return ResponseEntity.ok().header("Location", id.toString()).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<String> read(@RequestHeader("authorization") String authorization, @PathVariable(value = "id") Long id) {
        UserThreadLocal.unset();
        UserThreadLocal.set(ServiceUtility.getUserId(authorization));
        return ResponseEntity.ok(applicationService.readById(id).getBlob());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestHeader("authorization") String authorization, @PathVariable(value = "id") Long id, @RequestBody String blob) {
        UserThreadLocal.unset();
        UserThreadLocal.set(ServiceUtility.getUserId(authorization));
        applicationService.update(id, blob);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestHeader("authorization") String authorization, @PathVariable(value = "id") Long id) {
        UserThreadLocal.unset();
        UserThreadLocal.set(ServiceUtility.getUserId(authorization));
        applicationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
