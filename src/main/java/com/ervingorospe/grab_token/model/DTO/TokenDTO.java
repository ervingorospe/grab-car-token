package com.ervingorospe.grab_token.model.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record TokenDTO (
    UUID id,
    String type,
    LocalDateTime expiration,
    UUID userId
) { }
