package io.einharjar.domain.persistence.entity.common;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditedEntity extends BaseEntity{
    @Column(name = "created_date", updatable = false)
    @CreatedDate
    private long createdDate;
    @Column(name = "modified_date")
    @LastModifiedDate
    private long modifiedDate;
    @Version
    private Long version;
}
