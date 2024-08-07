package io.pashayev.onroad.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String imageUrl;
    private Boolean enabled;
    private LocalDateTime createdAt;
}
