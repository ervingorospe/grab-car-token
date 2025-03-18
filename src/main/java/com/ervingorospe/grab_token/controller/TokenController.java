package com.ervingorospe.grab_token.controller;

import com.ervingorospe.grab_token.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/test")
    public String testing() {
        return "test";
    }

    @PostMapping("/generate/{id}")
    public ResponseEntity<String> createToken(@PathVariable String id, @RequestParam(defaultValue = "registration") String type) {
        tokenService.saveToken(UUID.fromString(id), type);
        return ResponseEntity.status(HttpStatus.CREATED).body("Token Generated");
    }

    @PostMapping("/{id}/verify/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String id, @PathVariable String token, @RequestParam(defaultValue = "registration") String type) {
        tokenService.verifyToken(UUID.fromString(id), token, type);

        return ResponseEntity.status(HttpStatus.OK).body("Token Verified");
    }
}
