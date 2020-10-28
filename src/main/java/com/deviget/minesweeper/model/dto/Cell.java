package com.deviget.minesweeper.model.dto;

import com.deviget.minesweeper.model.enums.CellRevealState;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class Cell implements Serializable {

	private static final long serialVersionUID = 4415817349508249234L;

	private int id;

	private int xCoordinate;
	private int yCoordinate;

	@Builder.Default
	private CellRevealState revealState = CellRevealState.NON_REVEALED;

	@Builder.Default
	private CellRevealState flagState = CellRevealState.NON_REVEALED;
}
