package com.deviget.minesweeper.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

/**
 * Base entity for shared information.-
 */
public class BaseEntity {
	@JsonIgnore
	private final LocalDateTime createTime = LocalDateTime.now();
	@JsonIgnore
	private final LocalDateTime editTime = LocalDateTime.now();
}
