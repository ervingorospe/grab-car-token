package com.ervingorospe.grab_token.service.token;

import java.util.UUID;

public interface TokenService {
    void saveToken(UUID id, String type);
    void verifyToken(UUID id, String token, String type);
}
