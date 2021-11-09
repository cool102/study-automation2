package at.study.redmine.model.user;

import at.study.redmine.db.requests.EmailRequests;
import at.study.redmine.model.Createable;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import static at.study.redmine.utils.StringUtils.randomEmail;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Email extends CreatableEntity implements Createable<Email> {

    private Integer userId;
    private String address = randomEmail();
    private Boolean isDefault = true;
    private Boolean notify = true;

    public Email(User user) {

        this.userId = user.id;
        user.getEmails().add(this);
    }

    @Override
    @Step("Создан email в БД")
    public Email create() {
        new EmailRequests().create(this);
        return this;
    }
}
