package at.study.redmine.model.user;

import at.study.redmine.db.requests.UserRequests;
import at.study.redmine.model.Createable;
import at.study.redmine.model.project.Project;
import at.study.redmine.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static at.study.redmine.utils.StringUtils.randomEnglishString;
import static at.study.redmine.utils.StringUtils.randomHexString;
import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class User extends CreatableEntity implements Createable<User> {

    private String login = "SAF(login)" + randomEnglishString(10);
    private String password = "1qaz@WSX";
    private String salt = randomHexString(32);
    private String hashedPassword = getHashedPassword();
    private String fisrtName = "SAF(FirstName)" + randomEnglishString(10);
    private String lastName = "SAF(LastName)" + randomEnglishString(10);
    private Boolean isAdmin = false;
    private Status status = Status.ACTIVE;
    private LocalDateTime lastLoginOn;
    private Language language = Language.RUSSIAN;
    private String authsourceId;
    private String type = "User";
    private String identityUrl;
    private MailNotfication mailNotification = MailNotfication.ALL;
    private Boolean mustChangePassword = false;
    private LocalDateTime passwordChangedOn;
    private List<Token> tokens = new ArrayList<>();
    private List<Email> emails = new ArrayList<>();


    public String getHashedPassword() {
        return sha1Hex(salt + sha1Hex(password));
    }

    @Override
    public User create() {
        new UserRequests().create(this);
        tokens.forEach(t -> t.setUserId(id));
        tokens.forEach(Token::create);
        emails.forEach(e -> e.setUserId(id));
        emails.forEach(Email::create);
        return this;
    }
    public void delete(){
        new UserRequests().delete(this.id);
    }

    public void update(){
        new UserRequests().update(this.id,this);
    }
    public void addProject(Project project, List<Role> roles) {
        //TODO: реализовать с помощью SQL-запроса
    }
}
