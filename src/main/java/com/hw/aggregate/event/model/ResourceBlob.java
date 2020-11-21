package com.hw.aggregate.event.model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

//@RedisHash("ResourceBlob")
public class ResourceBlob implements Serializable {
    @Id
    private String resourceId;
    private String blob;
    private MetaInfo metaInfo;

    public ResourceBlob(String resourceId, String blob) {
        this.resourceId = resourceId;
        this.blob = blob;
        metaInfo = new MetaInfo();
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getBlob() {
        return blob;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }

    public ResourceBlob setOwner(String owner) {
        metaInfo.owner = owner;
        return this;
    }

    public MetaInfo getMetaInfo() {
        return metaInfo;
    }
}
