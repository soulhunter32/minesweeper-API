package com.deviget.minesweeper.model.entity;

import com.deviget.minesweeper.model.enums.CellRevealState;
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

	@Builder.Default
	private CellRevealState revealState = CellRevealState.NON_REVEALED;

	@Builder.Default
	private CellRevealState flagState = CellRevealState.NON_REVEALED;
}