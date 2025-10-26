package com.playframework.cric.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import io.ebean.Model;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "matches")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer seriesId;
    @Column(name = "team_1_id")
    private Long team1Id;
    @Column(name = "team_2_id")
    private Long team2Id;
    private Long tossWinnerId;
    private Long batFirstId;
    private Integer resultTypeId;
    private Long winnerId;
    private Integer winMargin;
    private Integer winMarginTypeId;
    private Long stadiumId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;
    private Boolean isOfficial;
}