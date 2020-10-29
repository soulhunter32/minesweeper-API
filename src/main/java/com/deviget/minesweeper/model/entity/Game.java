package com.deviget.minesweeper.model.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

/**
 * Game entity.-
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game")
public class Game extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Builder.Default
	@OneToOne
	@Cascade(CascadeType.ALL)
	private Board board = Board.builder().build();

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
}
