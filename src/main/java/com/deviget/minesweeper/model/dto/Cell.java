package com.deviget.minesweeper.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cell implements Serializable {

	private static final long serialVersionUID = 4415817349508249234L;

	private int id;

	private int xCoordinate;
	private int yCoordinate;

	private boolean isRevealed = false;
	private boolean isFlagged = false;
	private boolean isMine = false;

	private int adjacentMines = 0;
}
