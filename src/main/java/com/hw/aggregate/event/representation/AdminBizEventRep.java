package com.hw.aggregate.event.representation;

import com.hw.aggregate.event.model.BizEvent;
import lombok.Data;

@Data
public class AdminBizEventRep {
    public Long id;
    public Object[] events;
    public Integer version;

    public AdminBizEventRep(BizEvent bizEvent) {
        id = bizEvent.getId();
        events = bizEvent.getEvents();
        version = bizEvent.getVersion();
    }
}
