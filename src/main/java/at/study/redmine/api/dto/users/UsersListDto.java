package at.study.redmine.api.dto.users;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true) // реализует паттерн Builder
public class UsersListDto {
    @SerializedName("total_count")
    Integer totalCount;
    Integer offset;
    Integer limit;
    private List<UserDto> users;
}
