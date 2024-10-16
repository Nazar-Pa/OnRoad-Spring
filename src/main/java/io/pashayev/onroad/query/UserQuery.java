package io.pashayev.onroad.query;

public class UserQuery {
    // :fristName comes from getSqlParameterSource inside UserRepositoryImpl
    public static final String INSERT_USER_QUERY = "INSERT INTO Users (first_name, last_name, email, password) VALUES (:firstName, :lastName, :email, :password)";
    public static final String COUNT_USER_EMAIL_QUERY = "SELECT COUNT(*) FROM Users WHERE email = :email";
    public static final String INSERT_ACCOUNT_VERIFICATION_URL_QUERY = "INSERT INTO AccountVerifications (user_id, token) VALUES (:userId, :token)";
    public static final String GET_USERID_OF_TOKEN = "SELECT * FROM AccountVerifications WHERE token = :token";
    public static final String ENABLE_USER_ACCOUNT_QUERY = "UPDATE Users SET enabled = :enabled WHERE id = :userId";
}