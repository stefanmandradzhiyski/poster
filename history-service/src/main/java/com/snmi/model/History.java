package com.snmi.model;

import com.snmi.enums.HistoryType;
import com.snmi.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "history")
public class History extends BaseEntity {

    @NotNull(message = "Post id must not be null")
    @Size(min = 36, max = 36, message = "Post id must be exactly 36 chars")
    @Column(name = "post_id")
    private String postId;

    @NotNull(message = "Post title must not be null")
    @Size(max = 128, message = "Post title must not be longer than 128 chars")
    private String postTitle;

    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 chars")
    private String username;

    @NotNull(message = "Interaction type must not be null")
    @Enumerated(value = EnumType.STRING)
    private HistoryType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        History that = (History) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}