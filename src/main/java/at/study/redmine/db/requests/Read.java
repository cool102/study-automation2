package at.study.redmine.db.requests;

import at.study.redmine.model.user.Entity;

/**
 * Данный интерефейс описывает операции с объектами
 * в БАЗЕ ДАННЫХ
 * Интерфейс для получения сущности по id
 *
 * @param <T>
 */

public interface Read<T extends Entity> {

    T read(Integer id);
}
