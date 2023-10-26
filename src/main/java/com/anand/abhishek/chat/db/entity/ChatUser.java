package com.anand.abhishek.chat.db.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Slf4j
@Data
@Entity
@Table(name = "customer", indexes = {
        @Index(name = "idx_chatuser_username", columnList = "username")
})
public class ChatUser {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username", nullable = false, unique = true, updatable = false, length = 54)
    private String username;

    @Column(name = "first_name", nullable = false, length = 54)
    private String firstName;

    @Column(name = "last_name", nullable = true, length = 54)
    private String lastName;

    @Version
    @Column(columnDefinition = "tinyint(4) default 0")
    protected Integer version;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    protected Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    protected Date updatedAt;

    @PostUpdate
    public void postUpdate() {
        log.info("Updated user : {}", this);
    }

}
