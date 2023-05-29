package com.playframework.cric.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import io.ebean.Model;

@Data
@NoArgsConstructor
@Entity
@Table(name = "team_types")
public class TeamType extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}