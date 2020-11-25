package com.hw.aggregate.event;

import com.hw.aggregate.event.command.AdminUpdateBizEventCommand;
import com.hw.aggregate.event.representation.AdminBizEventRep;
import com.hw.shared.ServiceUtility;
import com.hw.shared.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.hw.shared.AppConstant.HTTP_HEADER_CHANGE_ID;

@RestController
@RequestMapping(value = "events/admin", produces = "application/json")
public class BizEventController {
    @Autowired
    AdminBizEventApplicationService applicationService;

    @PostMapping("{id}")
    public ResponseEntity<Void> create(@RequestHeader("authorization") String authorization, @RequestBody Object[] data, @PathVariable(name = "id") Long id, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        UserThreadLocal.unset();
        UserThreadLocal.set(ServiceUtility.getUserId(authorization));
        applicationService.create(data, id,changeId);
        return ResponseEntity.ok().header("Location", id.toString()).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AdminBizEventRep> read(@RequestHeader("authorization") String authorization, @PathVariable(value = "id") Long id) {
        UserThreadLocal.unset();
        UserThreadLocal.set(ServiceUtility.getUserId(authorization));
        return ResponseEntity.ok(applicationService.readById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestHeader("authorization") String authorization, @PathVariable(value = "id") Long id, @RequestBody AdminUpdateBizEventCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        UserThreadLocal.unset();
        UserThreadLocal.set(ServiceUtility.getUserId(authorization));
        applicationService.update(id, command,changeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestHeader("authorization") String authorization, @PathVariable(value = "id") Long id, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        UserThreadLocal.unset();
        UserThreadLocal.set(ServiceUtility.getUserId(authorization));
        applicationService.delete(id,changeId);
        return ResponseEntity.ok().build();
    }
}
