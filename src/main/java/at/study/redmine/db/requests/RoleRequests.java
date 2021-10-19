package at.study.redmine.db.requests;

import at.study.redmine.db.connection.PostgresConnection;
import at.study.redmine.model.role.Role;

import java.util.List;
import java.util.Map;

public class RoleRequests implements Create<Role> {

    @Override
    public void create(Role role) {
        String query = "INSERT INTO roles (id, \"name\", \"position\"," +
                " assignable, builtin, permissions, issues_visibility," +
                " users_visibility, time_entries_visibility," +
                " all_roles_managed, settings)" +
                " VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;\n";

        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query,
                role.getName(),
                role.getPosition(),
                role.getAssignable(),
                role.getBuiltin(),
                role.getPermissions().name().toLowerCase(),
                role.getIssuesVisibility().name().toLowerCase(),
                role.getUsersVisibility().name().toLowerCase(),
                role.getTimeEntriesVisibility(),
                role.getAllRolesManaged(),
                role.getSettings()
        );
        Integer roleId = (Integer) result.get(0).get("id");
        role.setId(roleId);


    }
}
