package com.deviget.minesweeper.client.model

import org.springframework.http.HttpStatus

class MinesweeperApiErrorDetail(
	val timestamp: String,
	val status: HttpStatus,
	val message: String,
	val errors: List<String>
)