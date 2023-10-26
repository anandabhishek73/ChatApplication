package com.anand.abhishek.chat.db.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Slf4j
@Data
@Entity
@Table(name = "chat_message", indexes = {
        @Index(name = "idx_chatmessage_created_at", columnList = "created_at"),
        @Index(name = "idx_chatmessage_seen_at", columnList = "seen_at"),
        @Index(name = "idx_chatmessage_from_user", columnList = "from_user"),
        @Index(name = "idx_chatmessage_to_user", columnList = "to_user")
})
public class ChatMessage {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "from_user", nullable = false, updatable = false)
    private ChatUser fromUser;

    @Column(name = "to_user", nullable = false, updatable = false)
    private ChatUser toUser;

    @Column(name = "message", nullable = false)
    private String message;

    @Version
    @Column(columnDefinition = "tinyint(4) default 0")
    protected Integer version;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    protected Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "edited_at")
    protected Date editedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "seen_at")
    protected Date seenAt;

    @PostUpdate
    public void postUpdate() {
        log.info("Chat message edited: {}", getMessage());
    }

    @PostPersist
    public void postPersist() {
        log.debug("Chat message received FROM '{}' TO '{}' = '{}'",
                getFromUser().getUsername(), getToUser().getUsername(), getMessage());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ChatMessage that = (ChatMessage) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
