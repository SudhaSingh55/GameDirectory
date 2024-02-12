package org.game.response;

import lombok.Data;
import lombok.experimental.Accessors;
import org.game.entity.GameDirectory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class GameDirectoryResponse implements Serializable {
    List<GameDirectory> gameDirectories = new ArrayList<>();
    private boolean result;
    private String message;
}
