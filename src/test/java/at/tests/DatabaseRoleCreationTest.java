package at.tests;

import at.study.redmine.model.role.Role;
import org.testng.annotations.Test;

public class DatabaseRoleCreationTest {
    @Test
    public void roleCreationTest(){
        Role role = new Role();
        role.create();
        System.out.println(role.getName());


    }
}
