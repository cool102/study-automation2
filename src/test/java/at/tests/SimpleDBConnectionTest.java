package at.tests;

import at.study.redmine.db.connection.DatabaseConnection;
import at.study.redmine.db.connection.PostgresConnection;
import at.study.redmine.db.requests.TokenRequests;
import at.study.redmine.model.user.Token;
import at.study.redmine.model.user.User;
import lombok.Data;
import lombok.SneakyThrows;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class SimpleDBConnectionTest {
    @Test
    @SneakyThrows
    public void testDbConnection() {

        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://edu-at.dfu.i-teco.ru:5432/db";
        Properties connectionProperties = new Properties();

        connectionProperties.setProperty("user", "redmine_user");
        connectionProperties.setProperty("password", "redmine_pass");

        Connection connection = DriverManager.getConnection(url, connectionProperties);
        int filterId = 20000;
        String query = "SELECT * FROM tokens";
        PreparedStatement stmt = connection.prepareStatement(query);
        //stmt.setObject(1, filterId);

        ResultSet rs = stmt.executeQuery();


        List<Map<String, Object>> resultList = new ArrayList<>();

        while (rs.next()) {
            int columnCount = rs.getMetaData().getColumnCount();
            Map<String, Object> resultRow = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String key = rs.getMetaData().getColumnName(i);
                Object value = rs.getObject(key);
                resultRow.put(key, value);
            }
            resultList.add(resultRow);
        }
        System.out.println();
    }

    @Test
    public void postrgresClientTest() {
        DatabaseConnection client = new PostgresConnection();
        List<Map<String, Object>> result = client.executeQuery("select * from tokens WHERE id = ? OR id = ?", 37622, 37618);
    }

    @Test
    public void getUserTokensTest() {
        User user = new User();
        user.setId(26477);
        List<Token> userTokens = new TokenRequests(user).readAll();
    }

    @Test
    public void tokenCreationTEst(){
        User user = new User();
        user.setId(26477);
        TokenRequests requests = new TokenRequests(user);

        List<Token> userTokensBefore = requests.readAll();

        Token token = new Token(user);
        requests.create(token);

        List<Token> userTokensAfter = requests.readAll();
    }
}