package com.hw.shared.cache;

import com.google.common.base.Objects;
import lombok.Data;

import javax.annotation.Nullable;
import java.io.Serializable;

@Data
public class CacheCriteria implements Serializable {
    public enum RoleEnum {
        ROOT,
        ADMIN,
        USER,
        APP,
        PUBLIC
    }

    RoleEnum role;
    @Nullable
    String query;
    String page;
    String config;

    public CacheCriteria(RoleEnum role, String query, String page, String config) {
        this.role = role;
        this.query = query;
        this.page = page;
        this.config = config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheCriteria that = (CacheCriteria) o;
        return role == that.role &&
                Objects.equal(query, that.query) &&
                Objects.equal(page, that.page) &&
                Objects.equal(config, that.config);
    }

    @Override
    public int hashCode() {
        //use role.name to make sure hash not change after application restart
        return Objects.hashCode(role.name(), query, page, config);
    }
}
