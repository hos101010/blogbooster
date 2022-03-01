package com.snl.blogbooster.model.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass/*상속한 경우에 BaseEntity필드를 컬럼으로 인식한다.*/
@EntityListeners(AuditingEntityListener.class)/*Auditing기능을 포함시킨다.*/
public abstract class BaseEntity {

    @LastModifiedDate/*마지막으로 수정된 시간 자동저장*/
    private LocalDateTime auditAt;

    @CreatedDate/*생성된 시간을 자동저장*/
    private LocalDateTime createdAt;
    private String auditId;
    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }

}
