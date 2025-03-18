package com.ervingorospe.grab_token.model.DTO;

import com.ervingorospe.grab_token.model.entity.UserDetails;

public record UserProfileDTO(
        String firstName,
        String lastName
) {
    public UserProfileDTO(UserDetails userDetails) {
        this(
            userDetails.getFirstName(),
            userDetails.getLastName()
        );
    }
}
