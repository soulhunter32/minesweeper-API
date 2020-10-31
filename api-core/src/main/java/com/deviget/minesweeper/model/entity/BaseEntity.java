package com.deviget.minesweeper.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Base entity for shared information.-
 */
@Data
public class BaseEntity {
    protected final LocalDateTime createTime = LocalDateTime.now();
    protected LocalDateTime editTime = LocalDateTime.now();
    protected LocalDateTime endTime;
}
