package com.hw.aggregate.event.command;

import lombok.Data;

@Data
public class AdminUpdateBizEventCommand {
    private Integer version;
    private String events;
}
