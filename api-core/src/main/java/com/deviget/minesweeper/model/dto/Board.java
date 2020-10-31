package com.deviget.minesweeper.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Board implements Serializable {

    private static final long serialVersionUID = -8267658033441946043L;
    private final LocalDateTime createTime = LocalDateTime.now();
    private final LocalDateTime editTime = LocalDateTime.now();
    private int id;
    private BoardSettings settings;
    @Builder.Default
    private List<Cell> cellList = new ArrayList<>();
}
