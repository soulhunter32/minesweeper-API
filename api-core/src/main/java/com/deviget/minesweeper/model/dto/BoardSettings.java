package com.deviget.minesweeper.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Board Settings", description = "The board settings for the game")
public class BoardSettings {

    @ApiModelProperty(notes = "The width of the board, translating to the amount of cells in the 'X' direction")
    private int width;

    @ApiModelProperty(notes = "The height of the board, translating to the amount of cells in the 'Y' direction")
    private int height;

    @ApiModelProperty(notes = "The amount of mines in the board")
    private int totalMines;
}
