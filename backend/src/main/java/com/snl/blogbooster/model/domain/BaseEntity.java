package com.snl.blogbooster.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Setter
@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {

    @LastModifiedBy
    private Timestamp auditAt;
    private Timestamp createdAt;
    private String auditId;
    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }

}
