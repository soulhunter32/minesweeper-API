package com.deviget.minesweeper.client

import com.deviget.minesweeper.client.enums.UriEnum
import com.deviget.minesweeper.model.dto.BoardSettings
import com.deviget.minesweeper.model.dto.Cell
import com.deviget.minesweeper.model.dto.Game
import com.deviget.minesweeper.model.dto.User
import com.deviget.minesweeper.model.enums.FlagTypeEnum
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

/**
 * Minesweeper API Kotlin Client.-
 */
@Component
object MinesweeperClient {

	private val FLAG_TYPE_QUERY_PARAM: String = "flagType"

	private val apiSchema: String? = "http"
	private val apiContext: String? = "minesweeper"
	private val apiPort: String? = "8080"

	private val logger: Logger = LoggerFactory.getLogger(MinesweeperClient.javaClass)

	@Autowired
	private var restTemplate: RestTemplate = RestTemplateBuilder().build()

	private var HOST: String = "localhost"

	/**
	 * Creates a new instance of the Minesweeper client with the API url as parameter.-
	 *
	 * @param host the url where the API is located
	 */
	fun MinesweeperClient(host: String): MinesweeperClient {
		logger.info("Creating client for host: {}", host)
		HOST = host
		return this
	}

	/**
	 * Sets the Minesweeper client with the API url as parameter.-
	 *
	 * @param host the url where the API is located
	 */
	fun setUrl(host: String): MinesweeperClient {
		logger.info("Setting client host: {}", host)
		HOST = host
		return this
	}

	/**
	 * Creates a new user.-
	 *
	 * @param user the new user to create
	 */
	fun createUser(user: User): User? {
		logger.info("createUser :: Creating user with information: {}", user)

		val headers: HttpHeaders = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_JSON

		val uri = UriComponentsBuilder.newInstance().scheme(apiSchema).host(HOST).port(apiPort)
			.path(apiContext + UriEnum.USER_CREATION.uri).build().toUri()
		val httpEntity = HttpEntity(user, headers)
		return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, User::class.java).body
	}

	/**
	 * Creates a new game.-
	 *
	 * @param userId the user to assign the new game
	 * @param settings the settings for the new game
	 */
	fun createNewGame(userId: Int?, settings: BoardSettings?): Game? {
		logger.info("createNewGame :: Creating new game for user {} and settings: {}", userId, settings)

		val headers: HttpHeaders = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_JSON

		val uri = UriComponentsBuilder.newInstance().scheme(apiSchema).host(HOST).port(apiPort)
			.path(apiContext + UriEnum.GAME_CREATION.uri)
			.buildAndExpand(userId)
			.toUri()
		val httpEntity = HttpEntity(settings, headers)
		return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Game::class.java).body
	}


	/**
	 * Reveals a cell in the current game.-
	 *
	 * @param gameId the game id of the reveal cell's game
	 * @param cell the cell to reveal
	 */
	fun revealCell(gameId: Int?, cell: Cell?): Game? {
		logger.info("revealCell :: Revealing sell for game {}: {}", gameId, cell)

		val headers: HttpHeaders = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_JSON

		val uri = UriComponentsBuilder.newInstance().scheme(apiSchema).host(HOST).port(apiPort)
			.path(apiContext + UriEnum.REVEAL_CELL.uri)
			.buildAndExpand(gameId)
			.toUri()
		val httpEntity = HttpEntity(cell, headers)
		return restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, Game::class.java).body
	}

	/**
	 * Flags a cell as a red flag or question mark in the current game.-
	 *
	 * @param gameId the game id of the reveal cell's game
	 * @param flagType the flag type to set in the cell
	 * @param cell the cell to reveal
	 */
	fun flagCell(gameId: Int?, cell: Cell?, flagType: FlagTypeEnum?): Game? {
		logger.info("revealCell :: Flagging sell with type {} for game {}: {}", flagType, gameId, cell)

		val headers: HttpHeaders = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_JSON

		val uri = UriComponentsBuilder.newInstance().scheme(apiSchema).host(HOST).port(apiPort)
			.path(apiContext + UriEnum.FLAG_CELL.uri)
			.query("$FLAG_TYPE_QUERY_PARAM={$FLAG_TYPE_QUERY_PARAM}").buildAndExpand(gameId, flagType)
			.toUri()
		val httpEntity = HttpEntity(cell, headers)
		return restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, Game::class.java).body
	}
}