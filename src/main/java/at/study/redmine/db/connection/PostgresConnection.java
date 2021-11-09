package at.study.redmine.db.connection;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import static at.study.redmine.property.Property.getIntegerProperty;
import static at.study.redmine.property.Property.getStringProperty;


public class PostgresConnection implements DatabaseConnection {
    public final static DatabaseConnection INSTANCE = new PostgresConnection();
    private final String host = getStringProperty("db.host");
    private final Integer port = getIntegerProperty("db.port");
    private final String database = getStringProperty("db.database");
    private final String user = getStringProperty("db.user");
    private final String password = getStringProperty("db.password");
    private Connection connection;

    public PostgresConnection() {
        connect();
    }

    @SneakyThrows
    public void connect() {

        Class.forName("org.postgresql.Driver");
        String url = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
        Properties connectionProperties = new Properties();

        connectionProperties.setProperty("user", user);
        connectionProperties.setProperty("password", password);

        connection = DriverManager.getConnection(url, connectionProperties);

    }


    @Override
    @SneakyThrows
    @Step("Выполнение SQL-запроса")
    public List<Map<String, Object>> executeQuery(String query, Object... parameters) {

        PreparedStatement stmt = connection.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            stmt.setObject(i + 1, parameters[i]);
        }
        try {
            Allure.addAttachment("SQL-запрос", stmt.toString());
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
            Allure.addAttachment("SQL-ответ", resultList.toString());
            return resultList;
        } catch (PSQLException exc) {
            if (exc.getMessage().equals("Запрос не вернул результатов.")) {
                return null;
            } else throw exc;
        }

    }

    @SneakyThrows
    public int executeUpdate(String query, Object... parameters) {
        PreparedStatement stmt = connection.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            stmt.setObject(i + 1, parameters[i]);
        }
        return stmt.executeUpdate();

    }

}
