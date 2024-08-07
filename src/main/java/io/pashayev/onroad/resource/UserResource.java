package io.pashayev.onroad.resource;

import io.pashayev.onroad.domain.HttpResponse;
import io.pashayev.onroad.domain.User;
import io.pashayev.onroad.dto.UserDTO;
import io.pashayev.onroad.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    private URI getUri() {
         return URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/verify/<userId>").toUriString()
         );
    }
}
