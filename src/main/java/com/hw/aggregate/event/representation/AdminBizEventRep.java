package com.hw.aggregate.event.representation;

import com.hw.aggregate.event.model.BizEvent;
import lombok.Data;

@Data
public class AdminBizEventRep {
    public Long id;
    public String blob;

    public AdminBizEventRep(BizEvent bizEvent) {
        id=bizEvent.getId();
        blob = bizEvent.getBlob();
    }
}
