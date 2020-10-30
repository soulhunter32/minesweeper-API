package com.deviget.minesweeper.client.enums

/**
 * Enum for all URI path endpoints.-
 */
enum class UriEnum(val uri: String) {
	USER_CREATION("/users"),
	GAME_CREATION(USER_CREATION.uri + "/{userId}/game"),
	REVEAL_CELL("/games/{gameId}/reveal-cell"),
	FLAG_CELL("/games/{gameId}/flag-cell")
}