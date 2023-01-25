package com.snmi.model;

import com.snmi.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
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
@Table(name = "user", schema = "public")
public class User extends BaseEntity {

    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 chars")
    private String username;

    @NotNull(message = "Password must not be null")
    private String password;

    @Min(0)
    private int visiblePostCount;

    @Min(0)
    private int totalPostCount;

    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}