package com.hw.aggregate.event.model;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class MetaInfo implements IMetaInfo {
    public String createAt;
    public String lastUpdateAt;
    public ResourceStatusEnum status;
    public String owner;

    public MetaInfo() {
        init();
    }

    @Override
    public void init() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        createAt = now.toString();
        lastUpdateAt = now.toString();
        status = ResourceStatusEnum.New;
    }

    @Override
    public void update() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        lastUpdateAt = now.toString();
        status = ResourceStatusEnum.New;
    }
}
