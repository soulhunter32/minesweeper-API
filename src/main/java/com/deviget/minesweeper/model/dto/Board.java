package com.deviget.minesweeper.model.dto;

import com.deviget.minesweeper.model.enums.BoardStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Board implements Serializable {

	private static final long serialVersionUID = -8267658033441946043L;

	private int id;

	@Builder.Default
	private BoardStatus status = BoardStatus.IN_ṔROGRESS;

	private BoardSettings settings;

	@Builder.Default
	private List<Cell> cellList = new ArrayList<>();
}
