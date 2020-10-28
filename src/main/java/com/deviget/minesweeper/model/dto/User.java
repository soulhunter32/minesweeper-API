package com.deviget.minesweeper.model.dto;

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
public class User implements Serializable {

	private static final long serialVersionUID = -6626786360493165143L;

	private int userId;

	private String username;

	@Builder.Default
	private List<Game> gameList = new ArrayList<>();
}
