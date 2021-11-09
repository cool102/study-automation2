package at.study.redmine.model.project;

import at.study.redmine.db.requests.ProjectRequests;
import at.study.redmine.model.Createable;
import at.study.redmine.model.role.Role;
import at.study.redmine.model.user.CreatableEntity;
import at.study.redmine.model.user.User;
import at.study.redmine.utils.StringUtils;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Random;

@NoArgsConstructor
@Getter
@Setter
public class Project extends CreatableEntity implements Createable<Project> {
    private String name = "saf_Project_" + StringUtils.randomEnglishString(2);
    private String description = "saf_Description_" + StringUtils.randomEnglishString(11);
    private String homePage = "http://edu-at.dfu.i-teco.ru/projects/" + "saf(HomePage)" + StringUtils.randomEnglishString(5);
    private Boolean isPublic = true;
    private String parent_id = null;
    private String identifier = "saf_Identifier_" + StringUtils.randomEnglishString(5);
    private Integer status = 1;
    private Integer lft = new Random().nextInt(25) + 1;
    private Integer rgt = new Random().nextInt(25) + 1;
    private Boolean inherit_members = false;
    private Integer default_version_id = null;
    private Integer default_assigned_to_id = null;

    @Override
    @Step("Создан новый проект в БД")
    public Project create() {
        new ProjectRequests().create(this);
        return this;
    }

    /***
     * метод добавляет в таблицу  проект пользователя у которого может
     * быть несколько ролей.
     * @param user
     * @param role
     */
    // public int addUserAndProjectToMembers(User user, Project project) {
    //     return new ProjectRequests().create(user,project);
    // }
//
    // public void addMembersAndRolestoMemberRoles(int memberId, Role role){
    //     new ProjectRequests().create(memberId,role);
    // }
    //// public void addUserWithRoleToProject(User user, Project project, Role role) {
    ////     int memberId = new ProjectRequests().create(user, project);
    ////     new ProjectRequests().create(memberId, role);
    //// }
    public void addUserWithListRoletiProject(User user, Project project, List<Role> roles) {
        int memberId = new ProjectRequests().create(user, project);
        new ProjectRequests().create(memberId, roles);
    }

}
