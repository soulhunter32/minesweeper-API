package com.deviget.minesweeper

import com.deviget.minesweeper.client.MinesweeperClient
import com.deviget.minesweeper.model.dto.BoardSettings
import com.deviget.minesweeper.model.dto.Cell
import com.deviget.minesweeper.model.enums.FlagTypeEnum
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Minesweeper API Kotlin Client.-
 */
@SpringBootApplication
class MinesweeperClientApplication

fun main(args: Array<String>) {
	runApplication<MinesweeperClientApplication>(*args)

	//val createdUser = MinesweeperClient.createUser(User.builder().username("Pepe").build())
	val newGame = MinesweeperClient.createNewGame(
		1,
		BoardSettings.builder().width(10).height(10).totalMines(10).build()
	)

	MinesweeperClient.revealCell(newGame?.id, Cell.builder().xCoordinate(1).yCoordinate(1).build())

	MinesweeperClient.flagCell(
		newGame?.id,
		Cell.builder().xCoordinate(2).yCoordinate(2).build(), FlagTypeEnum.RED_FLAG
	)
}