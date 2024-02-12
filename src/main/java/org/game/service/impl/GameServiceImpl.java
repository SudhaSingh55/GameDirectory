package org.game.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.game.entity.Game;
import org.game.entity.GameDirectory;
import org.game.entity.Users;
import org.game.exception.ApiException;
import org.game.repository.GameDirectoryRepository;
import org.game.repository.GameRepository;
import org.game.repository.UsersRepository;
import org.game.request.*;
import org.game.response.ApiAddResponse;
import org.game.response.CreditGameStats;
import org.game.response.GameDirectoryResponse;
import org.game.service.GameService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.annotation.Immutable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GameServiceImpl implements GameService {

    private final UsersRepository usersRepository;
    private final GameRepository gameRepository;
    private final GameDirectoryRepository gameDirectoryRepository;

    public static final List<String> LEVELS =  Arrays.asList("NOOB", "PRO", "INVINCIBLE");
    @Override
    public ApiAddResponse createUser(UserRequest request) {
        Users user = new Users(request);
        try {
            usersRepository.save(user);
            return new ApiAddResponse().setMessage("User Created Successfully")
                    .setResult(true);
        }catch(Exception e){
            log.info("error:"+e.getMessage());
            throw new ApiException("User not Created Successfully");
        }
    }

    @Override
    public ApiAddResponse createGame(GameRequest gameRequest) {
        Game game = new Game(gameRequest);
        try {
            gameRepository.save(game);
            return new ApiAddResponse().setMessage("Game Created Successfully")
                    .setResult(true);
        }catch(Exception e){
            log.info("error:"+e.getMessage());
            throw new ApiException("Game not Created Successfully");
        }
    }

    @Override
    public ApiAddResponse enrollUserGame(EnrollGamerRequest request) {
        // Validate user is valid
        Users user = usersRepository.findById(request.getUserId()).orElse(null);
        if(user==null || user.getUserName()==null){
            throw new ApiException("User is not valid");
        }

        //validate game is valid
        List<Game> gamesList = gameRepository.findAll();
        Game game = gamesList.stream().filter(item -> item.getGameName().equalsIgnoreCase(request.getGameName()))
                .findFirst().orElse(null);
        if(game==null ){
            throw new ApiException("Game is not valid");
        }

        //validate level is valid
        if(!LEVELS.contains(request.getLevel().toUpperCase())){
            throw new ApiException("Level Should be one of (noob, pro, invincible) List");
        }

        GameDirectory userDetailInDirectory = gameDirectoryRepository.findByUserAndGame(request.getUserId(),request.getGameName());
        if(userDetailInDirectory!=null){
            throw new ApiException("User is already enrolled for game");
        }

        GameDirectory gameDirectory = new GameDirectory();
        gameDirectory.setUserId(user.getUserId());
        gameDirectory.setGameId(game.getGameId());
        gameDirectory.setGameName(game.getGameName());
        gameDirectory.setLevel(request.getLevel());

        try {
            gameDirectoryRepository.save(gameDirectory);
            return new ApiAddResponse().setMessage("User enrolled into game Successfully")
                    .setResult(true);
        }catch(Exception e){
            log.info("error:"+e.getMessage());
            throw new ApiException("User enrollment Failed!!!");
        }



    }

    @Override
    public GameDirectoryResponse searchGame(GameDirectoryRequest request) {
        List<GameDirectory> gameResult = null;
        if(request.getGameName()!=null && request.getLevel()==null){
            gameResult = gameDirectoryRepository.findByGame(request.getGameName());
        }else if(request.getGameName()==null && request.getLevel()!=null){
            gameResult = gameDirectoryRepository.findByLevel(request.getLevel());
        }else if(request.getGameName()!=null && request.getLevel()!=null){
            gameResult = gameDirectoryRepository.findByLevelAndGame(request.getLevel(), request.getGameName());
        }else{
            throw new ApiException("Search based on either level / game name is required!!!");
        }

        if(!CollectionUtils.isEmpty(gameResult) && request.getGeography()!=null){
            gameResult = gameResult.stream()
                    .filter(gameDir -> gameDir.getUsers().getGeography().equalsIgnoreCase(request.getGeography()))
                    .collect(Collectors.toList());

        }

        if(CollectionUtils.isEmpty(gameResult))
            throw new ApiException("Search Result not found!!!");

        return new GameDirectoryResponse().setGameDirectories(gameResult);
    }

    @Override
    public ApiAddResponse creditUserGame(CreditGameRequest request) {
        // Validate user is valid
        Users user = usersRepository.findById(request.getUserId()).orElse(null);
        if(user==null || user.getUserName()==null){
            throw new ApiException("User is not valid");
        }

        //validate game is valid
        List<Game> gamesList = gameRepository.findAll();
        Game game = gamesList.stream().filter(item -> item.getGameName().equalsIgnoreCase(request.getGameName()))
                .findFirst().orElse(null);
        if(game==null ){
            throw new ApiException("Game is not valid");
        }

        GameDirectory gameDirectory = gameDirectoryRepository.findByUserAndGame(request.getUserId(), request.getGameName());
        if(gameDirectory == null){
            throw new ApiException("User is not yet enrolled for game");
        }
        BigDecimal credit = (gameDirectory.getCredit()==null)?BigDecimal.ZERO:gameDirectory.getCredit();
        gameDirectory.setCredit(credit.add(request.getCredit()));
        try {
        gameDirectoryRepository.save(gameDirectory);
        }catch(Exception e){
            log.info("error:"+e.getMessage());
            throw new ApiException("Credit not applied successfully!!!");
        }
        return new ApiAddResponse().setMessage("Credit applied successfully")
                .setResult(true);
    }

    @Override
    public List<CreditGameStats> searchMaxCredit(String gameName) {
        List<GameDirectory> gameResult = null;
        if(gameName!=null){
            gameResult = gameDirectoryRepository.findByGame(gameName);
        }else{
            gameResult = gameDirectoryRepository.findAll();
        }

        List<CreditGameStats> result = new ArrayList<>();
        CreditGameStats stats = null;
        final Map<String, List<GameDirectory>> resultByGame = gameResult.stream().collect(groupingBy(dir -> dir.getGameName()));

        if(resultByGame == null){
            throw new ApiException("Issue with search result!!!");
        }

        for (Map.Entry<String,List<GameDirectory>> entry : resultByGame.entrySet()){

            GameDirectory maxCreditDirectory = entry.getValue().stream()
                    .max(Comparator.comparing(GameDirectory::getCredit))
                    .orElse(null);
            stats = new CreditGameStats()
                    .setCredit(maxCreditDirectory.getCredit())
                    .setUserName((maxCreditDirectory.getUsers()!=null)?maxCreditDirectory.getUsers().getUserName():null)
                    .setGameName(maxCreditDirectory.getGameName())
                    .setLevel(maxCreditDirectory.getLevel());
            result.add(stats);
        }
        return result;
    }


}
