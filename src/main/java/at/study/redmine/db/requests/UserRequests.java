package at.study.redmine.db.requests;

import at.study.redmine.db.connection.PostgresConnection;
import at.study.redmine.model.user.Status;
import at.study.redmine.model.user.User;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class UserRequests implements Create<User>, Read<User>, Update<User>, Delete {

    @Override
    public void create(User user) {
        String query = "INSERT INTO users" +
                "(id, login, hashed_password, firstname, lastname, \"admin\"," +
                "status, last_login_on, \"language\", auth_source_id, created_on, updated_on," +
                "\"type\", identity_url, mail_notification, salt, must_change_passwd,passwd_changed_on)" +
                "VALUES(DEFAULT, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?,?,?,?,?,?,?) RETURNING id;\n";

        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query,
                user.getLogin(),
                user.getHashedPassword(),
                user.getFisrtName(),
                user.getLastName(),
                user.getIsAdmin(),
                user.getStatus().statusCode,
                user.getLastLoginOn(),
                user.getLanguage().laguageCode,
                user.getAuthsourceId(),
                user.getCreatedOn(),
                user.getUpdatedOn(),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification().name().toLowerCase(),
                user.getSalt(),
                user.getMustChangePassword(),
                user.getPasswordChangedOn()
        );
        Integer userId = (Integer) result.get(0).get("id");
        user.setId(userId);
    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM users WHERE id =?";
        PostgresConnection.INSTANCE.executeUpdate(
                query,
                id
        );
    }

    @Override
    public void update(Integer id, User user) {

        String query = "UPDATE users SET login=?," +
                "hashed_password=?, firstname=?," +
                "lastname=?, \"admin\"=?, status=?," +
                "last_login_on=?, \"language\"=?," +
                "auth_source_id=?, created_on=?," +
                "updated_on=?, \"type\"=?," +
                "identity_url=?, mail_notification=?, salt=?," +
                "must_change_passwd=?, passwd_changed_on=? WHERE id=?;\n";

        PostgresConnection.INSTANCE.executeUpdate(
                query,
                user.getLogin(),
                user.getHashedPassword(),
                user.getFisrtName(),
                user.getLastName(),
                user.getIsAdmin(),
                user.getStatus().statusCode,
                user.getLastLoginOn(),
                user.getLanguage().laguageCode,
                user.getAuthsourceId(),
                user.getCreatedOn(),
                user.getUpdatedOn(),
                user.getType(),
                user.getIdentityUrl(),
                user.getMailNotification().name().toLowerCase(),
                user.getSalt(),
                user.getMustChangePassword(),
                user.getPasswordChangedOn(),
                id
        );
        user.setId(id);

    }

    @Override
    public User read(Integer id) {
        String query = "SELECT * FROM users WHERE id=?";

        List<Map<String, Object>> result = PostgresConnection.INSTANCE.executeQuery(
                query,
                id
        );

        User returnUser = new User();
        try {
            returnUser.setId((Integer) result.get(0).get("id"));
            returnUser.setLogin((String) result.get(0).get("login"));
            returnUser.setFisrtName((String) result.get(0).get("firstname"));
            returnUser.setLastName((String) result.get(0).get("lastname"));
        }
        catch (IndexOutOfBoundsException exception){
            throw new RuntimeException("указанного пользователя не найдено");
        }
        int tempStatus = (Integer) result.get(0).get("status");
        switch (tempStatus) {
            case (1):
                returnUser.setStatus(Status.ACTIVE);
                break;
            case (2):
                returnUser.setStatus(Status.UNACCEPTED);
                break;
            case (3):
                returnUser.setStatus(Status.LOCKED);
                break;

        }

return returnUser;
    }


}
