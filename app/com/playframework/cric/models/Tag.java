package com.playframework.cric.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import io.ebean.Model;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}