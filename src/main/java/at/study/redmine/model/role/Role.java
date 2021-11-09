package at.study.redmine.model.role;

import at.study.redmine.db.requests.RoleRequests;
import at.study.redmine.model.Createable;
import at.study.redmine.model.user.Entity;
import at.study.redmine.utils.StringUtils;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@NoArgsConstructor
@Getter
@Setter
public class Role extends Entity implements Createable<Role> {
    private String name = "saf_Role_" + StringUtils.randomEnglishString(5);
    private Integer position = new Random().nextInt(25) + 1;
    private Boolean assignable = true;
    private Integer builtin = 0;
    private Permissions permissions = Permissions.LOG_TIME;
    private Issues_visibility issuesVisibility = Issues_visibility.ALL;
    private Users_visibility usersVisibility = Users_visibility.ALL;
    private String timeEntriesVisibility = "all";
    private Boolean allRolesManaged = true;
    private String settings = null;

    @Step("Создана новая роль в БД")
    @Override
    public Role create() {
        new RoleRequests().create(this);
        return this;
    }
}
