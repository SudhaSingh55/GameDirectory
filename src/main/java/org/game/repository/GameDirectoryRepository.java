package org.game.repository;

import org.game.entity.Game;
import org.game.entity.GameDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameDirectoryRepository extends JpaRepository<GameDirectory, String> {

    @Query("select gameDirectory FROM GameDirectory gameDirectory where gameDirectory.level = :level")
    List<GameDirectory> findByLevel(@Param("level") String level);

    @Query("select gameDirectory FROM GameDirectory gameDirectory where gameDirectory.gameName = :game")
    List<GameDirectory> findByGame(@Param("game") String game);

    @Query("select gameDirectory FROM GameDirectory gameDirectory where gameDirectory.level = :level and gameDirectory.gameName = :game")
    List<GameDirectory> findByLevelAndGame(@Param("level") String level, @Param("game") String game);
    @Query("select gameDirectory FROM GameDirectory gameDirectory where gameDirectory.userId = :user and gameDirectory.gameName = :game")
    GameDirectory findByUserAndGame(@Param("user") String user, @Param("game") String game);

}
