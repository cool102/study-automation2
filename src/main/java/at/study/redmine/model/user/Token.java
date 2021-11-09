package at.study.redmine.model.user;

import at.study.redmine.db.requests.TokenRequests;
import at.study.redmine.model.Createable;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static at.study.redmine.utils.StringUtils.randomHexString;

@Getter
@Setter
@Accessors(chain = true)
public class Token extends CreatableEntity implements Createable<Token> {
    private Integer userId;
    private TokenType action = TokenType.API;
    private String value = randomHexString(40);
    public Token(User user) {
        this.userId = user.id;
        user.getTokens().add(this);
    }

    @Override
    @Step("Создан токен в БД")
    public Token create() {
        new TokenRequests().create(this);
        return this;
    }


    public enum TokenType {
        API,
        FEEDS,
        SESSION
    }

}
