package at.tests;

import at.study.redmine.model.project.Project;
import org.testng.annotations.Test;

public class DatabaseProjectCreationTest {

    @Test
    public void ProjectCreationTest(){
        Project project = new Project();
        project.create();
        System.out.println(project.getName());

    }
}
