package at.study.redmine.db.requests;

import at.study.redmine.db.connection.PostgresConnection;
import at.study.redmine.model.user.Email;
import at.study.redmine.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequests implements Create<Email> {
    private User user;

    @Override
    public void create(Email email) {
        String query = "INSERT INTO email_addresses\n" +
        "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n"+
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?) RETURNING id;\n";
        List<Map<String,Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query,
                email.getUserId(),
                email.getAddress(),
                email.getIsDefault(),
                email.getNotify(),
                email.getCreatedOn(),
                email.getUpdatedOn()
        );
        Integer emailId = (Integer) result.get(0).get("id");
        email.setId(emailId);
    }
}
