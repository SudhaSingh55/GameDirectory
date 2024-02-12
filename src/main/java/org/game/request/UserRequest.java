package org.game.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserRequest implements Serializable {
    private int userId;
    private String userName;
    private String nickName;
    private String geography;
    private String phoneNumber;
    private String email;

}
