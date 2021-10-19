package at.study.redmine.db.requests;

import at.study.redmine.db.connection.PostgresConnection;
import at.study.redmine.model.user.Token;
import at.study.redmine.model.user.User;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Что мы можем сделать с токенами?
 * Создать и получить (прочитать все) токены
 * Тут мы созадали Объект отвечающий за запросы к токенам:
 * 1 запрос - запрос создания токена
 * 2 запрос - запрос получения всех токенов
 */
@NoArgsConstructor
public class TokenRequests implements Create<Token>, ReadAll<Token> {
    private User user;

    public TokenRequests(User user) {
        this.user = user;
    }

    @Override
    public void create(Token token) {
        String query = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?,?,?,?,?) RETURNING id;\n";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(
                query,
                token.getUserId(),
                token.getAction().name().toLowerCase(),
                token.getValue(),
                token.getCreatedOn(),
                token.getUpdatedOn()
        );
        Integer tokenId = (Integer) queryResult.get(0).get("id");
        token.setId(tokenId);
    }

    @Override
    public List<Token> readAll() {
        Integer userId = Objects.requireNonNull(user.getId());
        String query = "SELECT * FROM tokens WHERE user_id = ?";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(query, userId);
        return queryResult.stream()
                .map(data -> from(data, user))
                .collect(Collectors.toList());

    }

    private Token from(Map<String, Object> data, User user) {
        Token token = (Token) new Token(user)
                .setAction(Token.TokenType.valueOf(
                        data.get("action").toString().toUpperCase(Locale.ROOT)))
                .setValue((String) data.get("value"))
                .setCreatedOn(toLocalDate(data.get("created_on")))
                .setUpdatedOn(toLocalDate(data.get("updated_on")))
                .setId((Integer) data.get("id"));
        return token;
    }

    private LocalDateTime toLocalDate(Object timestamp) {
        Timestamp ts = (Timestamp) timestamp;
        return ts.toLocalDateTime();
    }
}
