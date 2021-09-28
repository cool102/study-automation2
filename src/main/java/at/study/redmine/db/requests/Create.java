package at.study.redmine.db.requests;

import at.study.redmine.model.user.Entity;

/**Данный интерефейс описывает операции с объектами
 * в БАЗЕ ДАННЫХ
 * Интерфейс чтобы создавать любые сущности
 * в БД. Метод create принимает сущность которую мы хотим создать в БД
 *
 * К примеру у нас есть пользователь. Если мы вызовем метод у класса который имплементирует
 * данный интерфейс то в БД будет создана сущность юзер.
 * @param <T>
 */


public interface Create<T extends Entity> {

    void create (T entity);
}
