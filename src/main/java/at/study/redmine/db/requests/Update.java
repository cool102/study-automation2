package at.study.redmine.db.requests;

import at.study.redmine.model.user.Entity;

/***
 * Данный интерефейс описывает операции с объектами
 *  * в БАЗЕ ДАННЫХ
 * метод update служит для обновления сущности id которой мы передадим в методе
 * Будем передавать 2 параметра
 * Id и сущность которую нужно обновить.
 */
public interface Update <T extends Entity>{

    void update(Integer id, T entity);
}
