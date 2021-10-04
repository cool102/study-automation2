package at.study.redmine.api.dto.users;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/***
 * этот класс описывает поля login и др. в класса UserDto
 * {
 *     "user": {
 *         "login": "alsal12",
 *         "firstname": "Almaz-Salyakhov",
 *         "lastname": "Fanilovich",
 *         "mail": "alsal@yahoo.gb",
 *         "password": "secret12344"
 *     }
 * }
 */
@Data // добавляет ctor, getter/setter
@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;

    private String login;
    private Boolean admin;

    @SerializedName("firstname")
    // Указываем значение как в json, потому что наименование по конвенции не совпадает с наименованием поля в json
    private String firstName;

    private Integer status;

    @SerializedName("lastname")
    private String lastName;

    private String mail;
    private String password;

    @SerializedName("created_on")
    private LocalDateTime createdOn;

    @SerializedName("last_login_on") // аннотация показывает по какому ключу
    // (в данном случае по ключу last_login_on) ожидается получения поля lastLoginOn
    private LocalDateTime lastLoginOn;


}
