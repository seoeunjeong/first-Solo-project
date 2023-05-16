package com.soloproject.community;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class Date {

    @CreatedDate
    @Column(name="created_at",updatable = false)
    private LocalDateTime createdAt=LocalDateTime.now();


    @LastModifiedDate
    @Column(name="modified_at")
    private LocalDateTime modifiedAt=LocalDateTime.now();

}
