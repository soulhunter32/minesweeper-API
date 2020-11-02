package com.deviget.minesweeper.controller.handler;

import com.deviget.minesweeper.exception.GameOverException;
import com.deviget.minesweeper.exception.generic.MinesweeperConflictException;
import com.deviget.minesweeper.exception.generic.MinesweeperNotFoundException;
import com.deviget.minesweeper.exception.generic.MinesweeperServerErrorException;
import com.deviget.minesweeper.model.MinesweeperApiErrorDetail;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.deviget.minesweeper.util.ResponseErrorBuilder.ResponseEntityBuilder.build;

@ControllerAdvice
@RestController
@Log4j2
@ApiIgnore(value = "true")
public class ApiResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Exception handler for {@link MinesweeperNotFoundException}
     *
     * @param ex the exception
     * @return a {@link ResponseEntity} with a {@link MinesweeperApiErrorDetail} populated object
     */
    @ExceptionHandler(MinesweeperNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(final MinesweeperNotFoundException ex) {
        return build(MinesweeperApiErrorDetail.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage()).errors(Collections.singletonList(ex.getMessage())).build());
    }

    /**
     * Exception handler for {@link MinesweeperConflictException}
     *
     * @param ex the exception
     * @return a {@link ResponseEntity} with a {@link MinesweeperApiErrorDetail} populated object
     */
    @ExceptionHandler(MinesweeperConflictException.class)
    public ResponseEntity<Object> handleConflictException(final MinesweeperConflictException ex) {
        return build(MinesweeperApiErrorDetail.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage()).errors(Collections.singletonList(ex.getMessage())).build());
    }

    /**
     * Exception handler for {@link MinesweeperServerErrorException}
     *
     * @param ex the exception
     * @return a {@link ResponseEntity} with a {@link MinesweeperApiErrorDetail} populated object
     */
    @ExceptionHandler(MinesweeperServerErrorException.class)
    public ResponseEntity<Object> handleServerErrorException(final MinesweeperServerErrorException ex) {
        return build(MinesweeperApiErrorDetail.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage()).errors(Collections.singletonList(ex.getMessage())).build());
    }

    /**
     * Exception handler for {@link GameOverException}
     *
     * @param ex the exception
     * @return a {@link ResponseEntity} with a {@link MinesweeperApiErrorDetail} populated object
     */
    @ExceptionHandler(GameOverException.class)
    public ResponseEntity<Object> handleGameOverErrorException(final GameOverException ex) {
        return build(MinesweeperApiErrorDetail.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.NO_CONTENT)
                .message(ex.getMessage()).errors(Collections.singletonList(ex.getMessage())).build());
    }


    /**
     * Exception handler for {@link org.springframework.web.servlet.NoHandlerFoundException
     *
     * @param ex the exception
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
                                                                   final HttpHeaders headers,
                                                                   final HttpStatus status,
                                                                   final WebRequest request) {
        return build(MinesweeperApiErrorDetail.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message("Method not found")
                .errors(Collections.singletonList(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL())))
                .build());
    }

    /**
     * Exception handler default exceptions.-
     *
     * @param ex the exception
     * @return a {@link ResponseEntity} with a {@link MinesweeperApiErrorDetail} populated object
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleDefaultException(final NoHandlerFoundException ex) {
        return build(MinesweeperApiErrorDetail.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("An unknown error occurred")
                .errors(Collections.singletonList(ex.getLocalizedMessage())).build());
    }
}