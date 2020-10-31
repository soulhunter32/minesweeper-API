package com.deviget.minesweeper.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"id", "settings", "cellList", "id"}, allowGetters = true)
@ApiModel(value = "Board", description = "The game Board")
public class Board implements Serializable {

    private static final long serialVersionUID = -8267658033441946043L;

    @ApiModelProperty(notes = "The unique identifier of the board")
    private int id;

    @ApiModelProperty(notes = "The board settings")
    private BoardSettings settings;

    @ApiModelProperty(notes = "The list of cells the board contains")
    @Builder.Default
    private List<Cell> cellList = new ArrayList<>();
}
