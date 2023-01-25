package com.snmi.model;

import com.snmi.model.base.BaseInteractionEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "likes")
public class Likes extends BaseInteractionEntity {

    

}
