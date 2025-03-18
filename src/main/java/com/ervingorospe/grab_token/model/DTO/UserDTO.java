package com.ervingorospe.grab_token.model.DTO;

import com.ervingorospe.grab_token.model.entity.User;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String email
) {
    // Constructor accepting only User (extracting details from User entity)
    public UserDTO(User user) {
        this(
            user.getId(),
            user.getEmail()
        );
    }
}
