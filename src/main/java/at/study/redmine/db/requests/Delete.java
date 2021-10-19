package at.study.redmine.db.requests;

/**
 * Данный интерефейс описывает операции с объектами
 * в БАЗЕ ДАННЫХ
 *
 * @param <T>
 */
public interface Delete {

    void delete(Integer id);
}
