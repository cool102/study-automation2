package at.study.redmine.model;

import at.study.redmine.model.user.Entity;

public interface Createable<T extends Entity> {
    T create();

}
