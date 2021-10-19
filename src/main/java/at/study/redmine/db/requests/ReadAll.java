package at.study.redmine.db.requests;

import at.study.redmine.model.user.Entity;

import java.util.List;

/**
 * Данный интерефейс описывает операции с объектами
 * в БАЗЕ ДАННЫХ
 * Возвращает список сущностей
 *
 * @param <T>
 */
public interface ReadAll<T extends Entity> {
    List<T> readAll();
}
