package io.pashayev.onroad.service;

import io.pashayev.onroad.domain.User;
import io.pashayev.onroad.dto.UserDTO;

public interface VerificationService {
    UserDTO enableUser(Long userId);
}
