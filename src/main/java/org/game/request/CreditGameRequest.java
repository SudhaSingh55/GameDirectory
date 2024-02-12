package org.game.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class CreditGameRequest implements Serializable {
    private String userId;
    private String gameName;
    private BigDecimal credit;
}
