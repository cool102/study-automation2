package at.study.redmine.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CreatableEntity extends Entity{
    protected LocalDateTime createdOn = LocalDateTime.now();
    protected LocalDateTime updatedOn = LocalDateTime.now();

}
