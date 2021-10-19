package at.study.redmine.api.dto.errors;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    @SerializedName("errors")
    private List<String> errList;
}
