package com.deviget.minesweeper.model.dto;

import com.deviget.minesweeper.model.enums.FlagTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"revealed", "flagged", "mine"}, allowGetters = true)
@ApiModel(value = "Cell", description = "A cell of the board")
public class Cell implements Serializable {

    private static final long serialVersionUID = 4415817349508249234L;

    @ApiModelProperty(notes = "The unique identifier of the cell")
    private int id;

    @ApiModelProperty(notes = "The 'X' position coordinate of the cell in the board")
    private int xCoordinate;

    @ApiModelProperty(notes = "The 'Y' position coordinate of the cell in the board")
    private int yCoordinate;

    @ApiModelProperty(notes = "This value defines if the cell has been revealed in the board")
    private boolean isRevealed = false;

    @ApiModelProperty(notes = "This value defines if the cell has been flagged in the board")
    private boolean isFlagged = false;

    @ApiModelProperty(notes = "This value defines if the cell contains a mine")
    private boolean isMine = false;

    @ApiModelProperty(notes = "Amount of mines surrounding the cell in all directions")
    private int adjacentMines = 0;

    @ApiModelProperty(notes = "The type of flag applied to the cell", example = "RED_FLAG")
    private FlagTypeEnum flagType;

    /**
     * Flags the cell with the corresponding flag.-
     *
     * @param flagType the flag type
     */
    public void setFlagType(final FlagTypeEnum flagType) {
        this.flagType = flagType;
        setFlagged(!Objects.isNull(flagType));
    }

}
