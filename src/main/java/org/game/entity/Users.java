package org.game.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.game.request.UserRequest;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
@RequiredArgsConstructor
public class Users implements Serializable {
    @Id
    @Column(name = "USER_ID")
    private int userId;
    @Column(name = "NAME")
    private String userName;
    @Column(name = "NICKNAME")
    private String nickName;
    @Column(name = "GEOGRAPHY")
    private String geography;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "EMAIL")
    private String email;
    @OneToMany(mappedBy="users")
    private Set<GameDirectory> gameDirectory;

    public Users(UserRequest request){
        this.userId = request.getUserId();
        this.userName = request.getUserName();
        this.nickName = request.getNickName();
        this.geography = request.getGeography();
        this.phoneNumber = request.getPhoneNumber();
        this.email = request.getEmail();
    }


}
