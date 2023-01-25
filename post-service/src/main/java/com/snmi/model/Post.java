package com.snmi.model;

import com.snmi.model.base.BasePostEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "post")
public class Post extends BasePostEntity {

    @NotNull(message = "Post title must not be null")
    @Size(max = 128, message = "Post title must not be longer than 128 chars")
    private String title;

    @Size(max = 512, message = "Short description must not be longer than 512 chars")
    private String shortDescription;

    @NotNull(message = "Content must not be null")
    private String content;

    @Min(0)
    private int viewCount;

    @Min(0)
    private int likeCount;

    @Min(0)
    private int commentCount;

    @Column(columnDefinition = "double precision")
    private double overallRating;

    @Min(0)
    private int totalRatingPoints;

    private boolean display;

    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Likes> likes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return id != null && Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
