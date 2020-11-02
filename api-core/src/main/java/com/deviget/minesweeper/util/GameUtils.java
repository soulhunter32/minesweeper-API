package com.deviget.minesweeper.util;

import com.deviget.minesweeper.model.dto.Game;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Game Utility helper.-
 */
@Log4j2
public final class GameUtils {

    /**
     * Sets the elapsed time for a finished game.-
     *
     * @param game the game to set the elapsed time
     */
    public static void setElapsedTime(final Game game) {
        final Duration elapsedTime = Duration.between(game.getCreateTime(), game.getEndTime());
        game.setElapsedTime(String.valueOf(TimeUnit.MILLISECONDS.toMinutes(elapsedTime.toMillis())).concat(" minutes and ")
                .concat(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(elapsedTime.toMillis()))));
    }
}
