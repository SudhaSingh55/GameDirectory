package org.game.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.game.request.GameRequest;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "GAME")
@Data
@RequiredArgsConstructor
public class Game implements Serializable {
    @Id
    @Column(name = "GAME_ID")
    private int gameId;
    @Column(name = "NAME")
    private String gameName;
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy="game")
    private Set<GameDirectory> gameLevel;

    public Game(GameRequest request){
        this.gameId = request.getGameId();
        this.gameName = request.getGameName();
        this.description = request.getDescription();
    }
}
