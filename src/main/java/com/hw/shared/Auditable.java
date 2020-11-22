package com.hw.shared;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Auditable implements Serializable {

    public static final String ENTITY_MODIFIED_BY = "modifiedBy";
    public static final String ENTITY_MODIFIED_AT = "modifiedAt";
    public static final String ENTITY_DELETED = "deleted";
    public static final String ENTITY_DELETED_BY = "deletedBy";
    public static final String ENTITY_DELETED_AT = "deletedAt";
    public static final String ENTITY_RESTORED_BY = "restoredBy";
    public static final String ENTITY_RESTORED_AT = "restoredAt";
    public static final String ENTITY_CREATED_BY = "createdBy";
    public static final String ENTITY_CREATED_AT = "createdAt";
    private String createdBy;
    private Date createdAt;
    private String modifiedBy;
    private Date modifiedAt;
    private Boolean deleted;
    private String deletedBy;
    private Date deletedAt;
    private String restoredBy;
    private Date restoredAt;

}
