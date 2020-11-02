package com.deviget.minesweeper.client.util

import com.deviget.minesweeper.client.model.MinesweeperApiErrorDetail
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.web.client.HttpServerErrorException

/**
 * Utility class for client operations.-
 */
class ClientUtils {

	/**
	 * Extracts the error message from API exception.-
	 */
	companion object {
		fun extractErrorMessage(e: HttpServerErrorException): String? {
			return jacksonObjectMapper().readValue<MinesweeperApiErrorDetail>(
				e.message?.subSequence(
					7,
					e.message.toString().length - 1
				).toString()
			).message
		}
	}
}