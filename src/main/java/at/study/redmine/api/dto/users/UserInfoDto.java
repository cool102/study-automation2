package at.study.redmine.api.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/***
 * модель dto нужна только для сериализации (нам нужно получить
 * json из этого класса чтобы передать в body )
 * {
 *     "user": {
 *         "login": "alsal12",
 *         "firstname": "Almaz-Salyakhov",
 *         "lastname": "Fanilovich",
 *         "mail": "alsal@yahoo.gb",
 *         "password": "secret12344"
 *     }
 * }
 *
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class UserInfoDto {
    private UserDto user;
}
