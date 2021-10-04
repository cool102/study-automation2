package at.tests;

import at.study.redmine.model.user.Email;
import at.study.redmine.model.user.Status;
import at.study.redmine.model.user.Token;
import at.study.redmine.model.user.User;
import org.testng.annotations.Test;

import java.util.Random;

public class DatabaseUserCreationTest {
    @Test
    public void userDatabaseCreationTest() {
        User user = new User();
        user.setPassword("1qaz@WSX3edc");
        user.setStatus(Status.ACTIVE);
        user.setFisrtName("Имя" + new Random().nextInt(100));
        user.setLastName("Фамилия" + new Random().nextInt(100));

        Token token = new Token(user);
        token.setAction(Token.TokenType.API);

        Email email1 = new Email(user);
        Email email2 = new Email(user);
        email2.setIsDefault(false);
        Email email3 = new Email(user);
        email3.setIsDefault(false);
        Email email4 = new Email(user);
        email4.setAddress("manual@test.ru");
        email4.setIsDefault(false);

        user.create();


        System.out.println(user.getLogin());
        System.out.println(token.getValue());
        System.out.println(email1.getAddress());
        System.out.println(email2.getAddress());
        System.out.println(email3.getAddress());

    }

    @Test
    public void testUserLifeCycle() {
        User user = new User();
        user.create();

        System.out.println(user.getLogin());
        user.setStatus(Status.UNACCEPTED);
        user.update();


        user.setStatus(Status.LOCKED);
        user.update();

       user.delete();

    }

}

