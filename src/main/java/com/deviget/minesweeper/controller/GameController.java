package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.service.IGameService;
import com.deviget.minesweeper.service.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller handles games endpoints.-
 */
@RestController
@RequestMapping("/games")
@Log4j2
public class GameController {

	@Autowired
	private IGameService gameService;

	@Autowired
	private IUserService userService;

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> revealCell(@RequestParam("xCoordinate") int xCoordinate,
	                                         @RequestParam("yCoordinate") int yCoordinate) {
		log.info("revealCell:: Entering Reveal Cell endpoint with coordinates: [x,y] -> [{}{}]...", xCoordinate,
				yCoordinate);

		return ResponseEntity.ok("Checked ok!");

	}
}
