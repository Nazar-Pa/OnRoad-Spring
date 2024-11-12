package io.pashayev.onroad.resource;

import io.pashayev.onroad.domain.HttpResponse;
import io.pashayev.onroad.domain.User;
import io.pashayev.onroad.dto.LoginUserDTO;
import io.pashayev.onroad.dto.UserDTO;
import io.pashayev.onroad.dtomapper.UserDTOMapper;
import io.pashayev.onroad.repository.UserRepository;
import io.pashayev.onroad.service.UserService;
import io.pashayev.onroad.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static java.time.LocalDateTime.now;
import static java.util.Map.*;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final UserRepository<User> userRepository;
    // private final AuthenticationService authenticationService;
    // private final JwtService jwtService;
    //private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil = new JwtUtil();

    // @Valid checks if validation constraints in User are met, like "email cannot be empty
    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user) {
        // UserDTO userDTO = userService.createUser(user);
        UserDTO userDTO = UserDTOMapper.fromUser(userRepository.create(user));
        return ResponseEntity.created(getUri()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userDTO))
                        .message("User created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> loginUser(@RequestBody LoginUserDTO loginUserDto) {
        if ("validUser".equals(loginUserDto.getEmail()) && "validPassword".equals(loginUserDto.getPassword())) {
            String jwtToken = jwtUtil.generateToken(loginUserDto.getEmail());
            return ResponseEntity.created(getUri()).body(
                    HttpResponse.builder()
                            .timeStamp(now().toString())
                            .data(of("user", jwtToken))
                            .message("User logged in")
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .build());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<HttpResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
//        User authenticatedUser = authenticateUser(loginUserDto);
//
//        String jwtToken = jwtUtil.generateToken(authenticatedUser);
//
//        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
//
//        return ResponseEntity.ok(loginResponse);
//    }

//    @PostMapping("/register")
//    public ResponseEntity<HttpResponse> sendOTP(@RequestBody @Valid String otp) {
//        UserDTO userDTO = userService.sendOTPtoNumber(otp);
//        return ResponseEntity.created(getUri()).body(
//                HttpResponse.builder()
//                        .timeStamp(now().toString())
//                        .data(of("user", userDTO))
//                        .message("User created")
//                        .status(HttpStatus.CREATED)
//                        .statusCode(HttpStatus.CREATED.value())
//                        .build());
//    }

    @GetMapping("verify")
    public ResponseEntity<HttpResponse> confirmUserAccount(@RequestParam("token") String token) {
        Boolean isSuccess = userService.verifyUser(token);
        return ResponseEntity.created(getUri()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("Success", isSuccess))
                        .message("Account verified")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

//    public User authenticateUser(LoginUserDTO input) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        input.getEmail(),
//                        input.getPassword()
//                )
//        );
//
//        return userRepository.findByEmail(input.getEmail());
//    }

    private URI getUri() {
         return URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/verify/<userId>").toUriString()
         );
    }
}
