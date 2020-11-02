package com.deviget.minesweeper.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
@ApiModel(value = "User", description = "The user")
public class User implements Serializable {

    private static final long serialVersionUID = -6626786360493165143L;

    @ApiModelProperty(notes = "The unique identifier of the user")
    private int id;

    @ApiModelProperty(notes = "The username/nick")
    private String username;

    @ApiModelProperty(notes = "The list of game for the user. It can contain games in different states")
    @JsonIgnore
    @Builder.Default
    private List<Game> gameList = new ArrayList<>();
}
