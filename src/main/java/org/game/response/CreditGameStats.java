package org.game.response;

import lombok.Data;
import lombok.experimental.Accessors;
import org.game.entity.GameDirectory;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class CreditGameStats  implements Serializable {
    private String gameName;
    private String level;
    private BigDecimal Credit;
    private String userName;
    private boolean result;
    private String message;
}
