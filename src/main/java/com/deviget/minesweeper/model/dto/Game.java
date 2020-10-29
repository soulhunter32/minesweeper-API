package com.deviget.minesweeper.model.dto;

import com.deviget.minesweeper.model.enums.GameStatusEnum;
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
public class Game implements Serializable {

	private static final long serialVersionUID = 9123734699152341971L;

	private int id;

	@Builder.Default
	private Board board = Board.builder().build();

	@Builder.Default
	private GameStatusEnum status = GameStatusEnum.IN_ṔROGRESS;

	private User user;
}
