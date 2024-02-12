package org.game.service;

import org.game.exception.ApiException;
import org.game.request.*;
import org.game.response.ApiAddResponse;
import org.game.response.CreditGameStats;
import org.game.response.GameDirectoryResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameServiceTest {
    @Autowired
    GameService gameService;

    @Transactional
    @Test
    public void testGameService(){
        createUser();
        createGame();
        enrollUserToGameValidation();
        enrollUserToGame();
        searchGameValidation();
        searchGame();
        addCreditToUserGame();
        searchMaxCredit();
    }

    private void createUser(){
        UserRequest user1 = new UserRequest()
                .setUserId(1)
                .setUserName("John")
                .setNickName("John01")
                .setGeography("usa")
                .setPhoneNumber("12345")
                .setEmail("John@test.com");

        UserRequest user2 = new UserRequest()
                .setUserId(2)
                .setUserName("Kim")
                .setNickName("Kim01")
                .setGeography("europe")
                .setPhoneNumber("12345")
                .setEmail("Kim@test.com");
        ApiAddResponse result1 = gameService.createUser(user1);
        ApiAddResponse result2 = gameService.createUser(user2);
        Assertions.assertEquals("User Created Successfully",result1.getMessage());
        Assertions.assertEquals("User Created Successfully",result2.getMessage());
    }

    private void createGame(){
        GameRequest game1 = new GameRequest()
                .setGameId(1)
                .setGameName("fortnite")
                .setDescription("game1");
        GameRequest game2 = new GameRequest()
                .setGameId(2)
                .setGameName("dota")
                .setDescription("game2");
        GameRequest game3 = new GameRequest()
                .setGameId(3)
                .setGameName("amongus")
                .setDescription("game3");
        ApiAddResponse result1 = gameService.createGame(game1);
        ApiAddResponse result2 = gameService.createGame(game2);
        ApiAddResponse result3 = gameService.createGame(game3);
        Assertions.assertEquals("Game Created Successfully",result1.getMessage());
        Assertions.assertEquals("Game Created Successfully",result2.getMessage());
        Assertions.assertEquals("Game Created Successfully",result3.getMessage());

    }

    private void enrollUserToGameValidation(){
        EnrollGamerRequest request = new EnrollGamerRequest();

        /*User is not valid**/
        request.setUserId("3")
                .setLevel("PRO")
                .setGameName("fortnite");

        ApiException exception1 = Assertions.assertThrows(ApiException.class,()->{
            ApiAddResponse result = gameService.enrollUserGame(request);
        });
        Assertions.assertEquals("User is not valid",exception1.getMessage());

        /*Game is not valid**/
        request.setUserId("1");
        request.setGameName("game");
        exception1 = Assertions.assertThrows(ApiException.class,()->{
            ApiAddResponse result = gameService.enrollUserGame(request);
        });
        Assertions.assertEquals("Game is not valid",exception1.getMessage());

        /*Level is not valid**/
        request.setGameName("fortnite");
        request.setLevel("level");
        exception1 = Assertions.assertThrows(ApiException.class,()->{
            ApiAddResponse result = gameService.enrollUserGame(request);
        });
        Assertions.assertEquals("Level Should be one of (noob, pro, invincible) List",exception1.getMessage());

    }

    private void enrollUserToGame(){

        EnrollGamerRequest request1 = new EnrollGamerRequest() // user1
                .setUserId("1")
                .setLevel("PRO")
                .setGameName("fortnite");

        EnrollGamerRequest request2 = new EnrollGamerRequest()// user1
                .setUserId("1")
                .setLevel("NOOB")
                .setGameName("dota");

        EnrollGamerRequest request3 = new EnrollGamerRequest()// user1
                .setUserId("1")
                .setLevel("INVINCIBLE")
                .setGameName("amongus");

        EnrollGamerRequest request4 = new EnrollGamerRequest()// user2
                .setUserId("2")
                .setLevel("PRO")
                .setGameName("fortnite");

        EnrollGamerRequest request5 = new EnrollGamerRequest() // user2
                .setUserId("2")
                .setLevel("NOOB")
                .setGameName("dota");

        EnrollGamerRequest request6 = new EnrollGamerRequest() // user2
                .setUserId("2")
                .setLevel("INVINCIBLE")
                .setGameName("amongus");

        ApiAddResponse result1 = gameService.enrollUserGame(request1);
        ApiAddResponse result2 = gameService.enrollUserGame(request2);
        ApiAddResponse result3 = gameService.enrollUserGame(request3);
        ApiAddResponse result4 = gameService.enrollUserGame(request4);
        ApiAddResponse result5 = gameService.enrollUserGame(request5);
        ApiAddResponse result6 = gameService.enrollUserGame(request6);

        Assertions.assertEquals("User enrolled into game Successfully",result1.getMessage());
        Assertions.assertEquals("User enrolled into game Successfully",result2.getMessage());
        Assertions.assertEquals("User enrolled into game Successfully",result3.getMessage());
        Assertions.assertEquals("User enrolled into game Successfully",result4.getMessage());
        Assertions.assertEquals("User enrolled into game Successfully",result5.getMessage());
        Assertions.assertEquals("User enrolled into game Successfully",result6.getMessage());

    }

    private void searchGameValidation(){
        GameDirectoryRequest request = new GameDirectoryRequest().setGeography("usa");
        ApiException exception1 = Assertions.assertThrows(ApiException.class,()->{
            GameDirectoryResponse response = gameService.searchGame(request);
        });
        Assertions.assertEquals("Search based on either level / game name is required!!!",exception1.getMessage());
    }

    private void searchGame(){

        /** Search by only GameName **/
        GameDirectoryRequest request1 = new GameDirectoryRequest().setGameName("fortnite");
        GameDirectoryResponse response = gameService.searchGame(request1);
        Assertions.assertEquals(2,response.getGameDirectories().size());
        Assertions.assertEquals("fortnite",response.getGameDirectories().get(0).getGameName());

        /** Search by only GameName **/
        GameDirectoryRequest request2 = new GameDirectoryRequest().setLevel("INVINCIBLE");
        GameDirectoryResponse response2 = gameService.searchGame(request2);
        Assertions.assertEquals(2,response2.getGameDirectories().size());
        Assertions.assertEquals("INVINCIBLE",response2.getGameDirectories().get(0).getLevel());

        /** Search by  GameName and Level **/
        GameDirectoryRequest request3 = new GameDirectoryRequest()
                .setGameName("fortnite")
                .setLevel("PRO");
        GameDirectoryResponse response3 = gameService.searchGame(request3);
        Assertions.assertEquals(2,response3.getGameDirectories().size());
        Assertions.assertEquals("fortnite",response3.getGameDirectories().get(0).getGameName());
        Assertions.assertEquals("PRO",response3.getGameDirectories().get(0).getLevel());

        /** Search by  GameName and Level and Geography
         * Note : In Junit test h2 not working many to one relation**/
//        GameDirectoryRequest request4 = new GameDirectoryRequest()
//                .setGameName("fortnite")
//                .setLevel("PRO")
//                .setGeography("europe");
//        GameDirectoryResponse response4 = gameService.searchGame(request4);
//        Assertions.assertEquals(1,response4.getGameDirectories().size());
//        Assertions.assertEquals("fortnite",response4.getGameDirectories().get(0).getGameName());
//        Assertions.assertEquals("PRO",response4.getGameDirectories().get(0).getLevel());
    }

    private void addCreditToUserGame(){
        CreditGameRequest request1 = new CreditGameRequest()
                .setUserId("1")
                .setGameName("fortnite")
                .setCredit(new BigDecimal(100));

        CreditGameRequest request2 = new CreditGameRequest()
                .setUserId("1")
                .setGameName("dota")
                .setCredit(new BigDecimal(200));

        CreditGameRequest request3 = new CreditGameRequest()
                .setUserId("1")
                .setGameName("amongus")
                .setCredit(new BigDecimal(300));

        CreditGameRequest request4 = new CreditGameRequest()
                .setUserId("2")
                .setGameName("fortnite")
                .setCredit(new BigDecimal(500));

        CreditGameRequest request5 = new CreditGameRequest()
                .setUserId("2")
                .setGameName("dota")
                .setCredit(new BigDecimal(600));

        CreditGameRequest request6 = new CreditGameRequest()
                .setUserId("2")
                .setGameName("amongus")
                .setCredit(new BigDecimal(700));

        ApiAddResponse result1 = gameService.creditUserGame(request1);
        ApiAddResponse result2 = gameService.creditUserGame(request2);
        ApiAddResponse result3 = gameService.creditUserGame(request3);
        ApiAddResponse result4 = gameService.creditUserGame(request4);
        ApiAddResponse result5 = gameService.creditUserGame(request5);
        ApiAddResponse result6 = gameService.creditUserGame(request6);

        Assertions.assertEquals("Credit applied successfully",result1.getMessage());
        Assertions.assertEquals("Credit applied successfully",result2.getMessage());
        Assertions.assertEquals("Credit applied successfully",result3.getMessage());
        Assertions.assertEquals("Credit applied successfully",result4.getMessage());
        Assertions.assertEquals("Credit applied successfully",result5.getMessage());
        Assertions.assertEquals("Credit applied successfully",result6.getMessage());
    }

    private void searchMaxCredit(){
        //search without filter
        List<CreditGameStats> statsResult = gameService.searchMaxCredit(null);
        Assertions.assertEquals(3,statsResult.size());
        Assertions.assertEquals(BigDecimal.valueOf(700),statsResult.get(0).getCredit()); //CreditGameStats(gameName=amongus, level=INVINCIBLE, Credit=700, userName=null, result=false, message=null)
        Assertions.assertEquals(BigDecimal.valueOf(600),statsResult.get(1).getCredit()); //CreditGameStats(gameName=dota, level=NOOB, Credit=600, userName=null, result=false, message=null)
        Assertions.assertEquals(BigDecimal.valueOf(500),statsResult.get(2).getCredit()); //CreditGameStats(gameName=fortnite, level=PRO, Credit=500, userName=null, result=false, message=null)

        //search with filter
        statsResult = gameService.searchMaxCredit("amongus");
        Assertions.assertEquals(1,statsResult.size());
        Assertions.assertEquals(BigDecimal.valueOf(700),statsResult.get(0).getCredit());
    }

}
