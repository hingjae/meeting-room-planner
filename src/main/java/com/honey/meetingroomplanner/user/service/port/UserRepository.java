package com.honey.meetingroomplanner.user.service.port;

import com.honey.meetingroomplanner.user.domain.User;

public interface UserRepository {
    User save(User user);
}
