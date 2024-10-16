package io.pashayev.onroad.service.implementation;

import io.pashayev.onroad.domain.User;
import io.pashayev.onroad.domain.Verification;
import io.pashayev.onroad.dto.UserDTO;
import io.pashayev.onroad.dtomapper.UserDTOMapper;
import io.pashayev.onroad.repository.UserRepository;
import io.pashayev.onroad.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;

    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }

    @Override
    public Boolean verifyUser(String token) {
        return userRepository.enableUserAccount(token);
    }

//    @Override
//    public Boolean verifyToken(String token) {
//        Verification verification = verificationRepository.findByToken(token);
//        User user = userRepository.findByEmailIgnoreCase(verification.getuser().getEmail());
//        user.setEnabled(true);
//        userUserRepository.save(user);
//        return Boolean.TRUE;
//    }

}
