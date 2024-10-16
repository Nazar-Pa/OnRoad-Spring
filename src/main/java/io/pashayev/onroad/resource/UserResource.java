package io.pashayev.onroad.resource;

import io.pashayev.onroad.domain.HttpResponse;
import io.pashayev.onroad.domain.User;
import io.pashayev.onroad.dto.UserDTO;
import io.pashayev.onroad.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // @Valid checks if validation constraints in User are met, like "email cannot be empty
    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user) {
        UserDTO userDTO = userService.createUser(user);
        return ResponseEntity.created(getUri()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userDTO))
                        .message("User created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

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

    private URI getUri() {
         return URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/verify/<userId>").toUriString()
         );
    }
}
