package org.game.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "USER_GAME_DIRECTORY")
@Data
@RequiredArgsConstructor
public class GameDirectory implements Serializable {
    @Id
    @Column(name = "LEVEl_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long levelId;
    @Column(name = "USER_ID")
    private int userId;
    @Column(name = "GAME_ID")
    private int gameId;
    @Column(name = "GAME_NAME")
    private String gameName;
    @Column(name = "LEVEL")
    private String level;
    @Column(name = "CREDIT")
    private BigDecimal credit;
    @ManyToOne
    @JoinColumn(name="USER_ID", nullable=false, insertable = false, updatable = false)
    private Users users;
    @ManyToOne
    @JoinColumn(name="GAME_ID", nullable=false, insertable = false, updatable = false)
    private Game game;
}
