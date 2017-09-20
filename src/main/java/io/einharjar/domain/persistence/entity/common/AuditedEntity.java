package io.einharjar.domain.persistence.entity.common;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditedEntity extends BaseEntity{
    @Column(name = "created_date", updatable = false)
    @CreatedDate
    @Setter(value = AccessLevel.NONE)
    private long createdDate;
    @Column(name = "modified_date")
    @LastModifiedDate
    @Setter(value = AccessLevel.NONE)
    private long modifiedDate;
    @Version
    @Setter(value = AccessLevel.NONE)
    private Long version;
}
