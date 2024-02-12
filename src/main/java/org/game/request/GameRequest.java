package org.game.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class GameRequest implements Serializable {
    private int gameId;
    private String gameName;
    private String description;
}
