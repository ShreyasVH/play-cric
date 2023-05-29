package com.playframework.cric.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import io.ebean.Model;

@Data
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class Team extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long countryId;
    private Integer typeId;
}