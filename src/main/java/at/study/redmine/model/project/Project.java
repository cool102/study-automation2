package at.study.redmine.model.project;

import at.study.redmine.db.requests.ProjectRequests;
import at.study.redmine.model.Createable;
import at.study.redmine.model.members.Members;
import at.study.redmine.model.role.Role;
import at.study.redmine.model.user.CreatableEntity;
import at.study.redmine.model.user.User;
import at.study.redmine.utils.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Random;

@NoArgsConstructor
@Getter
@Setter
public class Project extends CreatableEntity implements Createable<Project> {
    private String name = "SAFProject_" + StringUtils.randomEnglishString(5);
    private String description = "SAFDescription_" + StringUtils.randomEnglishString(5);
    private String homePage = "http://edu-at.dfu.i-teco.ru/projects/" + "SAFHomePage" + StringUtils.randomEnglishString(5);
    private Boolean isPublic = true;
    private String parent_id = null;
    private String identifier = "SAFIdentifier_" + StringUtils.randomEnglishString(5);
    private Integer status = 1;
    private Integer lft = new Random().nextInt(25) + 1;
    private Integer rgt = new Random().nextInt(25) + 1;
    private Boolean inherit_members = false;
    private Integer default_version_id = null;
    private Integer default_assigned_to_id = null;

    @Override
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
    public int addUserAndProjectToMembers(User user, Project project) {
        return new ProjectRequests().create(user,project);
    }

    public void addMembersAndRolestoMemberRoles(int memberId, Role role){
        new ProjectRequests().create(memberId,role);
    }



}
