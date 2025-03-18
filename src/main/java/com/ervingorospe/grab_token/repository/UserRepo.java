package com.ervingorospe.grab_token.repository;

import com.ervingorospe.grab_token.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
}
