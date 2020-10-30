package com.deviget.minesweeper.client

import com.deviget.minesweeper.client.enums.UriEnum
import com.deviget.minesweeper.model.dto.BoardSettings
import com.deviget.minesweeper.model.dto.Game
import com.deviget.minesweeper.model.dto.User
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

	private val logger: Logger = LoggerFactory.getLogger(MinesweeperClient.javaClass)

	@Autowired
	private var restTemplate: RestTemplate = RestTemplateBuilder().build()

	private var URL: String = "http://localhost:8080/Minesweeper"

	/**
	 * Creates a new instance of the Minesweeper client with the API url as parameter.-
	 *
	 * @param url the url where the API is located
	 */
	fun MinesweeperClient(url: String): MinesweeperClient {
		logger.info("Creating client with URL: {}", url)
		URL = url
		return this
	}

	/**
	 * Sets the Minesweeper client with the API url as parameter.-
	 *
	 * @param url the url where the API is located
	 */
	fun setUrl(url: String): MinesweeperClient {
		logger.info("Setting client URL: {}", url)
		URL = url
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
		//val requestParams = mapOf("userId" to 1)

		var uri = UriComponentsBuilder.fromUriString(URL + UriEnum.USER_CREATION.uri)
			.build().toUri()
		//.buildAndExpand(requestParams).toUri()
		//uri = UriComponentsBuilder.fromUriString(URL + UriEnum.GAME_CREATION.uri).queryParam("", "").build().toUri();

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
		val requestParams = mapOf("userId" to 1)

		var uri = UriComponentsBuilder.fromUriString(URL + UriEnum.GAME_CREATION.uri)
			.buildAndExpand(requestParams).toUri()
		//uri = UriComponentsBuilder.fromUriString(URL + UriEnum.GAME_CREATION.uri).queryParam("", "").build().toUri();

		val httpEntity = HttpEntity(settings, headers)
		return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Game::class.java).body
	}
}

