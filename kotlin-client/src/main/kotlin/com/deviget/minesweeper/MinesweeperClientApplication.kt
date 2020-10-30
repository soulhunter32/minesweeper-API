package com.deviget.minesweeper

import com.deviget.minesweeper.client.MinesweeperClient
import com.deviget.minesweeper.model.dto.BoardSettings
import com.deviget.minesweeper.model.dto.User
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Minesweeper API Kotlin Client.-
 */
@SpringBootApplication
class MinesweeperClientApplication

fun main(args: Array<String>) {
	runApplication<MinesweeperClientApplication>(*args)

	val createdUser = MinesweeperClient.createUser(User.builder().username("Pepe").build())
	MinesweeperClient.createNewGame(
		createdUser?.userId,
		BoardSettings.builder().width(10).height(10).totalMines(30).build()
	)
}