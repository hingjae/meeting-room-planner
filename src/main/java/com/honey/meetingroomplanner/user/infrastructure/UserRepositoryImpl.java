package com.honey.meetingroomplanner.user.infrastructure;

import com.honey.meetingroomplanner.user.UserEntity;
import com.honey.meetingroomplanner.user.domain.User;
import com.honey.meetingroomplanner.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user))
                .toModel();
    }
}
