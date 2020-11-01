package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.exception.*;
import com.deviget.minesweeper.model.dto.*;
import com.deviget.minesweeper.model.enums.FlagTypeEnum;
import com.deviget.minesweeper.model.enums.GameStatusEnum;
import com.deviget.minesweeper.repository.IGameRepository;
import com.deviget.minesweeper.service.IBoardService;
import com.deviget.minesweeper.service.ICellService;
import com.deviget.minesweeper.service.IGameService;
import com.deviget.minesweeper.service.IUserService;
import com.deviget.minesweeper.util.CellUtils;
import com.deviget.minesweeper.util.GameUtils;
import com.deviget.minesweeper.util.ModelMapperUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service in charge of all game related functionalities.-
 */
@Log4j2
@Service
public class GameService implements IGameService {

    @Autowired
    private ModelMapperUtils modelMapperUtils;
    @Autowired
    private IGameRepository gameRepository;
    @Autowired
    private IBoardService boardService;
    @Autowired
    private ICellService cellService;
    @Autowired
    private IUserService userService;

    /**
     * Creates a new game for the current userId with the current settings.-
     *
     * @param userId        the userId to create the game for
     * @param boardSettings the settings for the new board
     * @return the create board
     * @throws InvalidBoardSettingsException if the {@link BoardService} are not valid
     */
    @Override
    public Game createGame(final int userId, final BoardSettings boardSettings) throws InvalidBoardSettingsException, UserNotFoundException {

        final User user = userService.findById(userId);

        final Game game = Game.builder()
                .user(user)
                .board(Board.builder().settings(boardSettings).build())
                .build();
        final Board board = boardService.startNewBoard(boardSettings);

        game.setBoard(board);

        final com.deviget.minesweeper.model.entity.Game newGame = modelMapperUtils.map(game,
                com.deviget.minesweeper.model.entity.Game.class);

        newGame.setUser(modelMapperUtils.map(userService.findById(user.getId()),
                com.deviget.minesweeper.model.entity.User.class));

        return modelMapperUtils.map(gameRepository.save(newGame), Game.class);
    }

    /**
     * Finds a game by its id.-
     *
     * @param gameId the game id too look for
     * @return the game found for the id
     * @throws GameNotFoundException if the game was not found
     */
    @Override
    public Game findById(final Integer gameId) throws GameNotFoundException, InvalidGameStatusException {
        final com.deviget.minesweeper.model.entity.Game gameFound =
                gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        if (gameFound.isOver()) {
            throw new InvalidGameStatusException(gameFound.getStatus());
        }
        return modelMapperUtils.map(gameFound, Game.class);
    }

    /**
     * Flags a a Cell for the current game's board.-
     *
     * @param gameId   the current game
     * @param flagCell the cell to flag
     * @param flagType the type of flag to assign
     * @return the flagged cell
     */
    @Override
    public Cell flagCell(final int gameId, final Cell flagCell, final FlagTypeEnum flagType) throws CellNotFoundException,
            ExistingCellException, CellFlaggedException, InvalidGameStatusException, GameNotFoundException {
        return cellService.saveCell(CellUtils.flagCell(findById(gameId).getBoard(), flagCell, flagType));
    }

    /**
     * Reveals the current cell for the current game.-
     *
     * @param game       the game where the board' s cell is located
     * @param revealCell the cell to reveal
     * @return the revealed cell
     * @throws CellNotFoundException
     * @throws ExistingCellException
     */
    @Override
    public Cell revealCell(final Game game, final Cell revealCell) throws CellNotFoundException, ExistingCellException,
            GameOverException {
        return cellService.saveCell(CellUtils.revealCell(game, revealCell));
    }

    /**
     * Ends the current game in a Failed state.-
     *
     * @param game the game to end
     */
    @Override
    public void endGameAsFailed(final Game game) {
        game.setEndTime(LocalDateTime.now());
        GameUtils.setElapsedTime(game);

        final com.deviget.minesweeper.model.entity.Game gameToEnd = gameRepository.findById(game.getId()).get();
        gameToEnd.setElapsedTime(game.getElapsedTime());
        gameToEnd.setStatus(GameStatusEnum.FAILED);
        gameToEnd.setEndTime(LocalDateTime.now());

        gameRepository.saveAndFlush(gameToEnd);
    }

    /**
     * Saves the current game.-
     *
     * @param game the game to save
     * @return the current saved game
     */
    @Override
    public Game saveGame(final Game game) {
        game.setEditTime(LocalDateTime.now());
        return modelMapperUtils.map(
                gameRepository.save(modelMapperUtils.map(game, com.deviget.minesweeper.model.entity.Game.class)),
                Game.class);
    }
}
