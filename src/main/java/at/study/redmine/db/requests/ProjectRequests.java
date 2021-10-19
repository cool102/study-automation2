package at.study.redmine.db.requests;

import at.study.redmine.db.connection.PostgresConnection;
import at.study.redmine.model.project.Project;
import at.study.redmine.model.role.Role;
import at.study.redmine.model.user.User;

import java.util.List;
import java.util.Map;

public class ProjectRequests implements Create<Project> {

    public int create(User user, Project project) {
        String query = "INSERT INTO members" +
                " (id, user_id, project_id, created_on, mail_notification)" +
                " VALUES(DEFAULT, ?, ?, ?, ?) RETURNING id;\n";
        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query,
                user.getId(),
                project.getId(),
                user.getCreatedOn(),
                true
        );
        return (Integer) result.get(0).get("id");
    }

    public void create(int memberId, List<Role> roles) {
        int length = roles.size();

        String query = "INSERT INTO member_roles " +
                "(id, member_id, role_id, inherited_from) " +
                "VALUES(DEFAULT, ?, ?, ?);\n";

        for (int i = 0; i < length; i++) {
            List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                    query,
                    memberId,
                    roles.get(i).getId(),
                    null
            );

        }

    }


    @Override
    public void create(Project project) {
        String query = "INSERT INTO projects " +
                "(id, \"name\", description, homepage, is_public, " +
                "parent_id, created_on, updated_on, identifier, status," +
                " lft, rgt, inherit_members, default_version_id, default_assigned_to_id)" +
                " VALUES(DEFAULT,?,?,?,?," +
                "?, ?, ?, ?, ?," +
                "?, ?, ?, ?, ?)RETURNING id;\n";


        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query,
                project.getName(),
                project.getDescription(),
                project.getHomePage(),
                project.getIsPublic(),
                project.getParent_id(),
                project.getCreatedOn(),
                project.getUpdatedOn(),
                project.getIdentifier(),
                project.getStatus(),
                project.getLft(),
                project.getRgt(),
                project.getInherit_members(),
                project.getDefault_version_id(),
                project.getDefault_assigned_to_id()
        );
        Integer projectId = (Integer) result.get(0).get("id");
        project.setId(projectId);
    }
}

