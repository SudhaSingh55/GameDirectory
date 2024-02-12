package org.game.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class GameDirectoryRequest implements Serializable {
    private String gameName;
    private String level;
    private String geography;
}
