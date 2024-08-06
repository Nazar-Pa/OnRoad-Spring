package io.pashayev.onroad.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)

public class User {
    private  Long id;
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(@NotEmpty(message = "First name cannot be empty") String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NotEmpty(message = "Last name cannot be empty") String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(@NotEmpty(message = "Email cannot be empty") @Email(message = "Invalid email. Please enter a valid email address") String email) {
        this.email = email;
    }

    public void setPassword(@NotEmpty(message = "Password cannot be empty") String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public @NotEmpty(message = "First name cannot be empty") String getFirstName() {
        return firstName;
    }

    public @NotEmpty(message = "Last name cannot be empty") String getLastName() {
        return lastName;
    }

    public @NotEmpty(message = "Email cannot be empty") @Email(message = "Invalid email. Please enter a valid email address") String getEmail() {
        return email;
    }

    public @NotEmpty(message = "Password cannot be empty") String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email. Please enter a valid email address")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    private String phone;
    private String imageUrl;
    private Boolean enabled;
    private LocalDateTime createdAt;
}
