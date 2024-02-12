package org.game.service;

import org.game.request.*;
import org.game.response.ApiAddResponse;
import org.game.response.CreditGameStats;
import org.game.response.GameDirectoryResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface GameService {
    public ApiAddResponse createUser(UserRequest request);
    public ApiAddResponse createGame(GameRequest gameRequest);
    public ApiAddResponse enrollUserGame(EnrollGamerRequest request);
    public GameDirectoryResponse searchGame(GameDirectoryRequest request);
    public ApiAddResponse creditUserGame(CreditGameRequest request);
    public List<CreditGameStats> searchMaxCredit(String gameName);
}
