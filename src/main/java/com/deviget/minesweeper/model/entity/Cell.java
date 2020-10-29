package com.deviget.minesweeper.model.entity;

import com.deviget.minesweeper.model.enums.FlagTypeEnum;
import lombok.*;

import javax.persistence.*;

/**
 * Cell entity.-
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cell")
public class Cell extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int xCoordinate;
	private int yCoordinate;

	private boolean isRevealed = false;
	private boolean isFlagged = false;
	private boolean isMine = false;

	private int adjacentMines = 0;

	private FlagTypeEnum flagType;
}