package com.honey.meetingroomplanner.user.infrastructure;

import com.honey.meetingroomplanner.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
}
