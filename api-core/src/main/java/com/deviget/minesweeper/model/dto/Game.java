package com.deviget.minesweeper.model.dto;

import com.deviget.minesweeper.model.enums.GameStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Game", description = "A Minesweeper game")
public class Game implements Serializable {

    private static final long serialVersionUID = 9123734699152341971L;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(notes = "Create time of the game", hidden = true)
    private final LocalDateTime createTime = LocalDateTime.now();
    @ApiModelProperty(notes = "The score of the game. Each cell sums in the score")
    private int score = 0;
    @ApiModelProperty(notes = "The unique identifier of the game")
    private int id;
    @ApiModelProperty(notes = "The user of the game")
    private User user;
    @JsonIgnore
    @ApiModelProperty(notes = "Start time of the game")
    private LocalDateTime editTime = LocalDateTime.now();

    @ApiModelProperty(notes = "Finish time of the game")
    private LocalDateTime endTime;

    @ApiModelProperty(notes = "The elapsed time from the game starts till it finishes, due complete or game over")
    private String elapsedTime;

    @ApiModelProperty(notes = "The board for the game")
    @Builder.Default
    private Board board = Board.builder().build();

    @ApiModelProperty(notes = "The game status", example = "IN_PROGRESS")
    @Builder.Default
    private GameStatusEnum status = GameStatusEnum.IN_PROGRESS;

}
