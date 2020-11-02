package com.deviget.minesweeper.util;

import com.deviget.minesweeper.model.MinesweeperApiErrorDetail;
import org.springframework.http.ResponseEntity;

/**
 * Default builder for global exception creation.-
 */
public class ResponseErrorBuilder {
    public static class ResponseEntityBuilder {
        public static ResponseEntity<Object> build(final MinesweeperApiErrorDetail apiError) {
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
}
