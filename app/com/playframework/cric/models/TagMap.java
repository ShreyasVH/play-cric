package com.playframework.cric.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import io.ebean.Model;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags_map")
public class TagMap extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String entityType;
    private Integer entityId;
    private Integer tagId;
}