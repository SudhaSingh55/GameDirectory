package org.game.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ApiAddResponse implements Serializable {
    private boolean result;
    private String message;

    public ApiAddResponse(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
}
