package com.deviget.minesweeper.model.entity;

import com.deviget.minesweeper.model.enums.GameStatusEnum;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collections;

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

    private int score = 0;

    @Builder.Default
    @OneToOne
    @Cascade(CascadeType.ALL)
    private Board board = Board.builder().build();

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Builder.Default
    private GameStatusEnum status = GameStatusEnum.IN_ṔROGRESS;

    private String elapsedTime;

    /**
     * Checks if a board is over, whether is completed or failed.-
     *
     * @return true if its completed or failed, false otherwise
     */
    public boolean isOver() {
        return CollectionUtils.containsAny(Collections.singletonList(getStatus()),
                Arrays.asList(GameStatusEnum.COMPLETED, GameStatusEnum.FAILED));
    }
}
