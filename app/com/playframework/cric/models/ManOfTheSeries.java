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
@Table(name = "man_of_the_series")
public class ManOfTheSeries extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer seriesId;
    private Long playerId;
}

