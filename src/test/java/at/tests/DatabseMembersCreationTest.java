package at.tests;

import at.study.redmine.model.project.Project;
import at.study.redmine.model.role.Role;
import at.study.redmine.model.user.User;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class DatabseMembersCreationTest {
    @Test

    public void databaseMemberCreationTest(){
        User user = new User();
        user.create();
        Project project = new Project();
        project.create();
        List<Role> roles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Role role = new Role();
            role.create();
            roles.add(role);
        }

       // int memberId = project.addUserAndProjectToMembers(user,project);
       // project.addMembersAndRolestoMemberRoles(memberId,role);
       //// project.addUserWithRoleToProject(user, project, role);
        project.addUserWithListRoletiProject(user,project,roles);

        System.out.println(user.getLogin());
        System.out.println(user.getFisrtName());
        System.out.println(project.getName());
        for (int i = 0; i < 2; i++) {
            System.out.println(roles.get(i));
        }

    }
}
