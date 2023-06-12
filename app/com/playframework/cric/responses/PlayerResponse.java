package com.playframework.cric.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.Data;

import com.playframework.cric.models.Player;

@Data
@NoArgsConstructor
public class PlayerResponse {
    private Long id;
    private String name;
    private String image;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;
    private CountryResponse country;

    public PlayerResponse(Player player, CountryResponse country) {
        this.id = player.getId();
        this.name = player.getName();
        this.dateOfBirth = player.getDateOfBirth();
        this.image = player.getImage();
        this.country = country;
    }
}