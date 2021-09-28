package at.tests;

import at.study.redmine.model.project.Project;
import org.testng.annotations.Test;

public class ProjectTest {
    @Test
    public void projectCreationTest(){
        Project project = new Project();
        project.setName("Новый проект");
    }
}
