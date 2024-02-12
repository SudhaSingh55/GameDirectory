package org.game.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EnrollGamerRequest {
    private String userId;
    private String gameName;
    private String level;
}
