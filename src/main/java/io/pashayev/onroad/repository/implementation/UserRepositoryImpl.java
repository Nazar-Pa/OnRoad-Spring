package io.pashayev.onroad.repository.implementation;

import io.pashayev.onroad.domain.User;
import io.pashayev.onroad.domain.Verification;
import io.pashayev.onroad.exception.ApiException;
import io.pashayev.onroad.repository.UserRepository;
import io.pashayev.onroad.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.pashayev.onroad.enumeration.VerificationType.ACCOUNT;
import static io.pashayev.onroad.query.UserQuery.*;
import static java.util.Map.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {
    private final NamedParameterJdbcTemplate jdbc;
    private final BCryptPasswordEncoder encoder;
    private final EmailService emailService;
    private Verification verification;

    @Override
    public User create(User user) {
        // check the email is unique
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0)
            throw new ApiException("Email already in use. Please use a different email and try again.");
        // Save new user
        try {
            // get ID of the created user
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder, new String[] { "id" });
            user.setId(requireNonNull(holder.getKey()).longValue());
            // Send verification URL
            verification = new Verification(user);

//             String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            // Save URL in verification table
             jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId", user.getId(), "token", verification.getToken()));
            // Send email to user with verification URL
             emailService.sendSimpleMailMessage(user.getFirstName(), user.getEmail(), verification.getToken());
            user.setEnabled(false);
            // Return the newly created user
            return user;
            // If any errors, throw exception with proper message
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public Boolean enableUserAccount(String token) {
        Long user_id = verification.getUserId();

//        List<Verification> allUsers = jdbc.query(GET_USERID_OF_TOKEN, of("token", token), new BeanPropertyRowMapper(Verification.class));
        jdbc.update(ENABLE_USER_ACCOUNT_QUERY, of("userId", user_id, "enabled", true));
//        System.out.println("all users " + allUsers.get(0).getUserId());
        return Boolean.TRUE;
    }

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                // not to store the raw password in the database
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/verify/" + type + "/" + key).toUriString();
    }

}
