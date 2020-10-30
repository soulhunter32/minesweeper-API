package com.deviget.minesweeper.model.entity;

import com.deviget.minesweeper.model.dto.BoardSettings;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Board entity.-
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board")
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Embedded
	@Cascade(CascadeType.ALL)
	private BoardSettings settings;

	@Builder.Default
	@OneToMany
	@Cascade(CascadeType.ALL)
	private List<Cell> cellList = new ArrayList<>();
}
