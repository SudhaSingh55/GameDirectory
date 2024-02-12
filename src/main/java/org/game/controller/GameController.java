package org.game.controller;

import lombok.RequiredArgsConstructor;
import org.game.exception.ApiException;
import org.game.request.*;
import org.game.response.ApiAddResponse;
import org.game.response.CreditGameStats;
import org.game.response.GameDirectoryResponse;
import org.game.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/gamers")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/create-user")
    public ResponseEntity<ApiAddResponse> createUser(@RequestBody UserRequest userRequest) {
        try {
            ApiAddResponse apiAddResponse = gameService.createUser(userRequest);
            return ResponseEntity.ok(apiAddResponse);
        }catch(Exception exception){
            return ResponseEntity.badRequest().body(new ApiAddResponse(false, exception.getMessage()));
        }
    }

    @PostMapping("/create-game")
    public ResponseEntity<ApiAddResponse> createGame(@RequestBody GameRequest gameRequest) {
        try {
            ApiAddResponse apiAddResponse = gameService.createGame(gameRequest);
            return ResponseEntity.ok(apiAddResponse);
        }catch(Exception exception){
            return ResponseEntity.badRequest().body(new ApiAddResponse(false, exception.getMessage()));
        }
    }

    @PostMapping("/enroll-gamer")
    public ResponseEntity<ApiAddResponse> enrollUserGame(@RequestBody EnrollGamerRequest request) {
        try {
            ApiAddResponse apiAddResponse = gameService.enrollUserGame(request);
            return ResponseEntity.ok(apiAddResponse);
        }catch(Exception exception){
            return ResponseEntity.badRequest().body(new ApiAddResponse(false, exception.getMessage()));
        }
    }

    @GetMapping("/search-game")
    public ResponseEntity<GameDirectoryResponse> searchGame( GameDirectoryRequest request) {
        try {
            GameDirectoryResponse response = gameService.searchGame(request);
            return ResponseEntity.ok(response.setResult(true).setMessage("Success"));
        }catch(Exception exception){
            return ResponseEntity.badRequest().body(new GameDirectoryResponse().setResult(false).setMessage(exception.getMessage()));
        }
    }

    @PostMapping("/credit-game")
    public ResponseEntity<ApiAddResponse> creditGame( @RequestBody CreditGameRequest request) {
        try {
            ApiAddResponse apiAddResponse = gameService.creditUserGame(request);
            return ResponseEntity.ok(apiAddResponse);
        }catch(Exception exception){
            return ResponseEntity.badRequest().body(new ApiAddResponse(false, exception.getMessage()));
        }
    }

    @GetMapping("/search-max-credit")
    public ResponseEntity<List<CreditGameStats>> searchCreditGame(@RequestParam(required = false) String gameName) {
        try {
            List<CreditGameStats> results = gameService.searchMaxCredit(gameName);
            return ResponseEntity.ok(results);
        }catch(Exception exception){
            CreditGameStats stats = new CreditGameStats().setResult(false).setMessage(exception.getMessage());
            List<CreditGameStats> results = new ArrayList<>();
            results.add(stats);
            return ResponseEntity.badRequest().body(results);
        }
    }
}
