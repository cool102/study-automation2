package at.study.redmine.model.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public abstract class Entity {
    protected Integer id;

}
