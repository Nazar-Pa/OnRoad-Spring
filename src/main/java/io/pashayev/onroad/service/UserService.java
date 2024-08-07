package io.pashayev.onroad.service;

import io.pashayev.onroad.domain.User;
import io.pashayev.onroad.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);
}
