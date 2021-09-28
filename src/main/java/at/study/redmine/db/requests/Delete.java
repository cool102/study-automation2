package at.study.redmine.db.requests;

import at.study.redmine.model.user.Entity;

/**
 * Данный интерефейс описывает операции с объектами
 * в БАЗЕ ДАННЫХ
 * @param <T>
 */
public interface Delete {

    void delete(Integer id);
}
