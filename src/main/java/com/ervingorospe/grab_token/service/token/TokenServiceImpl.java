package com.ervingorospe.grab_token.service.token;

import com.ervingorospe.grab_token.handler.error.TokenNotFoundException;
import com.ervingorospe.grab_token.handler.error.UserNotFoundException;
import com.ervingorospe.grab_token.model.entity.Token;
import com.ervingorospe.grab_token.model.entity.User;
import com.ervingorospe.grab_token.repository.TokenRepo;
import com.ervingorospe.grab_token.repository.UserRepo;
import com.ervingorospe.grab_token.service.email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService{
    private final TokenRepo repository;
    private final UserRepo userRepo;
    private final EmailService emailService;

    @Autowired
    public TokenServiceImpl(TokenRepo repository, UserRepo userRepo, EmailService emailService) {
        this.repository = repository;
        this.userRepo = userRepo;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public void saveToken(UUID id, String type) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User Does not exist"));

        String token = UUID.randomUUID().toString();
        LocalDateTime expiration = LocalDateTime.now().plusHours(24);
        Token newToken = new Token(token, type, id, expiration);

        repository.save(newToken);

        try {
            String emailContent = "<a href='http://localhost:7000/token/%s/verify/%s?type=%s'>Click here to verify your account</a>".formatted(user.getId(),token, type);
            emailService.sendEmail(user.getEmail(), "Verification", emailContent);
        } catch (MessagingException e) {
           throw new RuntimeException("Failed to sent email");
        }
    }

    @Override
    @Transactional
    public void verifyToken(UUID id, String token, String type) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Does not exist"));
        System.out.println(token);
        Token getToken = repository.findByToken(token).orElseThrow(() -> new TokenNotFoundException("Token not found"));

        if (this.isTokenValid(getToken)) {
            try {
                user.setActive(true);
                userRepo.save(user);
                repository.delete(getToken);
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isTokenValid(Token token) {
        return !token.getExpiration().isBefore(LocalDateTime.now());
    }
}
